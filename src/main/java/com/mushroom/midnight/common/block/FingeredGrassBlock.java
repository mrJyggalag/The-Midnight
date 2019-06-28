package com.mushroom.midnight.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class FingeredGrassBlock extends MidnightPlantBlock {
    public FingeredGrassBlock() {
        super(false);
        setTickRandomly(true); // builder
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomTick(BlockState state, World world, BlockPos pos, Random rand) {
        super.randomTick(state, world, pos, rand);
        if (rand.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.MYCELIUM, (double)(pos.getX() + rand.nextFloat()), pos.getY() + 1.1D, (double)(pos.getZ() + rand.nextFloat()), 0D, 0D, 0D);
        }
    }
}
