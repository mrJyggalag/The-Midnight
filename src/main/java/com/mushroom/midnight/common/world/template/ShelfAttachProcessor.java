package com.mushroom.midnight.common.world.template;

import com.mushroom.midnight.common.block.BlockMidnightFungiShelf;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.util.BlockStatePredicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ShelfAttachProcessor implements TemplatePostProcessor {
    private static final EnumFacing[] ATTACH_SIDES = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP };

    private static final Block[] SHELF_BLOCKS = new Block[] { ModBlocks.NIGHTSHROOM_SHELF, ModBlocks.DEWSHROOM_SHELF, ModBlocks.VIRIDSHROOM_SHELF };

    private final int attachChance;
    private final BlockStatePredicate replaceable;

    public ShelfAttachProcessor(int attachChance, BlockStatePredicate replaceable) {
        this.attachChance = attachChance;
        this.replaceable = replaceable;
    }

    public ShelfAttachProcessor(BlockStatePredicate replaceable) {
        this(6, replaceable);
    }

    @Override
    public void process(World world, Random random, BlockPos pos, IBlockState state) {
        if (state.isFullCube() && random.nextInt(this.attachChance) == 0) {
            this.attachShelf(world, random, pos);
        }
    }

    private void attachShelf(World world, Random random, BlockPos pos) {
        Block shelfBlock = SHELF_BLOCKS[random.nextInt(SHELF_BLOCKS.length)];
        EnumFacing attachSide = ATTACH_SIDES[random.nextInt(ATTACH_SIDES.length)];

        BlockPos offsetPos = pos.offset(attachSide);
        if (this.replaceable.test(world, offsetPos)) {
            world.setBlockState(offsetPos, shelfBlock.getDefaultState()
                    .withProperty(BlockMidnightFungiShelf.FACING, attachSide)
            );
        }
    }
}
