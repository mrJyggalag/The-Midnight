package com.mushroom.midnight.common.world.feature;

import com.mojang.datafixers.Dynamic;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import com.mushroom.midnight.common.world.template.TemplateCompiler;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.function.Function;

public class LargeFungiFeature extends TemplateTreeFeature {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/mushroom_flat_1"),
            new ResourceLocation(Midnight.MODID, "mushroom/mushroom_flat_2"),
            new ResourceLocation(Midnight.MODID, "mushroom/mushroom_slant_1"),
            new ResourceLocation(Midnight.MODID, "mushroom/mushroom_slant_2")
    };

    public LargeFungiFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize, BlockState stem, BlockState hat) {
        super(deserialize, TEMPLATES, stem, hat);

        this.setSapling((IPlantable) MidnightBlocks.NIGHTSHROOM);
    }

    @Override
    protected TemplateCompiler buildCompiler() {
        return super.buildCompiler()
                .withPostProcessor(new ShelfAttachProcessor(this::canPlaceShelf, ShelfAttachProcessor.FOREST_SHELF_BLOCKS));
    }

    @Override
    protected void processMarker(IWorld world, BlockPos pos, String key) {
        if (key.equals("inside")) {
            this.setBlockState(world, pos, MidnightBlocks.FUNGI_INSIDE.getDefaultState());
        }
        super.processMarker(world, pos, key);
    }

    private boolean canPlaceShelf(IWorld world, BlockPos pos) {
        if (World.isOutsideBuildHeight(pos)) {
            return false;
        }

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == MidnightBlocks.FUNGI_INSIDE) {
            return false;
        }

        return state.getBlock().isAir(state, world, pos) || state.isIn(BlockTags.LEAVES) || state.getMaterial() == Material.PLANTS;
    }
}
