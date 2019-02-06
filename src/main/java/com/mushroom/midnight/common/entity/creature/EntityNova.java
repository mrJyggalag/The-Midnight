package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.NavigatorFlying;
import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityNova extends EntityMob implements EntityFlying {
    private static final DataParameter<Boolean> IS_ATTACKING = EntityDataManager.createKey(EntityNova.class, DataSerializers.BOOLEAN);
    private float heightOffset = 0.5f;
    private int heightOffsetUpdateTime;

    public EntityNova(World world) {
        super(world);
        setSize(1.2f, 1.8f);
        this.experienceValue = 10;
        this.isImmuneToFire = true;
        setPathPriority(PathNodeType.LAVA, 8f);
        setPathPriority(PathNodeType.DANGER_FIRE, 0f);
        setPathPriority(PathNodeType.DAMAGE_FIRE, 0f);
        this.moveHelper = new EntityFlyHelper(this);
    }

    @Override
    protected PathNavigate createNavigator(World world) {
        NavigatorFlying nav = new NavigatorFlying(this, world);
        nav.setCanFloat(true);
        nav.setCanOpenDoors(false);
        nav.setCanEnterDoors(false);
        return nav;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (getPosition().getY() > 50) {
            return false;
        }
        IBlockState belowState = world.getBlockState(new BlockPos(this).down());
        return belowState.isFullCube() && belowState.canEntitySpawn(this);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_ATTACKING, false);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1d, false) {
            @Override
            public void resetTask() {
                super.resetTask();
                setAttacking(false);
            }

            @Override
            public void startExecuting() {
                super.startExecuting();
                setAttacking(true);
            }
        });
        this.tasks.addTask(5, new EntityAIMoveTowardsTarget(this, 1d, 40));
        this.tasks.addTask(7, new EntityAIWanderAvoidWaterFlying(this, 0.8d));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8f, 0.01f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, true, false));
    }

    @Override
    protected void updateAITasks() {
        if (isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1f);
        }
        // stay over the target by moment
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float) this.rand.nextGaussian() * 3f;
        }
        EntityLivingBase target = getAttackTarget();
        if (target != null && target.isEntityAlive() && target.posY + (double) target.getEyeHeight() > this.posY + (double) getEyeHeight() + (double) this.heightOffset) {
            this.motionY += (0.30000001192092896d - this.motionY) * 0.30000001192092896d;
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16d);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4d);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40d);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2d);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4d);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return ModLootTables.LOOT_TABLE_NOVA;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 14 << 20 | 14 << 4;
    }

    public boolean isAttacking() {
        return dataManager.get(IS_ATTACKING);
    }

    public void setAttacking(boolean isAttacking) {
        dataManager.set(IS_ATTACKING, isAttacking);
    }
}
