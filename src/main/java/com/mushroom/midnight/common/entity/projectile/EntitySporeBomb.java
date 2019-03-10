package com.mushroom.midnight.common.entity.projectile;

import com.mushroom.midnight.common.item.ItemSporeBomb;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntitySporeBomb extends EntityThrowable {
    private static final DataParameter<Integer> BOMB_TYPE = EntityDataManager.createKey(EntitySporeBomb.class, DataSerializers.VARINT);

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
        this.dataManager.register(BOMB_TYPE, 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("bomb_type", getBombType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setBombType(compound.getInteger("bomb_type"));
    }

    public int getBombType() {
        return this.dataManager.get(BOMB_TYPE);
    }

    public void setBombType(int bombType) {
        this.dataManager.set(BOMB_TYPE, bombType);
    }

    @Override
    public String getName() {
        if (hasCustomName()) {
            return getCustomNameTag();
        } else {
            return I18n.translateToLocal("item.midnight.spore_bomb." + ItemSporeBomb.BombType.values()[getBombType()].name().toLowerCase() + ".name");
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (isInWater()) {
            entityDropItem(new ItemStack(ModItems.DARK_PEARL), 0.1f);
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
                // explode directly
            } else {
                // drop bomb fusing
            }
            setDead();
        }
    }

    private boolean canBreakOn(@Nullable BlockPos impactedPos) {
        IBlockState impactedState;
        return impactedPos != null && ((impactedState = this.world.getBlockState(impactedPos)).getMaterial() == Material.ROCK || impactedState.getMaterial() == Material.IRON);
    }
}
