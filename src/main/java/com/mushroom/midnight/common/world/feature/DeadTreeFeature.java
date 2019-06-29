package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.function.Function;

public class DeadTreeFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_1"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_2"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_3"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_4"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_5"),
            new ResourceLocation(Midnight.MODID, "trees/dead_tree_6")
    };

    private static final BlockState LOG = MidnightBlocks.DEAD_WOOD_LOG.getDefaultState();
    private static final BlockState LEAVES = Blocks.AIR.getDefaultState();

    private final Block[] shelfBlocks;

    public DeadTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize, Block[] shelfBlocks) {
        super(deserialize, TEMPLATES, LOG, LEAVES);
        this.shelfBlocks = shelfBlocks;
    }

    @Override
    protected TemplateCompiler buildCompiler() {
        return super.buildCompiler()
                .withPostProcessor(new ShelfAttachProcessor(AbstractTreeFeature::isAirOrLeaves, this.shelfBlocks));
    }
}
