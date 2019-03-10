package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.entity.projectile.EntitySporeBomb;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

public class ItemSporeBomb extends Item {
    public enum BombType {
        NIGHTSHROOM, DEWSHROOM, VIRIDSHROOM, BOGSHROOM;
    }

    public ItemSporeBomb() {
        setCreativeTab(ModTabs.MIDNIGHT_ITEMS);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new ItemSporeBomb.DispenserBehavior());
        hasSubtypes = true;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            Arrays.stream(BombType.values()).map(bomb -> new ItemStack(this, 1, bomb.ordinal())).forEach(items::add);
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + "." + BombType.values()[Math.min(stack.getMetadata(), BombType.values().length - 1)].name().toLowerCase();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            EntitySporeBomb bomb = new EntitySporeBomb(world, player);
            bomb.setBombType(heldItem.getMetadata());
            bomb.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(bomb);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
    }

    @SideOnly(Side.CLIENT)
    public void renderModel() {
        Arrays.stream(BombType.values()).forEach(bomb -> ModelLoader.setCustomModelResourceLocation(this, bomb.ordinal(), new ModelResourceLocation(getRegistryName() + "_" + bomb.ordinal(), "inventory")));
    }

    private static class DispenserBehavior extends BehaviorProjectileDispense {
        @Override
        protected IProjectile getProjectileEntity(World world, IPosition pos, ItemStack stack) {
            return new EntitySporeBomb(world, pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
