package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.common.advancement.StatelessTrigger;
import com.mushroom.midnight.common.entity.creature.EntityNightStag;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;

import java.util.stream.IntStream;

import static com.mushroom.midnight.Midnight.MODID;

public class ModCriterion {
    public static final StatelessTrigger THROWN_BLADESHROOM = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(MODID, "thrown_bladeshroom")));
    public static final StatelessTrigger THROWN_GEODE = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(MODID, "thrown_geode")));
    public static final StatelessTrigger HARVESTED_SUAVIS = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(MODID, "harvested_suavis")));
    public static final StatelessTrigger SNAPPED_BY_SNAPPER = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(MODID, "snapped_by_snapper")));
    public static final StatelessTrigger[] NIGHTSTAG_BOW = new StatelessTrigger[EntityNightStag.MAX_ANTLER_TYPE];
    static  {
        IntStream.range(0, EntityNightStag.MAX_ANTLER_TYPE).forEach(i -> NIGHTSTAG_BOW[i] = CriteriaTriggers.register(new StatelessTrigger(new ResourceLocation(MODID, "nightstag_bow" + i))));
    }
}
