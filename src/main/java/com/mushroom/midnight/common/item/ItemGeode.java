package com.mushroom.midnight.common.item;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.entity.EntityThrownGeode;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGeode extends Item implements IModelProvider {
    public ItemGeode() {
        super();
        this.setCreativeTab(ModTabs.MIDNIGHT_ITEMS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);

        if (canBreakOn(state)) {
            ItemStack heldItem = player.getHeldItem(hand);
            if (!player.capabilities.isCreativeMode) {
                heldItem.shrink(1);
            }

            ItemStack brokenStack = getBrokenStack();
            if (!player.addItemStackToInventory(brokenStack)) {
                player.dropItem(brokenStack, false);
            }

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            heldItem.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            EntityThrownGeode geode = new EntityThrownGeode(world, player);
            geode.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(geode);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
    }

    public static ItemStack getBrokenStack() {
        return new ItemStack(ModItems.DARK_PEARL);
    }

    public static boolean canBreakOn(IBlockState state) {
        // TODO: Migrate to tags with 1.13
        return state.getMaterial() == Material.ROCK || state.getMaterial() == Material.IRON;
    }
}
