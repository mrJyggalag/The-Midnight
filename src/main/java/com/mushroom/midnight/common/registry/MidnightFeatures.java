package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.AlgaeFeature;
import com.mushroom.midnight.common.world.feature.DarkWillowTreeFeature;
import com.mushroom.midnight.common.world.feature.DeadLogFeature;
import com.mushroom.midnight.common.world.feature.DeadTreeFeature;
import com.mushroom.midnight.common.world.feature.LargeBogshroomFeature;
import com.mushroom.midnight.common.world.feature.MossFeature;
import com.mushroom.midnight.common.world.feature.ShadowrootTreeFeature;
import com.mushroom.midnight.common.world.template.ShelfAttachProcessor;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ScatteredPlantFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Midnight.MODID)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightFeatures {
//    public static final IMidnightFeature LUMEN_FEATURE = new PlantFeature(
//            MidnightBlocks.LUMEN_BUD.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature BLADESHROOM_FEATURE = new PlantFeature(
//            MidnightBlocks.BLADESHROOM.getDefaultState().withProperty(BladeshroomBlock.STAGE, BladeshroomBlock.Stage.CAPPED),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature BOGWEED_FEATURE = new PlantFeature(
//            MidnightBlocks.BOGWEED.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature GHOST_PLANT_FEATURE = new PlantFeature(
//            MidnightBlocks.GHOST_PLANT.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature FINGERED_GRASS_FEATURE = new PlantFeature(
//            MidnightBlocks.FINGERED_GRASS.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature VIOLEAF_FEATURE = new PlantFeature(
//            MidnightBlocks.VIOLEAF.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature UNSTABLE_BUSH_FEATURE = new PlantFeature(
//            MidnightBlocks.UNSTABLE_BUSH.getDefaultState(),
//            GeneratablePlant::canGenerate
//    ) {
//        @Override
//        public boolean placeFeature(World world, Random rand, BlockPos origin) {
//            Block block = rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_BLUE_BLOOMED : (rand.nextInt(3) != 0 ? MidnightBlocks.UNSTABLE_BUSH_LIME_BLOOMED : MidnightBlocks.UNSTABLE_BUSH_GREEN_BLOOMED);
//            BlockState state = block.getDefaultState().with(UnstableBushBloomedBlock.HAS_FRUIT, true);
//            if (this.predicate.canSpawn(world, origin, state)) {
//                setBlockAndNotifyAdequately(world, origin, state);
//                return true;
//            }
//            return false;
//        }
//    };
//
//    public static final IMidnightFeature RUNEBUSH_FEATURE = new PlantFeature(
//            MidnightBlocks.RUNEBUSH.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature DOUBLE_LUMEN_FEATURE = new DoublePlantFeature(
//            MidnightBlocks.DOUBLE_LUMEN_BUD.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature FUNGI_FEATURE = new FungiFeature(FungiFeature.FUNGI_STATES);
//    public static final IMidnightFeature BOG_FUNGI_FEATURE = new FungiFeature(FungiFeature.BOG_FUNGI_STATES);
//
//    public static final IMidnightFeature DOUBLE_FUNGI_FEATURE = new DoubleFungiFeature(DoubleFungiFeature.FUNGI_STATES);
//    public static final IMidnightFeature DOUBLE_BOG_FUNGI_FEATURE = new DoubleFungiFeature(DoubleFungiFeature.BOG_FUNGI_STATES);
//
//    public static final IMidnightFeature DRAGON_NEST_FEATURE = new PlantFeature(
//            MidnightBlocks.DRAGON_NEST.getDefaultState(),
//            (world, pos, state) -> MidnightBlocks.DRAGON_NEST.canPlaceBlockAt(world, pos)
//    );
//
//    public static final IMidnightFeature CRYSTAL_FLOWER_FEATURE = new PlantFeature(
//            MidnightBlocks.CRYSTAL_FLOWER.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature[] LARGE_FUNGI_FEATURES = new LargeFungiFeature[] {
//            new LargeFungiFeature(
//                    MidnightBlocks.DEWSHROOM_STEM.getDefaultState(),
//                    MidnightBlocks.DEWSHROOM_HAT.getDefaultState()
//            ),
//            new LargeFungiFeature(
//                    MidnightBlocks.NIGHTSHROOM_STEM.getDefaultState(),
//                    MidnightBlocks.NIGHTSHROOM_HAT.getDefaultState()
//            ),
//            new LargeFungiFeature(
//                    MidnightBlocks.VIRIDSHROOM_STEM.getDefaultState(),
//                    MidnightBlocks.VIRIDSHROOM_HAT.getDefaultState()
//            )
//    };
//
//    public static final IMidnightFeature LARGE_BOGSHROOM_FEATURE = new LargeBogshroomFeature();
//
//    public static final IMidnightFeature LARGE_BULB_FUNGUS_FEATURE = new LargeBulbFungusFeature();
//
//    public static final IMidnightFeature BULB_FUNGUS_FEATURE = new PlantFeature(
//            MidnightBlocks.BULB_FUNGUS.getDefaultState(),
//            GeneratablePlant::canGenerate
//    );
//
//    public static final IMidnightFeature BLOOMCRYSTAL_FEATURE = new CrystalClusterFeature(3, 4,
//            MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
//            MidnightBlocks.BLOOMCRYSTAL.getDefaultState()
//    );
//
//    public static final IMidnightFeature BLOOMCRYSTAL_SPIRE_FEATURE = new CrystalClusterFeature(4, 13,
//            MidnightBlocks.BLOOMCRYSTAL_ROCK.getDefaultState(),
//            MidnightBlocks.BLOOMCRYSTAL.getDefaultState()
//    );
//
//    public static final IMidnightFeature ROUXE_FEATURE = new CrystalClusterFeature(3, 4,
//            MidnightBlocks.ROUXE_ROCK.getDefaultState(),
//            MidnightBlocks.ROUXE.getDefaultState()
//    );
//
//    public static final IMidnightFeature NIGHTSTONE_BOULDER_FEATURE = new BoulderFeature(2) {
//        @Override
//        protected BlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random) {
//            return MidnightBlocks.NIGHTSTONE.getDefaultState();
//        }
//    };
//    public static final IMidnightFeature NIGHTSTONE_SPIKE_FEATURE = new SpikeFeature(MidnightBlocks.NIGHTSTONE.getDefaultState());
//
//    public static final IMidnightFeature TRENCHSTONE_BOULDER_FEATURE = new BoulderFeature(2) {
//        private final float radiusSquareIn = radius <= 1f ? 0f : (radius - 1f) * (radius - 1f);
//
//        @Override
//        protected BlockState getStateForPlacement(World world, BlockPos origin, BlockPos pos, double dist, float radiusSquare, Random random) {
//            return dist <= radiusSquareIn && random.nextFloat() < 0.1f ? MidnightBlocks.ARCHAIC_ORE.getDefaultState() : MidnightBlocks.TRENCHSTONE.getDefaultState();
//        }
//    };
//
//    public static final IMidnightFeature ROCKSHROOM_HEAP_FEATURE = new HeapFeature(MidnightBlocks.ROCKSHROOM.getDefaultState());
//
//    public static final IMidnightFeature[] UNDERGROUND_FEATURES = new IMidnightFeature[] {
//            new PlantFeature(MidnightBlocks.TENDRILWEED.getDefaultState(), GeneratablePlant::canGenerate),
//            FUNGI_FEATURE,
//            new MidnightAbstractFeature() {
//                @Override
//                public boolean placeFeature(World world, Random random, BlockPos origin) {
//                    if (world.getBlockState(origin.down()).isFullBlock()) {
//                        world.setBlockState(origin, MidnightBlocks.STINGER_EGG.getDefaultState().with(PileOfEggsBlock.EGGS, random.nextInt(4) + 1), 2 | 16);
//                        return true;
//                    }
//                    return false;
//                }
//            }
//    };

    public static final AbstractTreeFeature<NoFeatureConfig> SHADOWROOT_TREE = RegUtil.injected();
    public static final AbstractTreeFeature<NoFeatureConfig> DARK_WILLOW_TREE = RegUtil.injected();
    public static final Feature<NoFeatureConfig> DEAD_TREE = RegUtil.injected();
    public static final Feature<NoFeatureConfig> BOG_DEAD_TREE = RegUtil.injected();
    public static final Feature<NoFeatureConfig> DEAD_LOG = RegUtil.injected();
    public static final Feature<NoFeatureConfig> LARGE_BOGSHROOM = RegUtil.injected();

    public static final Feature<NoFeatureConfig> SUAVIS = RegUtil.injected();
    public static final Feature<NoFeatureConfig> DECEITFUL_ALGAE = RegUtil.injected();
    public static final Feature<NoFeatureConfig> DECEITFUL_MOSS = RegUtil.injected();

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        RegUtil.generic(event.getRegistry())
                .add("shadowroot_tree", new ShadowrootTreeFeature(NoFeatureConfig::deserialize))
                .add("dark_willow_tree", new DarkWillowTreeFeature(NoFeatureConfig::deserialize))
                .add("dead_tree", new DeadTreeFeature(NoFeatureConfig::deserialize, ShelfAttachProcessor.FOREST_SHELF_BLOCKS))
                .add("bog_dead_tree", new DeadTreeFeature(NoFeatureConfig::deserialize, ShelfAttachProcessor.SHELF_BLOCKS))
                .add("dead_log", new DeadLogFeature(NoFeatureConfig::deserialize))
                .add("large_bogshroom", new LargeBogshroomFeature(NoFeatureConfig::deserialize))
                .add("suavis", new ScatteredPlantFeature(NoFeatureConfig::deserialize, MidnightBlocks.SUAVIS.getDefaultState()))
                .add("deceitful_algae", new AlgaeFeature(NoFeatureConfig::deserialize))
                .add("deceitful_moss", new MossFeature(NoFeatureConfig::deserialize));
    }
}
