package com.mushroom.midnight.common.entities;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.client.particle.RiftParticle;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.world.MidnightTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import javax.vecmath.Point2f;
import java.util.List;
import java.util.Random;

public class EntityRift extends Entity {
    public static final int OPEN_TIME = 10;
    public static final int UNSTABLE_TIME = 100;

    private static final int POINT_COUNT = 12;
    private static final float DISPLACEMENT_SCALE = 0.4F;

    private static final int LIFETIME = 120;

    private static final float PULL_RADIUS = 8.0F;
    private static final float PULL_INTENSITY = 5.0F;

    private static final DataParameter<Boolean> OPEN = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> UNSTABLE = EntityDataManager.createKey(EntityRift.class, DataSerializers.BOOLEAN);

    private long geometrySeed = new Random().nextLong();

    public int openProgress;
    public int prevOpenProgress;

    public int unstableTime;
    public int prevUnstableTime;

    public EntityRift(World world) {
        super(world);
        this.setSize(1.8F, 4.0F);
        this.noClip = true;
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
        } else {
            this.spawnParticles();
        }

        this.updateTimers();
    }

    private void updateTimers() {
        if (this.isOpen()) {
            this.openProgress = Math.min(this.openProgress + 1, OPEN_TIME);
        } else {
            this.openProgress = Math.max(this.openProgress - 1, 0);
        }

        if (this.isUnstable()) {
            this.unstableTime = Math.min(this.unstableTime + 1, UNSTABLE_TIME);
        } else {
            this.unstableTime = Math.max(this.unstableTime - 1, 0);
        }
    }

    private void pullEntities() {
        double pullIntensity = Math.pow((double) this.unstableTime / UNSTABLE_TIME, 3.0) * PULL_INTENSITY;

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
        AxisAlignedBB bounds = this.getEntityBoundingBox().grow(-0.8);
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

    private void spawnParticles() {
        Random random = this.world.rand;

        if (this.world.provider.getDimensionType() != ModDimensions.MIDNIGHT) {
            this.spawnSpores(random);
        }

        if (this.isOpen() && this.openProgress < OPEN_TIME) {
            this.spawnOrbitalParticles(random);
        }
    }

    private void spawnSpores(Random random) {
        if (random.nextInt(5) == 0) {
            double particleX = this.posX + (random.nextInt(4) - random.nextInt(4));
            double particleY = this.posY + (random.nextInt(4) - random.nextInt(4));
            double particleZ = this.posZ + (random.nextInt(4) - random.nextInt(4));
            double velocityX = (random.nextDouble() - 0.5) * 0.02;
            double velocityY = (random.nextDouble() - 0.5) * 0.02;
            double velocityZ = (random.nextDouble() - 0.5) * 0.02;
            MidnightParticles.SPORE.spawn(this.world, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    private void spawnOrbitalParticles(Random random) {
        ParticleManager effectRenderer = Minecraft.getMinecraft().effectRenderer;

        for (int i = 0; i < 20; i++) {
            float offsetHorizontal = (random.nextFloat() - 0.5F) * this.width * 0.8F;
            float offsetVertical = (random.nextFloat() - 0.5F) * this.height * 0.8F;

            float theta = (float) Math.toRadians(this.rotationYaw);
            double particleX = this.posX + MathHelper.cos(theta) * offsetHorizontal;
            double particleY = this.posY + offsetVertical;
            double particleZ = this.posZ + MathHelper.sin(theta) * offsetHorizontal;

            float orbitalOffset = random.nextFloat() * 360.0F;

            RiftParticle particle = new RiftParticle(this, particleX, particleY, particleZ, orbitalOffset, orbitalInclination);
            effectRenderer.addEffect(particle);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setLong("geometry_seed", this.geometrySeed);
        compound.setInteger("age", this.ticksExisted);
        compound.setBoolean("open", this.isOpen());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.geometrySeed = compound.getLong("geometry_seed");
        this.ticksExisted = compound.getInteger("age");
        this.dataManager.set(OPEN, !compound.hasKey("open") || compound.getBoolean("open"));
    }

    public Point2f[] computePath(float animation, float unstableAnimation, float time) {
        Random random = new Random(this.geometrySeed);
        Point2f[] ring = new Point2f[POINT_COUNT];

        float idleSpeed = 0.08F;
        float idleIntensity = 0.1F * animation * (unstableAnimation * 5.0F + 1.0F);

        float displacementAnimation = Math.min(2.0F * animation, 1.0F);

        float sizeX = (this.width / 2.0F) * animation;
        float sizeY = (this.height / 2.0F) * 0.5F * (animation + 1.0F);

        // Generates a circle around (0; 0), stretching it horizontally and displacing each vertex by a random amount

        float tau = (float) (Math.PI * 2.0F);
        for (int i = 0; i < POINT_COUNT; i++) {
            float angle = i * tau / POINT_COUNT;

            float idleAnimation = MathHelper.sin(time * idleSpeed + i * 10) * idleIntensity;
            float displacement = (random.nextFloat() * 2.0F - 1.0F) + idleAnimation;
            float scaledDisplacement = displacement * DISPLACEMENT_SCALE * displacementAnimation;

            float pointX = -MathHelper.sin(angle) * (sizeX + scaledDisplacement);
            float pointY = MathHelper.cos(angle) * (sizeY + scaledDisplacement);

            ring[i] = new Point2f(pointX, pointY);
        }

        return ring;
    }

    public boolean isOpen() {
        return this.dataManager.get(OPEN);
    }

    public boolean isUnstable() {
        return this.dataManager.get(UNSTABLE);
    }
}
