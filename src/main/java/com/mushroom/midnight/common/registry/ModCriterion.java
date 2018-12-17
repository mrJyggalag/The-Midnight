package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.advancement.StatelessTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;

public class ModCriterion {
    public static final StatelessTrigger THROWN_BLADESHROOM = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(Midnight.MODID, "thrown_bladeshroom")));
    public static final StatelessTrigger HARVESTED_SUAVIS = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(Midnight.MODID, "harvested_suavis")));
    public static final StatelessTrigger SNAPPED_BY_SNAPPER = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(Midnight.MODID, "snapped_by_snapper")));
}
