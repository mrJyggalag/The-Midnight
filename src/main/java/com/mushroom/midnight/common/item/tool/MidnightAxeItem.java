package com.mushroom.midnight.common.item.tool;

import com.google.common.collect.ImmutableMap;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MidnightAxeItem extends AxeItem {
    private static final ImmutableMap<Block, Block> STRIPPABLE_BLOCKS;

    static {
        ImmutableMap.Builder<Block, Block> builder = new ImmutableMap.Builder<>();
        builder.put(MidnightBlocks.SHADOWROOT_LOG, MidnightBlocks.SHADOWROOT_STRIPPED_LOG);
        builder.put(MidnightBlocks.DARK_WILLOW_LOG, MidnightBlocks.DARK_WILLOW_STRIPPED_LOG);
        builder.put(MidnightBlocks.DEAD_WOOD_LOG, MidnightBlocks.DEAD_WOOD_STRIPPED_LOG);
        STRIPPABLE_BLOCKS = builder.build();
    }

    public MidnightAxeItem(IItemTier tier, Properties properties) {
        super(tier, 6f, -3.2f, properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        Block strippedBlock = STRIPPABLE_BLOCKS.getOrDefault(block, null);
        if (strippedBlock == null) {
            strippedBlock = BLOCK_STRIPPING_MAP.getOrDefault(block, null);
        }
        if (strippedBlock != null) {
            world.playSound(context.getPlayer(), pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1f, 1f);
            if (!world.isRemote) {
                world.setBlockState(pos, strippedBlock.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
                if (context.getPlayer() != null) {
                    context.getItem().damageItem(1, context.getPlayer(), player -> player.sendBreakAnimation(context.getHand()));
                }
            }

            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }
}
