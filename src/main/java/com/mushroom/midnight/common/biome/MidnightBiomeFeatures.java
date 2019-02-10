package com.mushroom.midnight.common.biome;

import com.mushroom.midnight.common.block.BlockBladeshroom;
import com.mushroom.midnight.common.block.BlockPileOfEggs;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.world.feature.BoulderFeature;
import com.mushroom.midnight.common.world.feature.CrystalClusterFeature;
import com.mushroom.midnight.common.world.feature.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.DeadLogFeature;
import com.mushroom.midnight.common.world.feature.DeadTreeFeature;
import com.mushroom.midnight.common.world.feature.DoubleFungiFeature;
import com.mushroom.midnight.common.world.feature.DoublePlantFeature;
import com.mushroom.midnight.common.world.feature.FungiFeature;
import com.mushroom.midnight.common.world.feature.GourdFeature;
import com.mushroom.midnight.common.world.feature.HeapFeature;
import com.mushroom.midnight.common.world.feature.IMidnightFeature;
import com.mushroom.midnight.common.world.feature.LargeFungiFeature;
import com.mushroom.midnight.common.world.feature.MidnightAbstractFeature;
import com.mushroom.midnight.common.world.feature.MossFeature;
import com.mushroom.midnight.common.world.feature.PlantFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;
import com.mushroom.midnight.common.world.feature.SpikeFeature;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MidnightBiomeFeatures {
    public static final IMidnightFeature SHADOWROOT_TREE_FEATURE = new ShadowrootTreeFeature();
    public static final IMidnightFeature DARK_WILLOW_TREE_FEATURE = new DarkWillowTreeFeature();

    public static final IMidnightFeature DEAD_TREE_FEATURE = new DeadTreeFeature();
    public static final IMidnightFeature DEAD_LOG_FEATURE = new DeadLogFeature();

    public static final IMidnightFeature TALL_GRASS_FEATURE = new PlantFeature(
            ModBlocks.TALL_MIDNIGHT_GRASS.getDefaultState(),
            ((BlockBush) ModBlocks.TALL_MIDNIGHT_GRASS)::canBlockStay
    );

    public static final IMidnightFeature DOUBLE_GRASS_FEATURE = new DoublePlantFeature(
            ModBlocks.DOUBLE_MIDNIGHT_GRASS.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_MIDNIGHT_GRASS.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature LUMEN_FEATURE = new PlantFeature(
            ModBlocks.LUMEN_BUD.getDefaultState(),
            ((BlockBush) ModBlocks.LUMEN_BUD)::canBlockStay
    );

    public static final IMidnightFeature BLADESHROOM_FEATURE = new PlantFeature(
            ModBlocks.BLADESHROOM.getDefaultState().withProperty(BlockBladeshroom.STAGE, BlockBladeshroom.Stage.CAPPED),
            ((BlockBush) ModBlocks.BLADESHROOM)::canBlockStay
    );

    public static final IMidnightFeature SUAVIS_FEATURE = new GourdFeature(
            ModBlocks.SUAVIS.getDefaultState(),
            ModBlocks.MIDNIGHT_GRASS,
            2
    );

    public static final IMidnightFeature COMMON_SUAVIS_FEATURE = new GourdFeature(
            ModBlocks.SUAVIS.getDefaultState(),
            ModBlocks.MIDNIGHT_GRASS,
            1
    );

    public static final IMidnightFeature DECEITFUL_ALGAE_FEATURE = new PlantFeature(
            ModBlocks.DECEITFUL_ALGAE.getDefaultState(),
            (world, pos, state) -> ModBlocks.DECEITFUL_ALGAE.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature DECEITFUL_MOSS_FEATURE = new MossFeature(ModBlocks.DECEITFUL_MOSS.getDefaultState());

    public static final IMidnightFeature BOGWEED_FEATURE = new PlantFeature(
            ModBlocks.BOGWEED.getDefaultState(),
            ((BlockBush) ModBlocks.BOGWEED)::canBlockStay
    );

    public static final IMidnightFeature GHOST_PLANT_FEATURE = new PlantFeature(
            ModBlocks.GHOST_PLANT.getDefaultState(),
            ((BlockBush) ModBlocks.GHOST_PLANT)::canBlockStay
    );

    public static final IMidnightFeature FINGERED_GRASS_FEATURE = new PlantFeature(
            ModBlocks.FINGERED_GRASS.getDefaultState(),
            ((BlockBush) ModBlocks.FINGERED_GRASS)::canBlockStay
    );

    public static final IMidnightFeature VIOLEAF_FEATURE = new PlantFeature(
            ModBlocks.VIOLEAF.getDefaultState(),
            ((BlockBush) ModBlocks.VIOLEAF)::canBlockStay
    );

    public static final IMidnightFeature RUNEBUSH_FEATURE = new PlantFeature(
            ModBlocks.RUNEBUSH.getDefaultState(),
            ((BlockBush) ModBlocks.RUNEBUSH)::canBlockStay
    );

    public static final IMidnightFeature[] UNDERGROUND_FEATURES = new IMidnightFeature[] {
            new PlantFeature(ModBlocks.TENDRILWEED.getDefaultState(), ((BlockBush) ModBlocks.TENDRILWEED)::canBlockStay),
            new MidnightAbstractFeature() {
                @Override
                public boolean placeFeature(World world, Random random, BlockPos origin) {
                    if (world.getBlockState(origin.down()).isFullBlock()) {
                        world.setBlockState(origin, ModBlocks.STINGER_EGG.getDefaultState().withProperty(BlockPileOfEggs.EGGS, random.nextInt(4) + 1), 2 | 16);
                        return true;
                    }
                    return false;
                }
            }
    };

    public static final IMidnightFeature DOUBLE_LUMEN_FEATURE = new DoublePlantFeature(
            ModBlocks.DOUBLE_LUMEN_BUD.getDefaultState(),
            (world, pos, state) -> ModBlocks.DOUBLE_LUMEN_BUD.canPlaceBlockAt(world, pos)
    );

    public static final IMidnightFeature FUNGI_FEATURE = new FungiFeature();
    public static final IMidnightFeature DOUBLE_FUNGI_FEATURE = new DoubleFungiFeature();

    public static final IMidnightFeature CRYSTAL_FLOWER_FEATURE = new PlantFeature(
            ModBlocks.CRYSTAL_FLOWER.getDefaultState(),
            ((BlockBush) ModBlocks.CRYSTAL_FLOWER)::canBlockStay
    );

    public static final IMidnightFeature[] LARGE_FUNGI_FEATURES = new LargeFungiFeature[] {
            new LargeFungiFeature(
                    ModBlocks.DEWSHROOM_STEM.getDefaultState(),
                    ModBlocks.DEWSHROOM_HAT.getDefaultState()
            ),
            new LargeFungiFeature(
                    ModBlocks.NIGHTSHROOM_STEM.getDefaultState(),
                    ModBlocks.NIGHTSHROOM_HAT.getDefaultState()
            ),
            new LargeFungiFeature(
                    ModBlocks.VIRIDSHROOM_STEM.getDefaultState(),
                    ModBlocks.VIRIDSHROOM_HAT.getDefaultState()
            )
    };

    public static final IMidnightFeature BLOOMCRYSTAL_FEATURE = new CrystalClusterFeature(3, 4,
            ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
            ModBlocks.BLOOMCRYSTAL.getDefaultState()
    );

    public static final IMidnightFeature BLOOMCRYSTAL_SPIRE_FEATURE = new CrystalClusterFeature(4, 13,
            ModBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
            ModBlocks.BLOOMCRYSTAL.getDefaultState()
    );

    public static final IMidnightFeature ROUXE_FEATURE = new CrystalClusterFeature(3, 4,
            ModBlocks.ROUXE_ROCK.getDefaultState(),
            ModBlocks.ROUXE.getDefaultState()
    );

    public static final IMidnightFeature NIGHTSTONE_BOULDER_FEATURE = new BoulderFeature(2) {
        @Override
        protected IBlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random) {
            return ModBlocks.NIGHTSTONE.getDefaultState();
        }
    };
    public static final IMidnightFeature NIGHTSTONE_SPIKE_FEATURE = new SpikeFeature(ModBlocks.NIGHTSTONE.getDefaultState());

    public static final IMidnightFeature TRENCHSTONE_BOULDER_FEATURE = new BoulderFeature(2) {
        private final float radiusSquareIn = radius <= 1f ? 0f : (radius - 1f) * (radius - 1f);

        @Override
        protected IBlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random) {
            return dist <= radiusSquareIn && random.nextFloat() < 0.1f ? ModBlocks.ARCHAIC_ORE.getDefaultState() : ModBlocks.TRENCHSTONE.getDefaultState();
        }
    };

    public static final IMidnightFeature ROCKSHROOM_HEAP_FEATURE = new HeapFeature(ModBlocks.ROCKSHROOM.getDefaultState());
}
