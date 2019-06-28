package com.mushroom.midnight.common.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class LogAndLeafConfig implements IFeatureConfig {
    public final BlockState log;
    public final BlockState leaf;

    public LogAndLeafConfig(BlockState log, BlockState leaf) {
        this.log = log;
        this.leaf = leaf;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(
                ImmutableMap.of(
                        ops.createString("log"), BlockState.serialize(ops, this.log).getValue(),
                        ops.createString("leaf"), BlockState.serialize(ops, this.leaf).getValue()
                )
        ));
    }

    public static <T> LogAndLeafConfig deserialize(Dynamic<T> dynamic) {
        BlockState log = dynamic.get("log").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        BlockState leaf = dynamic.get("leaf").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        return new LogAndLeafConfig(log, leaf);
    }
}
