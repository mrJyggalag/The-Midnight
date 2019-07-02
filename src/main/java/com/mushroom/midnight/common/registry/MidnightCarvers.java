package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.generator.MidnightCaveCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

// TODO: Register from correct event when Forge is fixed
@ObjectHolder(Midnight.MODID)
public class MidnightCarvers {
    public static final WorldCarver<ProbabilityConfig> WIDE_CAVE = new MidnightCaveCarver(ProbabilityConfig::deserialize, 5.0F);

    public static void registerCarvers(IForgeRegistry<WorldCarver<?>> registry) {
        RegUtil.generic(registry)
                .add("wide_cave", WIDE_CAVE);
    }
}
