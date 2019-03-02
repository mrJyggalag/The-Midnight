package com.mushroom.midnight.common.item;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class ItemMidnightSeed extends Item implements IModelProvider {
    private final Supplier<IBlockState> plantSupplier;

    public ItemMidnightSeed(Supplier<IBlockState> plantSupplier) {
        this.plantSupplier = plantSupplier;
        this.setCreativeTab(ModTabs.MIDNIGHT_ITEMS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = world.getBlockState(pos).getBlock();
        if (!block.isReplaceable(world, pos)) {
            pos = pos.offset(facing);
        }
        ItemStack stack = player.getHeldItem(hand);
        IBlockState seedState = this.plantSupplier.get();
        if (!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack) && world.mayPlace(seedState.getBlock(), pos, false, facing, null)) {
            if (world.setBlockState(pos, seedState, 11)) {
                IBlockState state = world.getBlockState(pos);
                if (state.getBlock() == seedState.getBlock()) {
                    seedState.getBlock().onBlockPlacedBy(world, pos, state, player, stack);
                    if (player instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
                        SoundType soundtype = seedState.getBlock().getSoundType(seedState, world, pos, player);
                        world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        stack.shrink(1);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.FAIL;
    }
}
