package com.mushroom.midnight.common.world.template;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompiledTemplate {
    private final ResourceLocation templateId;
    private final Template template;
    private final PlacementSettings settings;
    private final BlockPos origin;

    private final ITemplateProcessor processor;
    private final TemplateDataProcessor dataProcessor;
    private final Collection<TemplatePostProcessor> postProcessors;

    private final Map<BlockPos, String> dataBlocks;

    CompiledTemplate(
            ResourceLocation templateId,
            Template template, PlacementSettings settings, BlockPos origin,
            ITemplateProcessor processor,
            TemplateDataProcessor dataProcessor,
            Collection<TemplatePostProcessor> postProcessors
    ) {
        this.templateId = templateId;
        this.template = template;
        this.settings = settings;
        this.origin = origin;

        this.processor = processor != null ? processor : new BlockRotationProcessor(origin, settings);
        this.dataProcessor = dataProcessor;
        this.postProcessors = postProcessors;

        ImmutableMap.Builder<BlockPos, String> dataBuilder = ImmutableMap.builder();

        for (Template.BlockInfo info : this.template.func_215381_a(origin, settings, Blocks.STRUCTURE_BLOCK)) {
            if (info.nbt != null) {
                StructureMode mode = StructureMode.valueOf(info.nbt.getString("mode"));
                if (mode == StructureMode.DATA) {
                    String metadata = info.nbt.getString("metadata");
                    dataBuilder.put(info.pos, metadata);
                }
            }
        }

        this.dataBlocks = dataBuilder.build();
    }

    public void addTo(World world, Random random, int flags) {
        this.template.addBlocksToWorld(world, this.origin, this.processor, this.settings, flags);

        if (this.dataProcessor != null) {
            for (Map.Entry<BlockPos, String> entry : this.dataBlocks.entrySet()) {
                this.dataProcessor.process(world, entry.getKey(), entry.getValue());
            }
        }

        for (TemplatePostProcessor processor : this.postProcessors) {
            for (Template.BlockInfo info : this.template.blocks) {
                BlockPos pos = Template.transformedBlockPos(this.settings, info.pos).add(this.origin);
                Template.BlockInfo processedInfo = this.processor.processBlock(world, pos, info);
                if (processedInfo != null) {
                    processor.process(world, random, pos, processedInfo.blockState);
                }
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
        return this.dataBlocks.entrySet().stream()
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
