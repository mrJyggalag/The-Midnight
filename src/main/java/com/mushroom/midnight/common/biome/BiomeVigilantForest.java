package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.entity.creature.EntityRifter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeVigilantForest extends BiomeBase {

    public BiomeVigilantForest(BiomeProperties properties) {
        super(properties);

        this.decorator.treesPerChunk = 8;
        this.decorator.grassPerChunk = 2;

        this.spawnableMonsterList.add(new SpawnListEntry(EntityRifter.class, 1, 0, 1));
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return rand.nextBoolean() ? SHADOWROOT_TREE_GEN : DARK_WILLOW_TREE_GEN;
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            if (rand.nextInt(2) == 0) {
                this.generateCoverPlant(world, rand, pos, 1, LUMEN_GENERATOR);
                this.generateCoverPlant(world, rand, pos, 1, DOUBLE_LUMEN_GENERATOR);
            }
        }

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.SHROOM)) {
            this.generateCoverPlant(world, rand, pos, 1, FUNGI_GENERATOR);
        }

        super.decorate(world, rand, pos);

        if (TerrainGen.decorate(world, rand, chunkPos, DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH)) {
            for (int i = 0; i < 6; i++) {
                int offsetX = rand.nextInt(16) + 8;
                int offsetZ = rand.nextInt(16) + 8;
                BlockPos treePos = world.getHeight(pos.add(offsetX, 0, offsetZ));
                DEAD_TREE_GENERATOR.generate(world, rand, treePos);
            }
        }
    }
}
