package com.mushroom.midnight.common.entity;

import com.google.common.collect.Lists;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.entity.task.EntityTaskRifterCapture;
import com.mushroom.midnight.common.entity.task.EntityTaskRifterMelee;
import com.mushroom.midnight.common.entity.task.EntityTaskRifterTransport;
import com.mushroom.midnight.common.network.MessageCaptureEntity;
import com.mushroom.midnight.common.registry.ModDimensions;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EntityRifter extends EntityMob implements IRiftTraveler, IEntityAdditionalSpawnData {
    public static final int PICK_UP_COOLDOWN = 20;

    private static final double RIFT_SEARCH_RADIUS = 48.0;

    private final EntityReference<EntityRift> homeRift;
    private final DragSolver dragSolver;

    public int pickUpCooldown;

    private EntityLivingBase capturedEntity;

    public EntityRifter(World world) {
        super(world);
        this.homeRift = new EntityReference<>(world);
        this.dragSolver = new DragSolver(this);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityTaskRifterTransport(this, 1.0));
        this.tasks.addTask(2, new EntityTaskRifterCapture(this, 1.0));
        this.tasks.addTask(3, new EntityTaskRifterMelee(this, 1.0));

        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));

        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 2, true, false, e -> {
            if (e == null || RifterCapturedCapability.isCaptured(e)) {
                return false;
            }
            return !(e instanceof EntityRifter);
        }));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            this.checkBurn();

            if (this.capturedEntity != null && !this.capturedEntity.isEntityAlive()) {
                this.setCapturedEntity(null);
            }

            if (this.pickUpCooldown > 0) {
                this.pickUpCooldown--;
            }

            if (this.world.provider.getDimensionType() == DimensionType.OVERWORLD) {
                this.updateHomeRift();
            }
        }

        if (!this.world.isRemote || Midnight.proxy.isClientPlayer(this.capturedEntity)) {
            this.dragSolver.solveDrag();
        }

        super.onLivingUpdate();
    }

    public boolean shouldCapture() {
        if (this.world.isDaytime() || this.world.provider.getDimensionType() == ModDimensions.MIDNIGHT) {
            return false;
        }
        return this.homeRift.isPresent();
    }

    private void updateHomeRift() {
        if (this.ticksExisted % 20 == 0 && !this.homeRift.isPresent()) {
            AxisAlignedBB searchBounds = this.getEntityBoundingBox().grow(RIFT_SEARCH_RADIUS);
            List<EntityRift> rifts = this.world.getEntitiesWithinAABB(EntityRift.class, searchBounds);
            if (!rifts.isEmpty()) {
                rifts.sort(Comparator.comparingDouble(this::getDistanceSq));
                this.homeRift.set(rifts.get(0));
            }
        }
    }

    private void checkBurn() {
        if (this.world.isDaytime()) {
            float brightness = this.getBrightness();
            if (brightness > 0.5F && this.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F) {
                if (!this.world.canSeeSky(this.getPosition())) {
                    return;
                }
                this.setFire(8);
                this.setCapturedEntity(null);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (super.attackEntityFrom(source, amount)) {
            this.setCapturedEntity(null);
            return true;
        }
        return false;
    }

    public void setCapturedEntity(EntityLivingBase capturedEntity) {
        if (this.capturedEntity != null) {
            RifterCapturedCapability capability = this.capturedEntity.getCapability(Midnight.rifterCapturedCap, null);
            if (capability != null) {
                capability.setCaptured(false);
            }
        }

        this.capturedEntity = capturedEntity;
        this.dragSolver.setDragged(capturedEntity);

        if (capturedEntity != null) {
            RifterCapturedCapability capability = capturedEntity.getCapability(Midnight.rifterCapturedCap, null);
            if (capability != null) {
                capability.setCaptured(true);
            }
        }

        if (!this.world.isRemote) {
            MessageCaptureEntity message = new MessageCaptureEntity(this, capturedEntity);
            Midnight.NETWORK.sendToAllTracking(message, this);
        }
    }

    public EntityLivingBase getCapturedEntity() {
        return this.capturedEntity;
    }

    public boolean hasCaptured() {
        return this.capturedEntity != null;
    }

    public EntityReference<EntityRift> getHomeRift() {
        return this.homeRift;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setTag("home_rift", this.homeRift.serialize(new NBTTagCompound()));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.homeRift.deserialize(compound.getCompoundTag("home_rift"));
    }

    @Override
    public void onEnterRift(EntityRift rift) {
        if (this.capturedEntity != null) {
            this.setCapturedEntity(null);
        }
    }

    @Override
    public Collection<Entity> getAdditionalTeleportEntities() {
        if (this.capturedEntity != null) {
            return Lists.newArrayList(this.capturedEntity);
        }
        return Collections.emptyList();
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.capturedEntity != null ? this.capturedEntity.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        int capturedId = buffer.readInt();
        if (capturedId != -1) {
            Entity entity = this.world.getEntityByID(capturedId);
            if (entity instanceof EntityLivingBase) {
                this.setCapturedEntity((EntityLivingBase) entity);
            }
        }
    }
}
