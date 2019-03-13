package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.common.item.ItemSporeBomb;
import com.mushroom.midnight.common.item.ItemSporeBomb.BombType;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class EntitySporeBomb extends EntityThrowable {
    private static final DataParameter<ItemStack> BOMB_STACK = EntityDataManager.createKey(EntitySporeBomb.class, DataSerializers.ITEM_STACK);

    public EntitySporeBomb(World world) {
        super(world);
    }

    public EntitySporeBomb(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntitySporeBomb(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BOMB_STACK, new ItemStack(ModItems.SPORE_BOMB));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setTag("bomb_stack", getBombStack().writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("bomb_stack", Constants.NBT.TAG_COMPOUND)) {
            setBombStack(new ItemStack(compound.getCompoundTag("bomb_stack")));
        }
    }

    public ItemStack getBombStack() {
        return this.dataManager.get(BOMB_STACK);
    }

    public void setBombStack(ItemStack bombStack) {
        this.dataManager.set(BOMB_STACK, bombStack);
    }

    @Override
    public String getName() {
        if (hasCustomName()) {
            return getCustomNameTag();
        } else {
            return getBombStack().getDisplayName();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote) {
            if (isInWater()) {
                entityDropItem(new ItemStack(ModItems.DARK_PEARL), 0.1f);
                setDead();
            } else if (ItemSporeBomb.checkExplode(this.world, getBombStack())) {
                ItemSporeBomb.explode(BombType.fromStack(getBombStack()), this.world, this.posX, this.posY, this.posZ);
                setDead();
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            DamageSource source = DamageSource.causeThrownDamage(this, getThrower());
            result.entityHit.attackEntityFrom(source, 1f);
        }
        if (!world.isRemote) {
            if (canBreakOn(result.getBlockPos())) {
                ItemSporeBomb.explode(BombType.fromStack(getBombStack()), this.world, this.posX, this.posY, this.posZ);
            } else {
                entityDropItem(getBombStack().copy(), 0.01f);
            }
            setDead();
        }
    }

    private boolean canBreakOn(@Nullable BlockPos impactedPos) {
        IBlockState impactedState;
        return impactedPos != null && ((impactedState = this.world.getBlockState(impactedPos)).getMaterial() == Material.ROCK || impactedState.getMaterial() == Material.IRON);
    }
}
