package com.mushroom.midnight.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EntityUtil {
    private static final Map<Class<? extends LivingEntity>, Stance> STANCES = new HashMap<>();

    public static void register() {
        registerStance(EntitySpider.class, Stance.QUADRUPEDAL);
        registerStance(EntityCaveSpider.class, Stance.QUADRUPEDAL);
    }

    public static void registerStance(Class<? extends LivingEntity> entity, Stance stance) {
        STANCES.put(entity, stance);
    }

    public static Stance getStance(LivingEntity entity) {
        Stance registeredStance = STANCES.get(entity.getClass());
        if (registeredStance != null) {
            return registeredStance;
        }
        return guessStance(entity);
    }

    private static Stance guessStance(LivingEntity entity) {
        if (entity instanceof EntityAnimal) {
            return Stance.QUADRUPEDAL;
        } else if (entity instanceof EntityMob) {
            return Stance.BIPEDAL;
        }
        float height = Math.max(entity.height, entity.getEyeHeight());
        return height > entity.width ? Stance.BIPEDAL : Stance.QUADRUPEDAL;
    }

    public static boolean isCoveredBy(LivingEntity entity, ItemArmor.ArmorMaterial material) {
        for (ItemStack armorStack : entity.getArmorInventoryList()) {
            if (armorStack.isEmpty()) {
                return false;
            }
            Item armorItem = armorStack.getItem();
            if (armorItem instanceof ItemArmor) {
                if (material != ((ItemArmor) armorItem).getArmorMaterial()) {
                    return false;
                }
            }
        }
        return true;
    }

    public enum Stance {
        BIPEDAL,
        QUADRUPEDAL,
        NONE
    }
}
