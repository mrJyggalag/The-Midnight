package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightSounds;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.mushroom.midnight.common.registry.MidnightLootTables.LOOT_TABLE_CRYSTAL_BUG;

public class CrystalBugEntity extends AmbientEntity {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(CrystalBugEntity.class, DataSerializers.BOOLEAN);
    private BlockPos spawnPosition;

    public CrystalBugEntity(World world) {
        super(world);
        setSize(0.2f, 0.2f);
        experienceValue = 3;
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(IS_STANDING, false);
    }


    @Override
    public boolean getCanSpawnHere() {
        return getPosition().getY() > world.getSeaLevel() && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 10;
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    public boolean isStanding() {
        return dataManager.get(IS_STANDING);
    }

    private void setStanding(boolean isStanding) {
        dataManager.set(IS_STANDING, isStanding);
    }

    @Override
    public void tick() {
        super.tick();
        if (isStanding()) {
            setMotion(Vec3d.ZERO);
        } else {
            setMotion(getMotion().mul(1.0, 0.4, 1.0));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        dataManager.set(IS_STANDING, compound.getBoolean("isStanding"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("isStanding", dataManager.get(IS_STANDING));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isEntityInvulnerable(source)) {
            return false;
        }
        if (!world.isRemote && isStanding()) {
            setStanding(false);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.offset(getHorizontalFacing());
        if (isStanding()) {
            if (canStayOnBlock(world.getBlockState(blockpos1))) {
                if (world.getNearestPlayerNotCreative(this, 4d) != null) {
                    setStanding(false);
                    world.playEvent(null, 1025, blockpos, 0);
                }
            } else {
                setStanding(false);
                world.playEvent(null, 1025, blockpos, 0);
            }
        } else {
            if (spawnPosition != null && (!world.isAirBlock(spawnPosition) || spawnPosition.getY() < 1)) {
                spawnPosition = null;
            }
            if (spawnPosition == null || rand.nextInt(30) == 0 || spawnPosition.distanceSq((double) ((int) posX), (double) ((int) posY), (double) ((int) posZ)) < 4d) {
                spawnPosition = new BlockPos((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
            }
            double d0 = (double) spawnPosition.getX() + 0.5d - posX;
            double d1 = (double) spawnPosition.getY() + 0.1d - posY;
            double d2 = (double) spawnPosition.getZ() + 0.5d - posZ;
            motionX += (Math.signum(d0) * 0.2d - motionX) * 0.1d;
            motionY += (Math.signum(d1) * 0.4d - motionY) * 0.1d;
            motionZ += (Math.signum(d2) * 0.2d - motionZ) * 0.1d;
            float f = (float) (MathHelper.atan2(motionZ, motionX) * (180d / Math.PI)) - 90f;
            float f1 = MathHelper.wrapDegrees(f - rotationYaw);
            moveForward = 0.2f;
            rotationYaw += f1;
            if (rand.nextInt(100) == 0 && canStayOnBlock(world.getBlockState(blockpos1))) {
                setStanding(true);
                spawnPosition = blockpos1;
            }
        }
        if (!isStanding() && ticksExisted % 80 == 0) {
            world.playSound(null, getPosition(), MidnightSounds.CRYSTAL_BUG_FLYING, SoundCategory.NEUTRAL, getRNG().nextFloat() * 0.6f, getRNG().nextFloat() * 0.15f);
        }
    }

    private boolean canStayOnBlock(BlockState state) {
        return state.getBlock() == MidnightBlocks.BLOOMCRYSTAL_ROCK;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3d);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public float getEyeHeight() {
        return height / 3f;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return MidnightSounds.CRYSTAL_BUG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MidnightSounds.CRYSTAL_BUG_DEATH;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE_CRYSTAL_BUG;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getBrightnessForRender() {
        return 14 << 20 | 14 << 4;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}
