package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.feature.placement.DragonNestPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

// TODO: Register from correct event when Forge is fixed
@ObjectHolder(Midnight.MODID)
public class MidnightPlacements {
    public static final Placement<NoPlacementConfig> DRAGON_NEST = new DragonNestPlacement(NoPlacementConfig::deserialize);

    public static void registerPlacements(IForgeRegistry<Placement<?>> registry) {
        RegUtil.generic(registry)
                .add("dragon_nest", DRAGON_NEST);
    }
}
