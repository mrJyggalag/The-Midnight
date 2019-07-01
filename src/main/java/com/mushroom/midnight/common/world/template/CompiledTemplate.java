package com.mushroom.midnight.common.world.template;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompiledTemplate {
    private final ResourceLocation templateId;
    private final Template template;
    private final PlacementSettings settings;
    private final BlockPos origin;

    private final TemplateMarkerProcessor markerProcessor;
    private final Collection<TemplatePostProcessor> postProcessors;

    private final Map<BlockPos, String> markerBlocks;

    CompiledTemplate(
            ResourceLocation templateId,
            Template template, PlacementSettings settings, BlockPos origin,
            TemplateMarkerProcessor markerProcessor,
            Collection<TemplatePostProcessor> postProcessors
    ) {
        this.templateId = templateId;
        this.template = template;
        this.settings = settings;
        this.origin = origin;

        this.markerProcessor = markerProcessor;
        this.postProcessors = postProcessors;

        this.markerBlocks = TemplateCompiler.collectDataMarkers(origin, settings, template);
    }

    public void addTo(IWorld world, Random random, int flags) {
        this.template.addBlocksToWorld(world, this.origin, this.settings, flags);

        if (this.markerProcessor != null) {
            for (Map.Entry<BlockPos, String> entry : this.markerBlocks.entrySet()) {
                this.markerProcessor.process(world, entry.getKey(), entry.getValue());
            }
        }

        for (TemplatePostProcessor processor : this.postProcessors) {
            List<Template.BlockInfo> blocks = Template.func_215387_a(world, this.origin, this.settings, this.settings.func_204764_a(this.template.blocks, this.origin));
            for (Template.BlockInfo info : blocks) {
                processor.process(world, random, info.pos, info.state);
            }
        }
    }

    @Nullable
    public BlockPos lookupAny(String key) {
        return this.lookupStream(key).findFirst().orElse(null);
    }

    public Collection<BlockPos> lookup(String key) {
        return this.lookupStream(key).collect(Collectors.toList());
    }

    public Stream<BlockPos> lookupStream(String key) {
        return this.markerBlocks.entrySet().stream()
                .filter(e -> e.getValue().equals(key))
                .map(Map.Entry::getKey);
    }

    @Override
    public String toString() {
        return "CompiledTemplate{" +
                "template=" + this.templateId +
                ", origin=" + this.origin +
                '}';
    }
}
