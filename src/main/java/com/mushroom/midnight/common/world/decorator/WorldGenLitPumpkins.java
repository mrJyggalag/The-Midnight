package com.mushroom.midnight.common.world.decorator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLitPumpkins extends WorldGenerator {

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if(rand.nextInt(8) == 0){
            if (worldIn.isAirBlock(position) && (!worldIn.provider.isNether() || position.getY() < 255)) {
                worldIn.setBlockState(position, Blocks.LIT_PUMPKIN.getDefaultState(), 2);
            }
        }
        return true;
    }
}
