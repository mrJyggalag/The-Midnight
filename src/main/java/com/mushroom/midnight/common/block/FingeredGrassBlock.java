package com.mushroom.midnight.common.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.Random;

public class FingeredGrassBlock extends MidnightPlantBlock {
    public FingeredGrassBlock() {
        super(false);
        setTickRandomly(true);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(10) == 0) {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (double)(pos.getX() + rand.nextFloat()), pos.getY() + 1.1D, (double)(pos.getZ() + rand.nextFloat()), 0D, 0D, 0D);
        }
    }
}
