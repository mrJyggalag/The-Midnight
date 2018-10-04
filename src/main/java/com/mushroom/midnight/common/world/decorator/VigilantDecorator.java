package com.mushroom.midnight.common.world.decorator;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class VigilantDecorator extends BiomeDecorator {

    private final WorldGenLitPumpkins worldGenPumpkin = new WorldGenLitPumpkins();

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
        super.genDecorations(biomeIn, worldIn, random);

        int randX = random.nextInt(16) + 8;
        int randZ = random.nextInt(16) + 8;
        int randY = worldIn.getHeight(this.chunkPos.add(randX, 0, randZ)).getY();
        this.worldGenPumpkin.generate(worldIn, random, this.chunkPos.add(randX, randY, randZ));
    }
}
