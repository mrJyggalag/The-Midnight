package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.common.item.SporeBombItem;
import com.mushroom.midnight.common.item.SporeBombItem.Type;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class SporeBombEntity extends ThrowableEntity {
    private static final DataParameter<ItemStack> BOMB_STACK = EntityDataManager.createKey(SporeBombEntity.class, DataSerializers.ITEMSTACK);

    public SporeBombEntity(World world) {
        super(world);
    }

    public SporeBombEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public SporeBombEntity(World world, LivingEntity thrower) {
        super(world, thrower);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(BOMB_STACK, new ItemStack(MidnightItems.SPORE_BOMB));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("bomb_stack", getBombStack().write(new CompoundNBT()));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("bomb_stack", Constants.NBT.TAG_COMPOUND)) {
            setBombStack(ItemStack.read(compound.getCompound("bomb_stack")));
        }
    }

    public ItemStack getBombStack() {
        return this.dataManager.get(BOMB_STACK);
    }

    public void setBombStack(ItemStack bombStack) {
        this.dataManager.set(BOMB_STACK, bombStack);
    }

    @Override
    public ITextComponent getName() {
        if (hasCustomName()) {
            return getCustomName();
        } else {
            return getBombStack().getDisplayName();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (isInWater()) {
                entityDropItem(new ItemStack(MidnightItems.DARK_PEARL), 0.1f);
                remove();
            } else if (SporeBombItem.checkExplode(this.world, getBombStack())) {
                SporeBombItem.explode(Type.fromStack(getBombStack()), this.world, this.posX, this.posY, this.posZ);
                remove();
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
                SporeBombItem.explode(Type.fromStack(getBombStack()), this.world, this.posX, this.posY, this.posZ);
            } else {
                entityDropItem(getBombStack().copy(), 0.01f);
            }
            remove();
        }
    }

    private boolean canBreakOn(@Nullable BlockPos impactedPos) {
        if (impactedPos == null) {
            return false;
        }

        BlockState impactedState = this.world.getBlockState(impactedPos);
        return impactedState.getMaterial() == Material.ROCK || impactedState.getMaterial() == Material.IRON;
    }
}
