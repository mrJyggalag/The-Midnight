package com.mushroom.midnight.common.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.ArrayList;
import java.util.Arrays;

public class TemplateTreeConfig implements IFeatureConfig {
    public final ResourceLocation[] templates;
    public final BlockState log;
    public final BlockState leaf;

    public TemplateTreeConfig(ResourceLocation[] templates, BlockState log, BlockState leaf) {
        this.templates = templates;
        this.log = log;
        this.leaf = leaf;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(
                ImmutableMap.of(
                        ops.createString("templates"), ops.createList(Arrays.stream(this.templates).map(t -> ops.createString(t.toString()))),
                        ops.createString("log"), BlockState.serialize(ops, this.log).getValue(),
                        ops.createString("leaf"), BlockState.serialize(ops, this.leaf).getValue()
                )
        ));
    }

    public static <T> TemplateTreeConfig deserialize(Dynamic<T> dynamic) {
        ResourceLocation[] templates = dynamic.get("templates")
                .asListOpt(t -> new ResourceLocation(t.asString("minecraft:none")))
                .orElse(new ArrayList<>())
                .toArray(new ResourceLocation[0]);

        BlockState log = dynamic.get("log").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        BlockState leaf = dynamic.get("leaf").map(BlockState::deserialize)
                .orElse(Blocks.AIR.getDefaultState());

        return new TemplateTreeConfig(templates, log, leaf);
    }
}
