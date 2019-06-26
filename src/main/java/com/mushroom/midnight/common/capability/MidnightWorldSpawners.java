package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.biome.surface.SurfaceBiome;
import com.mushroom.midnight.common.registry.MidnightSurfaceBiomes;
import com.mushroom.midnight.common.world.MidnightEntitySpawner;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public interface MidnightWorldSpawners extends ICapabilityProvider {
    void populateChunk(int chunkX, int chunkZ, Random random);

    void spawnAroundPlayers();

    @Override
    default boolean hasCapability(@Nonnull Capability<?> capability, @Nullable Direction facing) {
        return capability == Midnight.WORLD_SPAWNERS_CAP;
    }

    @Nullable
    @Override
    default <T> T getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == Midnight.WORLD_SPAWNERS_CAP) {
            return Midnight.WORLD_SPAWNERS_CAP.cast(this);
        }
        return null;
    }

    class Void implements MidnightWorldSpawners {
        @Override
        public void populateChunk(int chunkX, int chunkZ, Random random) {
        }

        @Override
        public void spawnAroundPlayers() {
        }
    }

    class SurfaceAndCave implements MidnightWorldSpawners {
        private final ServerWorld world;
        private final MidnightEntitySpawner<SurfaceBiome> biomeEntitySpawner;
        private final MidnightEntitySpawner<CavernousBiome> cavernEntitySpawner;

        public SurfaceAndCave(ServerWorld world) {
            this.world = world;

            this.biomeEntitySpawner = new MidnightEntitySpawner<>(this::getSurfaceSpawnBiome, SurfaceBiome.PlacementLevel.INSTANCE);
            this.cavernEntitySpawner = new MidnightEntitySpawner<>(this::getCavernSpawnBiome, CavernousBiome.PlacementLevel.INSTANCE);
        }

        private SurfaceBiome getSurfaceSpawnBiome(BlockPos pos) {
            Biome biome = this.world.getBiome(pos);
            if (biome instanceof SurfaceBiome) {
                return (SurfaceBiome) biome;
            }
            return (SurfaceBiome) MidnightSurfaceBiomes.NIGHT_PLAINS;
        }

        private CavernousBiome getCavernSpawnBiome(BlockPos pos) {
            return CavernousBiomeStore.getBiome(this.world, pos.getX(), pos.getZ());
        }

        @Override
        public void populateChunk(int chunkX, int chunkZ, Random random) {
            this.biomeEntitySpawner.populateChunk(this.world, chunkX, chunkZ, random);
            this.cavernEntitySpawner.populateChunk(this.world, chunkX, chunkZ, random);
        }

        @Override
        public void spawnAroundPlayers() {
            this.biomeEntitySpawner.spawnAroundPlayers(this.world);
            this.cavernEntitySpawner.spawnAroundPlayers(this.world);
        }
    }
}
