package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.cavern.CavernousBiome;
import com.mushroom.midnight.common.registry.MidnightCavernousBiomes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CavernousBiomeStore implements ICapabilitySerializable<CompoundNBT> {
    private final CavernousBiome[] biomes = new CavernousBiome[256];

    public static CavernousBiome getBiome(World world, int x, int z) {
        Chunk chunk = world.getChunk(x >> 4, z >> 4);

        return chunk.getCapability(Midnight.CAVERNOUS_BIOME_CAP)
                .map(store -> store.getBiome(x & 15, z & 15))
                .orElse(MidnightCavernousBiomes.CLOSED_CAVERN);
    }

    public void populate(CavernousBiome[] biomes) {
        System.arraycopy(biomes, 0, this.biomes, 0, biomes.length);
    }

    @Nonnull
    public CavernousBiome getBiome(int x, int z) {
        CavernousBiome biome = this.biomes[(x & 15) + (z & 15) * 16];
        if (biome == null) {
            return MidnightCavernousBiomes.CLOSED_CAVERN;
        }
        return biome;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();

        byte[] bytes = new byte[this.biomes.length];
        for (int i = 0; i < this.biomes.length; i++) {
            CavernousBiome biome = this.biomes[i];
            if (biome != null) {
                bytes[i] = (byte) (MidnightCavernousBiomes.getId(biome) & 0xFF);
            }
        }

        compound.putByteArray("biomes", bytes);

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        ForgeRegistry<CavernousBiome> registry = MidnightCavernousBiomes.getRegistry();

        byte[] bytes = compound.getByteArray("biomes");
        for (int i = 0; i < bytes.length; i++) {
            this.biomes[i] = registry.getValue(bytes[i] & 0xFF);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return Midnight.CAVERNOUS_BIOME_CAP.orEmpty(cap, LazyOptional.of(() -> this));
    }
}
