package com.mushroom.midnight.common.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CrystalClusterConfig implements IFeatureConfig {
    public final BlockState rock;
    public final BlockState crystal;

    public CrystalClusterConfig(BlockState rock, BlockState crystal) {
        this.rock = rock;
        this.crystal = crystal;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(
                ImmutableMap.of(
                        ops.createString("rock"), BlockState.serialize(ops, this.rock).getValue(),
                        ops.createString("crystal"), BlockState.serialize(ops, this.crystal).getValue()
                )
        ));
    }

    public static <T> CrystalClusterConfig deserialize(Dynamic<T> dynamic) {
        BlockState log = dynamic.get("rock").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        BlockState leaf = dynamic.get("crystal").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        return new CrystalClusterConfig(log, leaf);
    }
}
