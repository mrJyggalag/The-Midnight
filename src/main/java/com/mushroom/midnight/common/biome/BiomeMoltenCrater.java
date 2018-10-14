package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeMoltenCrater extends BiomeBase {
    public BiomeMoltenCrater(BiomeProperties properties) {
        super(properties);

        this.topBlock = ModBlocks.TRENCHSTONE.getDefaultState();
        this.fillerBlock = ModBlocks.TRENCHSTONE.getDefaultState();
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 16; i++) {
            int offsetX = rand.nextInt(16) + 8;
            int offsetZ = rand.nextInt(16) + 8;
            mutablePos.setPos(pos.getX() + offsetX, 0, pos.getZ() + offsetZ);

            BlockPos surface = world.getTopSolidOrLiquidBlock(mutablePos);
            mutablePos.setY(surface.getY() - 1);

            IBlockState miasmaState = ModBlocks.MIASMA.getDefaultState();
            world.setBlockState(mutablePos, miasmaState, 2);
            world.immediateBlockTick(mutablePos, miasmaState, rand);
        }

        super.decorate(world, rand, pos);
    }

    @Override
    protected IBlockState chooseTopBlock(Random random) {
        if (random.nextInt(5) == 0) {
            return ModBlocks.MIASMA_SURFACE.getDefaultState();
        }
        return ModBlocks.TRENCHSTONE.getDefaultState();
    }

    @Override
    public float getRidgeWeight() {
        return 0.0F;
    }
}
