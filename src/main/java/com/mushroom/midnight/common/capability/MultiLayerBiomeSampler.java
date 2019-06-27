package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.BiomeLayerType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MultiLayerBiomeSampler implements ICapabilityProvider {
    private final Map<BiomeLayerType<?>, BiomeLayerSampler<?>> layers = new HashMap<>();

    public <T> void put(BiomeLayerType<T> layerType, BiomeLayerSampler<T> sampler) {
        this.layers.put(layerType, sampler);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> BiomeLayerSampler<T> getLayer(BiomeLayerType<T> layerType) {
        return (BiomeLayerSampler<T>) this.layers.get(layerType);
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP.orEmpty(capability, LazyOptional.of(() -> this));
    }
}
