package com.mushroom.midnight.common.entity.creature;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public abstract class GrowableEntity extends CreatureEntity {
    private static final DataParameter<Integer> GROWING_AGE = EntityDataManager.createKey(GrowableEntity.class, DataSerializers.VARINT);
    protected int growingTime = 0;
    private float ageWidth = -1f;
    private float ageHeight = -1f;

    public GrowableEntity(EntityType<? extends GrowableEntity> entityType, World worldIn) {
        super(entityType, worldIn);
    }

    public abstract int getMaxGrowingAge();
    public abstract int getGrowingTimeByAge();
    protected abstract void onGrowingToAge(int age);

    @Nullable
    public abstract GrowableEntity createChild(GrowableEntity growable);

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GROWING_AGE, 0);
    }

    public int getGrowingAge() {
        return this.dataManager.get(GROWING_AGE);
    }

    public void setGrowingAge(int age) {
        this.dataManager.set(GROWING_AGE, age);
        this.growingTime = 0;
        onGrowingToAge(age);
        recalculateSize();
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        float scale = 1f + getGrowingAge() * 0.5f;
        return EntitySize.flexible(this.ageWidth * scale, this.ageHeight * scale);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("growing_age", getGrowingAge());
        compound.putInt("growing_time", this.growingTime);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("growing_age", Constants.NBT.TAG_INT)) {
            setGrowingAge(compound.getInt("growing_age"));
        }
        if (compound.contains("growing_time", Constants.NBT.TAG_INT)) {
            this.growingTime = compound.getInt("growing_time");
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (GROWING_AGE.equals(key)) {
            recalculateSize();
        }
        super.notifyDataManagerChange(key);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        int growingAge = getGrowingAge();
        if (growingAge < getMaxGrowingAge()) {
            if (++this.growingTime >= getGrowingTimeByAge()) {
                setGrowingAge(++growingAge);
                this.growingTime = growingTime - getGrowingTimeByAge();
            }
        }
    }

    @Override
    public boolean isChild() {
        return this.getGrowingAge() < 0;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof SpawnEggItem && ((SpawnEggItem) stack.getItem()).hasType(stack.getTag(), this.getType())) {
            if (!this.world.isRemote) {
                GrowableEntity entity = createChild(this);
                if (entity != null) {
                    entity.setGrowingAge(-1);
                    entity.setLocationAndAngles(this.posX, this.posY, this.posZ, 0f, 0f);
                    this.world.addEntity(entity);
                    if (stack.hasDisplayName()) {
                        entity.setCustomName(stack.getDisplayName());
                    }
                    onChildSpawnFromEgg(player, entity);
                    if (!player.abilities.isCreativeMode) {
                        stack.shrink(1);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    protected void onChildSpawnFromEgg(PlayerEntity playerIn, GrowableEntity child) {
    }
}
