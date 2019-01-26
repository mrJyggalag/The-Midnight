package com.mushroom.midnight.common.entity.creature;

import com.mushroom.midnight.common.entity.NavigatorFlying;
import com.mushroom.midnight.common.registry.ModLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityNova extends EntityMob implements EntityFlying {
	
    private static final DataParameter<Boolean> IS_ATTACKING = EntityDataManager.createKey(EntityNova.class, DataSerializers.BOOLEAN);

    public EntityNova(World world) {
        super(world);
        setSize(1.4f, 1.8f);
        experienceValue = 10;
    }

    @Override
    public boolean getCanSpawnHere() {
        return getPosition().getY() < world.getSeaLevel() && super.getCanSpawnHere();
    }
    
    protected boolean canFlyUp() {
    	return this.getPosition().up() == Blocks.AIR.getDefaultState() && this.getPosition().getY() < world.getSeaLevel();
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
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.21d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14d);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2d);
    }
    
    @Override
    public void onLivingUpdate()
    {
    	if (!this.onGround && this.motionY < 0.0D && this.canFlyUp())
    	{
    		this.motionY *= 0.45D;
    	}
    	super.onLivingUpdate();
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (isServerWorld()) {
            moveRelative(strafe, vertical, forward, 0.1f);
            move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421d;
            this.motionY *= 0.8999999761581421d;
            this.motionZ *= 0.8999999761581421d;
        }
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
    public boolean canBreatheUnderwater() {
        return true;
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
