package com.mushroom.midnight.common.world.decorator;

import java.util.Random;

import com.mushroom.midnight.common.registry.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLitPumpkins extends WorldGenerator {

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if(rand.nextInt(8) == 0){
            BlockPos finalPos = position;

            if(worldIn.getBlockState(finalPos.down()) == ModBlocks.SHADOWROOT_LEAVES){
                while(worldIn.getBlockState(finalPos.down()) == ModBlocks.SHADOWROOT_LEAVES || worldIn.getBlockState(finalPos.down()) == Blocks.AIR){
                    finalPos = finalPos.down();
                }
            }
            if (worldIn.isAirBlock(finalPos) && position.getY() < 255) {
                worldIn.setBlockState(finalPos, Blocks.LIT_PUMPKIN.getDefaultState(), 2);
            }
        }
        return true;
    }
}
