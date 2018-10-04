package com.mushroom.midnight.common.entities;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.RiftParticleSystem;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.world.MidnightTeleporter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EntityRift extends Entity implements IEntityAdditionalSpawnData {
    public static final int OPEN_TIME = 20;
    public static final int CLOSE_SPEED = 2;
    public static final int CLOSE_TIME = OPEN_TIME / CLOSE_SPEED;

    public static final int UNSTABLE_TIME = 100;

    private static final int LIFETIME = 80;

    private static final float PULL_RADIUS = 8.0F;
    private static final float PULL_INTENSITY = 5.0F;

    private static final DataParameter<Boolean> OPEN = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> UNSTABLE = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);

    private RiftGeometry geometry;

    public int openProgress;
    public int prevOpenProgress;

    public int unstableTime;
    public int prevUnstableTime;

    private RiftParticleSystem particleSystem;

    public EntityRift(World world) {
        super(world);
        this.setSize(1.8F, 4.0F);
        this.noClip = true;
        this.initGeometry(new Random().nextLong());
        if (world.isRemote) {
            this.particleSystem = new RiftParticleSystem(this);
        }
    }

    private void initGeometry(long seed) {
        this.geometry = new RiftGeometry(seed, this.width, this.height);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(OPEN, true);
        this.dataManager.register(UNSTABLE, false);
    }

    @Override
    public void onUpdate() {
        this.prevOpenProgress = this.openProgress;
        this.prevUnstableTime = this.unstableTime;

        super.onUpdate();

        if (!this.world.isRemote) {
            if (!this.isOpen() && this.openProgress <= 0) {
                this.setDead();
                return;
            }

            if (this.isUnstable()) {
                if (this.unstableTime >= UNSTABLE_TIME && this.isOpen()) {
                    this.dataManager.set(OPEN, false);
                }
                if (this.world.provider.getDimensionType() != ModDimensions.MIDNIGHT) {
                    this.pullEntities();
                }
            } else if (this.ticksExisted > LIFETIME) {
                this.dataManager.set(UNSTABLE, true);
            }

            if (this.openProgress >= OPEN_TIME) {
                this.teleportEntities();
            }
        }

        if (this.particleSystem != null && this.world.isRemote) {
            this.particleSystem.updateParticles(this.world.rand);
        }

        this.updateTimers();
    }

    private void updateTimers() {
        if (this.isOpen()) {
            this.openProgress = Math.min(this.openProgress + 1, OPEN_TIME);
        } else {
            this.openProgress = Math.max(this.openProgress - CLOSE_SPEED, 0);
        }

        if (this.isUnstable()) {
            this.unstableTime = Math.min(this.unstableTime + 1, UNSTABLE_TIME);
        } else {
            this.unstableTime = Math.max(this.unstableTime - 1, 0);
        }
    }

    private void pullEntities() {
        double pullIntensity = Math.pow((double) this.unstableTime / UNSTABLE_TIME, 5.0) * PULL_INTENSITY;

        AxisAlignedBB pullBounds = this.getEntityBoundingBox().grow(PULL_RADIUS);
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, pullBounds);
        for (Entity entity : entities) {
            if (!(entity instanceof EntityRift)) {
                this.pullEntity(pullIntensity, entity);
            }
        }
    }

    private void pullEntity(double pullIntensity, Entity entity) {
        double deltaX = this.posX - entity.posX;
        double deltaY = (this.posY + this.height / 2.0F) - (entity.posY + entity.height / 2.0F);
        double deltaZ = this.posZ - entity.posZ;

        double distanceSq = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

        if (distanceSq > 0.0) {
            double distance = Math.sqrt(distanceSq);
            double intensity = MathHelper.clamp(pullIntensity / distanceSq, 0.0F, 1.0F);

            // TODO: Sync to the client somehow as clients control player movement
            entity.motionX += (deltaX / distance) * intensity;
            entity.motionY += (deltaY / distance) * intensity;
            entity.motionZ += (deltaZ / distance) * intensity;

            entity.fallDistance = 0.0F;
        }
    }

    private void teleportEntities() {
        AxisAlignedBB bounds = this.getEntityBoundingBox().grow(-0.6);
        DimensionType transportDimension = this.getTransportDimension();

        List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, bounds, entity -> {
            if (entity.isRiding() || entity.isBeingRidden()) {
                return false;
            }
            RiftCooldownCapability cooldown = entity.getCapability(Midnight.riftCooldownCap, null);
            if (cooldown != null && !cooldown.isReady()) {
                return false;
            }
            return !(entity instanceof EntityRift);
        });

        for (Entity entity : entities) {
            entity.changeDimension(transportDimension.getId(), MidnightTeleporter.INSTANCE);
        }
    }

    private DimensionType getTransportDimension() {
        if (this.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
            return DimensionType.OVERWORLD;
        } else {
            return ModDimensions.MIDNIGHT;
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setLong("geometry_seed", this.geometry.getSeed());
        compound.setInteger("age", this.ticksExisted);
        compound.setBoolean("open", this.isOpen());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.initGeometry(compound.getLong("geometry_seed"));
        this.ticksExisted = compound.getInteger("age");
        this.dataManager.set(OPEN, !compound.hasKey("open") || compound.getBoolean("open"));
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeLong(this.geometry.getSeed());
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.initGeometry(buffer.readLong());
    }

    public boolean isOpen() {
        return this.dataManager.get(OPEN);
    }

    public boolean isUnstable() {
        return this.dataManager.get(UNSTABLE);
    }

    public RiftGeometry getGeometry() {
        return this.geometry;
    }

    @Nullable
    public RiftParticleSystem getParticleSystem() {
        return this.particleSystem;
    }
}
