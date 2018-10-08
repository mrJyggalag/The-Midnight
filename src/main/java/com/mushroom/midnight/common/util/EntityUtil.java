package com.mushroom.midnight.common.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;

import java.util.HashMap;
import java.util.Map;

public class EntityUtil {
    private static final Map<Class<? extends EntityLivingBase>, Stance> STANCES = new HashMap<>();

    public static void onPreInit() {
        registerStance(EntitySpider.class, Stance.QUADRUPEDAL);
        registerStance(EntityCaveSpider.class, Stance.QUADRUPEDAL);
    }

    public static void registerStance(Class<? extends EntityLivingBase> entity, Stance stance) {
        STANCES.put(entity, stance);
    }

    public static Stance getStance(EntityLivingBase entity) {
        Stance registeredStance = STANCES.get(entity.getClass());
        if (registeredStance != null) {
            return registeredStance;
        }
        return guessStance(entity);
    }

    private static Stance guessStance(EntityLivingBase entity) {
        if (entity instanceof EntityAnimal) {
            return Stance.QUADRUPEDAL;
        } else if (entity instanceof EntityMob) {
            return Stance.BIPEDAL;
        }
        float height = Math.max(entity.height, entity.getEyeHeight());
        return height > entity.width ? Stance.BIPEDAL : Stance.QUADRUPEDAL;
    }

    public enum Stance {
        BIPEDAL,
        QUADRUPEDAL,
        NONE
    }
}
