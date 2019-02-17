package com.mushroom.midnight.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;

public class CreatureTypeCount {
    private final int[] counts;

    private CreatureTypeCount(int[] counts) {
        this.counts = counts;
    }

    public int getCount(EnumCreatureType creatureType) {
        int ordinal = creatureType.ordinal();
        if (ordinal >= this.counts.length) {
            return 0;
        }
        return this.counts[ordinal];
    }

    public static CreatureTypeCount count(World world) {
        return count(world, Arrays.asList(EnumCreatureType.values()));
    }

    public static CreatureTypeCount count(World world, Collection<EnumCreatureType> creatureTypes) {
        int[] counts = new int[EnumCreatureType.values().length];

        for (Entity entity : world.loadedEntityList) {
            for (EnumCreatureType creatureType : creatureTypes) {
                if (entity.isCreatureType(creatureType, true)) {
                    counts[creatureType.ordinal()] += 1;
                }
            }
        }

        return new CreatureTypeCount(counts);
    }
}
