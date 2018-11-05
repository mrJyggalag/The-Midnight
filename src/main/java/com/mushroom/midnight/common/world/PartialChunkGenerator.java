package com.mushroom.midnight.common.world;

import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public interface PartialChunkGenerator extends IChunkGenerator {
    void primeChunk(ChunkPrimer primer, int chunkX, int chunkZ);

    void primeChunkBare(ChunkPrimer primer, int chunkX, int chunkZ);
}
