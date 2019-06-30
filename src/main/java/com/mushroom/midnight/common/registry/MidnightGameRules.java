package com.mushroom.midnight.common.registry;

import net.minecraft.world.GameRules;

public class MidnightGameRules {
    public static final GameRules.RuleKey<GameRules.BooleanValue> DO_RIFT_SPAWNING = GameRules.register("doRiftSpawning", GameRules.BooleanValue.create(true));
}
