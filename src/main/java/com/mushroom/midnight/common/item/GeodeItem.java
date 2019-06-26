package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.entity.projectile.ThrownGeodeEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GeodeItem extends Item {
    public GeodeItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerDispenseBehavior(this, new GeodeItem.DispenserBehavior());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!player.abilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            ThrownGeodeEntity geode = new ThrownGeodeEntity(world, player);
            geode.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.addEntity(geode);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, heldItem);
    }

    private static class DispenserBehavior extends ProjectileDispenseBehavior {
        @Override
        protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack stack) {
            return new ThrownGeodeEntity(world, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
