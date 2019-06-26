package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.biome.BiomeLayerSampler;
import com.mushroom.midnight.common.biome.BiomeLayerType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

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

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable Direction facing) {
        return capability == Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP) {
            return Midnight.MULTI_LAYER_BIOME_SAMPLER_CAP.cast(this);
        }
        return null;
    }
}
