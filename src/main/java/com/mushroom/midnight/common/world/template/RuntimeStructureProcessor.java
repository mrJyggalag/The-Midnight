package com.mushroom.midnight.common.world.template;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;

public final class RuntimeStructureProcessor extends StructureProcessor {
    private final Function function;

    public RuntimeStructureProcessor(Function function) {
        this.function = function;
    }

    @Nullable
    @Override
    public Template.BlockInfo process(IWorldReader world, BlockPos pos, Template.BlockInfo srcInfo, Template.BlockInfo info, PlacementSettings settings) {
        return this.function.process(world, pos, srcInfo, info, settings);
    }

    @Override
    protected IStructureProcessorType getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected <T> Dynamic<T> serialize0(DynamicOps<T> dynamicOps) {
        throw new UnsupportedOperationException();
    }

    public interface Function {
        Template.BlockInfo process(IWorldReader world, BlockPos pos, Template.BlockInfo srcInfo, Template.BlockInfo info, PlacementSettings settings);
    }
}
