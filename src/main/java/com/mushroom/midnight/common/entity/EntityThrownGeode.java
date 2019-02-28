package com.mushroom.midnight.common.entity;

import com.mushroom.midnight.common.item.ItemGeode;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrownGeode extends EntityThrowable {
    private static final byte POPPED_STATE_ID = 3;

    public EntityThrownGeode(World world) {
        super(world);
    }

    public EntityThrownGeode(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == POPPED_STATE_ID) {
            this.spawnPopParticles();
        }
    }

    private void spawnPopParticles() {
        for (int i = 0; i < 8; i++) {
            double velX = (this.rand.nextDouble() - 0.5) * 0.1;
            double velY = (this.rand.nextDouble() - 0.5) * 0.1;
            this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY + 0.05, this.posZ, velX, 0.1, velY, Item.getIdFromItem(ModItems.GEODE));
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            DamageSource source = DamageSource.causeThrownDamage(this, this.getThrower());
            result.entityHit.attackEntityFrom(source, 1.0F);
        }

        if (!this.world.isRemote) {
            IBlockState impactedState = this.world.getBlockState(result.getBlockPos());
            if (ItemGeode.canBreakOn(impactedState)) {
                this.entityDropItem(ItemGeode.getBrokenStack(), 0.1F);
                this.world.setEntityState(this, POPPED_STATE_ID);
            } else {
                this.dropItem(ModItems.GEODE, 1);
            }

            this.setDead();
        }
    }
}
