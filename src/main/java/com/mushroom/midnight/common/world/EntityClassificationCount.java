package com.mushroom.midnight.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;

public class EntityClassificationCount {
    private final int[] counts;

    private EntityClassificationCount(int[] counts) {
        this.counts = counts;
    }

    public int getCount(EntityClassification classification) {
        int ordinal = classification.ordinal();
        if (ordinal >= this.counts.length) {
            return 0;
        }
        return this.counts[ordinal];
    }

    public static EntityClassificationCount count(World world) {
        return count(world, Arrays.asList(EntityClassification.values()));
    }

    public static EntityClassificationCount count(World world, Collection<EntityClassification> classifications) {
        int[] counts = new int[EntityClassification.values().length];

        for (Entity entity : world.loadedEntityList) {
            for (EntityClassification creatureType : classifications) {
                if (entity.isCreatureType(creatureType, true)) {
                    counts[creatureType.ordinal()] += 1;
                }
            }
        }

        return new EntityClassificationCount(counts);
    }
}
