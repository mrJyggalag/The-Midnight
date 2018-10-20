package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MiasmaSourceFeature extends MidnightAbstractFeature{
    @Override
    public boolean generate(World world, Random rand, BlockPos origin) {
        origin = origin.down();

        IBlockState miasmaState = ModBlocks.MIASMA.getDefaultState();
        world.setBlockState(origin, miasmaState, 2);
        world.immediateBlockTick(origin, miasmaState, rand);

        return true;
    }
}
