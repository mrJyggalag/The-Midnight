package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.placement.ChanceSurfaceDoublePlacement;
import com.mushroom.midnight.common.world.feature.placement.ChanceSurfacePlacement;
import com.mushroom.midnight.common.world.feature.placement.CountSurface32Placement;
import com.mushroom.midnight.common.world.feature.placement.CountSurfaceDoublePlacement;
import com.mushroom.midnight.common.world.feature.placement.CountSurfacePlacement;
import com.mushroom.midnight.common.world.feature.placement.CountWithChanceSurfaceDoublePlacement;
import com.mushroom.midnight.common.world.feature.placement.DragonNestPlacement;
import com.mushroom.midnight.common.world.feature.placement.SurfacePlacementLevel;
import com.mushroom.midnight.common.world.feature.placement.UndergroundPlacementLevel;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

// TODO: Register from correct event when Forge is fixed
@ObjectHolder(Midnight.MODID)
public class MidnightPlacements {
    public static final Placement<NoPlacementConfig> DRAGON_NEST = new DragonNestPlacement(NoPlacementConfig::deserialize);

    public static final Placement<FrequencyConfig> COUNT_SURFACE = new CountSurfacePlacement(FrequencyConfig::deserialize, SurfacePlacementLevel.INSTANCE);
    public static final Placement<FrequencyConfig> COUNT_SURFACE_32 = new CountSurface32Placement(FrequencyConfig::deserialize, SurfacePlacementLevel.INSTANCE);
    public static final Placement<FrequencyConfig> COUNT_SURFACE_DOUBLE = new CountSurfaceDoublePlacement(FrequencyConfig::deserialize, SurfacePlacementLevel.INSTANCE);
    public static final Placement<ChanceConfig> CHANCE_SURFACE = new ChanceSurfacePlacement(ChanceConfig::deserialize, SurfacePlacementLevel.INSTANCE);
    public static final Placement<ChanceConfig> CHANCE_SURFACE_DOUBLE = new ChanceSurfaceDoublePlacement(ChanceConfig::deserialize, SurfacePlacementLevel.INSTANCE);
    public static final Placement<HeightWithChanceConfig> COUNT_CHANCE_SURFACE_DOUBLE = new CountWithChanceSurfaceDoublePlacement(HeightWithChanceConfig::deserialize, SurfacePlacementLevel.INSTANCE);

    public static final Placement<FrequencyConfig> COUNT_UNDERGROUND = new CountSurfacePlacement(FrequencyConfig::deserialize, UndergroundPlacementLevel.INSTANCE);
    public static final Placement<FrequencyConfig> COUNT_UNDERGROUND_32 = new CountSurface32Placement(FrequencyConfig::deserialize, UndergroundPlacementLevel.INSTANCE);

    public static void registerPlacements(IForgeRegistry<Placement<?>> registry) {
        RegUtil.generic(registry)
                .add("dragon_nest", DRAGON_NEST)
                .add("count_surface", COUNT_SURFACE)
                .add("count_surface_32", COUNT_SURFACE_32)
                .add("count_surface_double", COUNT_SURFACE_DOUBLE)
                .add("chance_surface", CHANCE_SURFACE)
                .add("chance_surface_double", CHANCE_SURFACE_DOUBLE)
                .add("count_chance_surface_double", COUNT_CHANCE_SURFACE_DOUBLE)
                .add("count_underground", COUNT_UNDERGROUND)
                .add("count_underground_32", COUNT_UNDERGROUND_32);
    }
}
