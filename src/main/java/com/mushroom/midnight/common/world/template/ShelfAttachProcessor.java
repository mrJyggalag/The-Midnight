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
    protected static final EnumFacing[] ATTACH_SIDES = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP };

    public static final Block[] FOREST_SHELF_BLOCKS = new Block[] { ModBlocks.NIGHTSHROOM_SHELF, ModBlocks.DEWSHROOM_SHELF, ModBlocks.VIRIDSHROOM_SHELF };
    public static final Block[] SHELF_BLOCKS = new Block[] {
            ModBlocks.NIGHTSHROOM_SHELF,
            ModBlocks.VIRIDSHROOM_SHELF,
            ModBlocks.DEWSHROOM_SHELF,
            ModBlocks.BOGSHROOM_SHELF
    };

    private final int attachChance;
    private final BlockStatePredicate replaceable;
    private final Block[] shelfBlocks;

    public ShelfAttachProcessor(int attachChance, BlockStatePredicate replaceable, Block[] shelfBlocks) {
        this.attachChance = attachChance;
        this.replaceable = replaceable;
        this.shelfBlocks = shelfBlocks;
    }

    public ShelfAttachProcessor(BlockStatePredicate replaceable, Block[] shelfBlocks) {
        this(6, replaceable, shelfBlocks);
    }

    @Override
    public void process(World world, Random random, BlockPos pos, IBlockState state) {
        if (state.isFullCube() && random.nextInt(this.attachChance) == 0) {
            this.attachShelf(world, random, pos);
        }
    }

    protected void attachShelf(World world, Random random, BlockPos pos) {
        Block shelfBlock = this.shelfBlocks[random.nextInt(this.shelfBlocks.length)];
        EnumFacing attachSide = ATTACH_SIDES[random.nextInt(ATTACH_SIDES.length)];

        BlockPos offsetPos = pos.offset(attachSide);
        if (this.replaceable.test(world, offsetPos)) {
            world.setBlockState(offsetPos, shelfBlock.getDefaultState()
                    .withProperty(BlockMidnightFungiShelf.FACING, attachSide)
            );
        }
    }
}
