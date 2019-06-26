package com.mushroom.midnight.common.item;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MidnightSeedItem extends Item {
    private final Supplier<BlockState> plantSupplier;

    public MidnightSeedItem(Supplier<BlockState> plantSupplier) {
        this.plantSupplier = plantSupplier;
        this.setCreativeTab(MidnightItemGroups.ITEMS);
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World world, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
        Block block = world.getBlockState(pos).getBlock();
        if (!block.isReplaceable(world, pos)) {
            pos = pos.offset(facing);
        }
        ItemStack stack = player.getHeldItem(hand);
        BlockState seedState = this.plantSupplier.get();
        if (!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack) && world.mayPlace(seedState.getBlock(), pos, false, facing, null)) {
            if (world.setBlockState(pos, seedState, 11)) {
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() == seedState.getBlock()) {
                    seedState.getBlock().onBlockPlacedBy(world, pos, state, player, stack);
                    if (player instanceof ServerPlayerEntity) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
                        SoundType soundtype = seedState.getBlock().getSoundType(seedState, world, pos, player);
                        world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        stack.shrink(1);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.FAIL;
    }
}
