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
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.function.Function;

public class LargeBogshroomFeature extends TemplateTreeFeature implements IWorldGenerator {
    private static final ResourceLocation[] TEMPLATES = new ResourceLocation[] {
            new ResourceLocation(Midnight.MODID, "mushroom/bogshroom")
    };

    private static final BlockState STEM = MidnightBlocks.BOGSHROOM_STEM.getDefaultState();
    private static final BlockState HAT = MidnightBlocks.BOGSHROOM_HAT.getDefaultState();

    public LargeBogshroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize) {
        super(deserialize, TEMPLATES, STEM, HAT);
    }

    @Override
    protected TemplateCompiler buildCompiler() {
        return super.buildCompiler()
                .withPostProcessor(new ShelfAttachProcessor(this::canPlaceShelf, ShelfAttachProcessor.SHELF_BLOCKS));
    }

    @Override
    protected void processMarker(IWorld world, BlockPos pos, String key) {
        if (key.equals("inside")) {
            this.setBlockState(world, pos, MidnightBlocks.MUSHROOM_INSIDE.getDefaultState());
        }
        super.processMarker(world, pos, key);
    }

    private boolean canPlaceShelf(IWorld world, BlockPos pos) {
        if (World.isOutsideBuildHeight(pos)) {
            return false;
        }

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == MidnightBlocks.MUSHROOM_INSIDE) {
            return false;
        }

        return state.getBlock().isAir(state, world, pos) || state.isIn(BlockTags.LEAVES) || state.getMaterial() == Material.PLANTS;
    }
}
