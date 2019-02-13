package com.mushroom.midnight.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

import static com.mushroom.midnight.Midnight.MODID;

public class IsChildLootCondition implements LootCondition {
    private final boolean inverse;

    public IsChildLootCondition(boolean inverse) {
        this.inverse = inverse;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        return context.getLootedEntity() instanceof EntityLivingBase && ((EntityLivingBase) context.getLootedEntity()).isChild() == !inverse;
    }

    public static class Serialiser extends LootCondition.Serializer<IsChildLootCondition> {
        public Serialiser() {
            super(new ResourceLocation(MODID, "is_child"), IsChildLootCondition.class);
        }

        @Override
        public void serialize(JsonObject json, IsChildLootCondition value, JsonSerializationContext context) {
            json.addProperty("inverse", value.inverse);
        }

        @Override
        public IsChildLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return new IsChildLootCondition(JsonUtils.getBoolean(json, "inverse", false));
        }
    }
}
