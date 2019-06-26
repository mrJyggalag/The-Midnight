package com.mushroom.midnight.common.entity.creature;

import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public abstract class GrowableEntity extends CreatureEntity {
    private static final DataParameter<Integer> GROWING_AGE = EntityDataManager.createKey(GrowableEntity.class, DataSerializers.VARINT);
    protected int growingTime = 0;
    private float ageWidth = -1f;
    private float ageHeight;

    public GrowableEntity(World world) {
        super(world);
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
        setScaleForAge(age);
    }

    public void setScaleForAge(int age) {
        float scale = 1f + age * 0.5f;
        super.setSize(this.ageWidth * scale, this.ageHeight * scale);
    }

    @Override
    public void writeEntityToNBT(CompoundNBT compound) {
        super.writeEntityToNBT(compound);
        compound.putInt("growing_age", getGrowingAge());
        compound.putInt("growing_time", this.growingTime);
    }

    @Override
    public void readEntityFromNBT(CompoundNBT compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("growing_age", Constants.NBT.TAG_INT)) {
            setGrowingAge(compound.getInt("growing_age"));
        }
        if (compound.hasKey("growing_time", Constants.NBT.TAG_INT)) {
            this.growingTime = compound.getInt("growing_time");
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (GROWING_AGE.equals(key)) {
            setScaleForAge(getGrowingAge());
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
    protected final void setSize(float width, float height) {
        boolean flag = this.ageWidth > 0f;
        this.ageWidth = width;
        this.ageHeight = height;
        if (!flag) {
            super.setSize(this.ageWidth, this.ageHeight);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == Items.SPAWN_EGG) {
            if (!this.world.isRemote && holdingSpawnEggOfClass(stack, getClass())) {
                GrowableEntity entity = createChild(this);
                if (entity != null) {
                    entity.setGrowingAge(-1);
                    entity.setLocationAndAngles(this.posX, this.posY, this.posZ, 0f, 0f);
                    this.world.spawnEntity(entity);
                    if (stack.hasDisplayName()) {
                        entity.setCustomNameTag(stack.getDisplayName());
                    }
                    if (!player.capabilities.isCreativeMode) {
                        stack.shrink(1);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean holdingSpawnEggOfClass(ItemStack stack, Class<? extends Entity> entityClass) {
        if (stack.getItem() != Items.SPAWN_EGG) {
            return false;
        } else {
            ResourceLocation rl = ItemMonsterPlacer.getNamedIdFrom(stack);
            if (rl != null) {
                Class<? extends Entity> oclass = EntityList.getClass(rl);
                return entityClass == oclass;
            }
            return false;
        }
    }
}
