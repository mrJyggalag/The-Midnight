package com.mushroom.midnight.common.world.template;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CompiledTemplate {
    private final Template template;
    private final PlacementSettings settings;

    private final BlockPos origin;
    private final ITemplateProcessor processor;
    private final TemplateCompiler.IDataProcessor dataProcessor;

    private final Map<BlockPos, String> dataBlocks;

    CompiledTemplate(Template template, PlacementSettings settings, BlockPos origin, ITemplateProcessor processor, TemplateCompiler.IDataProcessor dataProcessor) {
        this.template = template;
        this.settings = settings;
        this.origin = origin;

        this.processor = processor != null ? processor : new BlockRotationProcessor(origin, settings);
        this.dataProcessor = dataProcessor;

        this.dataBlocks = this.template.getDataBlocks(origin, settings);
    }

    public void addTo(World world, int flags) {
        this.template.addBlocksToWorld(world, this.origin, this.processor, this.settings, flags);

        if (this.dataProcessor != null) {
            for (Map.Entry<BlockPos, String> entry : this.dataBlocks.entrySet()) {
                this.dataProcessor.process(world, entry.getKey(), entry.getValue());
            }
        }
    }

    @Nullable
    public BlockPos lookupAny(String key) {
        for (Map.Entry<BlockPos, String> entry : this.dataBlocks.entrySet()) {
            if (entry.getValue().equals(key)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Collection<BlockPos> lookup(String key) {
        Collection<BlockPos> result = new ArrayList<>();
        for (Map.Entry<BlockPos, String> entry : this.dataBlocks.entrySet()) {
            if (entry.getValue().equals(key)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "CompiledTemplate{" +
                "template=" + this.template +
                ", origin=" + this.origin +
                '}';
    }
}
