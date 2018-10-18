package com.mushroom.midnight.common.world.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public interface IMidnightFeature {
    boolean generate(World world, Random random, BlockPos origin);

    default DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.CUSTOM;
    }
}
