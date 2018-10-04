package com.mushroom.midnight.common.entities;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.RiftParticleSystem;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.world.MidnightTeleporter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Vector3d;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class EntityRift extends Entity implements IEntityAdditionalSpawnData {
    // TODO: In a singleplayer game where the overworld isn't loaded, the player can never get back!

    public static final int OPEN_TIME = 20;
    public static final int CLOSE_SPEED = 2;
    public static final int CLOSE_TIME = OPEN_TIME / CLOSE_SPEED;

    public static final int UNSTABLE_TIME = 100;

    public static final int LIFETIME = 1200;

    public static final float PULL_RADIUS = 8.0F;
    public static final float PULL_INTENSITY = 5.0F;

    public static final double MAX_PULL_VELOCITY = 1.2;

    public static final DataParameter<Boolean> OPEN = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> UNSTABLE = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> USED = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);

    private RiftGeometry geometry;

    public int openProgress;
    public int prevOpenProgress;

    public int unstableTime;
    public int prevUnstableTime;

    public BlockPos endpoint;
    public EntityRift endpointRift;

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
        this.dataManager.register(USED, false);
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

            if (this.endpointRift != null && this.endpointRift.isDead) {
                this.setDead();
                return;
            }

            if (this.isUnstable()) {
                if (this.unstableTime >= UNSTABLE_TIME && this.isOpen()) {
                    this.dataManager.set(OPEN, false);
                }
                if (!this.wasUsed() && this.world.provider.getDimensionType() != ModDimensions.MIDNIGHT) {
                    this.pullEntities();
                }
            } else if (this.ticksExisted > LIFETIME || this.world.isDaytime()) {
                this.dataManager.set(UNSTABLE, true);
            }

            if (this.openProgress >= OPEN_TIME && !this.wasUsed()) {
                this.teleportEntities();
            }

            if (this.endpointRift == null) {
                World endpointWorld = this.getEndpointWorld();
                if (endpointWorld != null && this.isEndpointLoaded(endpointWorld)) {
                    this.computeEndpointRift(endpointWorld);
                }
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
            this.unstableTime = 0;
        }
    }

    private void pullEntities() {
        double pullIntensity = this.getPullIntensity();

        AxisAlignedBB pullBounds = this.getEntityBoundingBox().grow(PULL_RADIUS);
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, pullBounds);
        for (Entity entity : entities) {
            if (!(entity instanceof EntityRift)) {
                this.pullEntity(pullIntensity, entity);
            }
        }
    }

    public void pullEntity(double pullIntensity, Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying) {
            return;
        }

        double deltaX = this.posX - entity.posX;
        double deltaY = (this.posY + this.height / 2.0F) - (entity.posY + entity.height / 2.0F);
        double deltaZ = this.posZ - entity.posZ;

        double distanceSq = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

        if (distanceSq > 0.0) {
            double distance = Math.sqrt(distanceSq);
            double intensity = MathHelper.clamp(pullIntensity / distanceSq, 0.0F, 1.0F);

            double velocityX = entity.motionX + (deltaX / distance) * intensity;
            double velocityY = entity.motionY + (deltaY / distance) * intensity;
            double velocityZ = entity.motionZ + (deltaZ / distance) * intensity;

            Vector3d velocity = new Vector3d(velocityX, velocityY, velocityZ);
            if (velocity.lengthSquared() > MAX_PULL_VELOCITY * MAX_PULL_VELOCITY) {
                velocity.normalize();
                velocity.scale(MAX_PULL_VELOCITY);
            }

            entity.motionX = velocity.x;
            entity.motionY = velocity.y;
            entity.motionZ = velocity.z;

            entity.fallDistance = 0.0F;
        }
    }

    public double getPullIntensity() {
        if (this.wasUsed()) {
            return 0.0;
        }
        return Math.pow((double) this.unstableTime / UNSTABLE_TIME, 5.0) * PULL_INTENSITY;
    }

    private void teleportEntities() {
        AxisAlignedBB bounds = this.getEntityBoundingBox().grow(-0.4);
        DimensionType endpointDimension = this.getEndpointDimension();

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
            entity.changeDimension(endpointDimension.getId(), new MidnightTeleporter(this));
        }
    }

    private boolean isEndpointLoaded(World endpointWorld) {
        if (this.endpoint != null) {
            return endpointWorld.isBlockLoaded(this.endpoint);
        }
        return endpointWorld.isBlockLoaded(this.getPosition());
    }

    @Nonnull
    public EntityRift computeEndpointRift(World endpointWorld) {
        if (this.endpointRift != null && this.endpointRift.world == endpointWorld) {
            return this.endpointRift;
        }

        this.endpointRift = this.getEndpointRift(endpointWorld);
        this.endpointRift.setEndpoint(this);

        return this.endpointRift;
    }

    private EntityRift getEndpointRift(World endpointWorld) {
        BlockPos endpoint = this.computeEndpoint(endpointWorld);
        EntityRift nearestRift = this.getNearestRift(endpointWorld, endpoint, 4.0);
        if (nearestRift != null) {
            return nearestRift;
        }

        return this.createEndpointRift(endpointWorld);
    }

    @Nullable
    private EntityRift getNearestRift(World endpointWorld, BlockPos pos, double range) {
        AxisAlignedBB bounds = new AxisAlignedBB(pos).grow(range);

        List<EntityRift> rifts = endpointWorld.getEntitiesWithinAABB(EntityRift.class, bounds);
        rifts.sort(Comparator.comparingDouble(e -> e.getDistanceSq(pos)));

        if (!rifts.isEmpty()) {
            return rifts.get(0);
        }

        return null;
    }

    private EntityRift createEndpointRift(World endpointWorld) {
        EntityRift endpointRift = new EntityRift(endpointWorld);

        BlockPos endpoint = this.endpoint;
        float yaw = this.rotationYaw;
        endpointRift.setPositionAndRotation(endpoint.getX() + 0.5, endpoint.getY() + 0.5, endpoint.getZ() + 0.5, yaw, 0.0F);

        endpointRift.dataManager.set(UNSTABLE, this.isUnstable());
        endpointRift.dataManager.set(OPEN, this.isOpen());

        endpointRift.ticksExisted = this.ticksExisted;
        endpointRift.openProgress = this.openProgress;
        endpointRift.prevOpenProgress = this.prevOpenProgress;
        endpointRift.unstableTime = this.unstableTime;
        endpointRift.prevUnstableTime = this.prevUnstableTime;

        endpointWorld.spawnEntity(endpointRift);

        return endpointRift;
    }

    private BlockPos computeEndpoint(World endpointWorld) {
        if (this.endpoint == null) {
            this.endpoint = endpointWorld.getTopSolidOrLiquidBlock(this.getPosition());
        }
        return this.endpoint;
    }

    public void setEndpoint(EntityRift rift) {
        this.endpointRift = rift;
        this.endpoint = rift.getPosition();
    }

    public void close() {
        this.dataManager.set(UNSTABLE, true);
        this.dataManager.set(USED, true);
    }

    public boolean isOpen() {
        return this.dataManager.get(OPEN);
    }

    public boolean isUnstable() {
        return this.dataManager.get(UNSTABLE);
    }

    public boolean wasUsed() {
        return this.dataManager.get(USED);
    }

    public RiftGeometry getGeometry() {
        return this.geometry;
    }

    @Nullable
    public RiftParticleSystem getParticleSystem() {
        return this.particleSystem;
    }

    public DimensionType getEndpointDimension() {
        if (this.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
            return DimensionType.OVERWORLD;
        } else {
            return ModDimensions.MIDNIGHT;
        }
    }

    @Nullable
    private World getEndpointWorld() {
        DimensionType endpointDimension = this.getEndpointDimension();
        return DimensionManager.getWorld(endpointDimension.getId());
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
        buffer.writeInt(this.openProgress);
        buffer.writeInt(this.unstableTime);
        buffer.writeInt(this.ticksExisted);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.initGeometry(buffer.readLong());

        this.openProgress = buffer.readInt();
        this.prevOpenProgress = this.openProgress;

        this.unstableTime = buffer.readInt();
        this.prevUnstableTime = this.unstableTime;

        this.ticksExisted = buffer.readInt();
    }
}
