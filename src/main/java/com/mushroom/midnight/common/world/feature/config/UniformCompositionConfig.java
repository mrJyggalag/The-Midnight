package com.mushroom.midnight.common.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class UniformCompositionConfig implements IFeatureConfig {
    public final BlockState state;

    public UniformCompositionConfig(BlockState state) {
        this.state = state;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(
                ImmutableMap.of(
                        ops.createString("state"), BlockState.serialize(ops, this.state).getValue()
                )
        ));
    }

    public static <T> UniformCompositionConfig deserialize(Dynamic<T> dynamic) {
        BlockState state = dynamic.get("state").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        return new UniformCompositionConfig(state);
    }
}
