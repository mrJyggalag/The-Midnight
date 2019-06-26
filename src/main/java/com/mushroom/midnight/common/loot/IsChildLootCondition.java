package com.mushroom.midnight.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import static com.mushroom.midnight.Midnight.MODID;

public class IsChildLootCondition implements ILootCondition {
    private final boolean inverse;

    public IsChildLootCondition(boolean inverse) {
        this.inverse = inverse;
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        return entity instanceof LivingEntity && ((LivingEntity) entity).isChild() == !inverse;
    }

    public static class Serializer extends ILootCondition.AbstractSerializer<IsChildLootCondition> {
        public Serializer() {
            super(new ResourceLocation(MODID, "is_child"), IsChildLootCondition.class);
        }

        @Override
        public void serialize(JsonObject json, IsChildLootCondition value, JsonSerializationContext context) {
            json.addProperty("inverse", value.inverse);
        }

        @Override
        public IsChildLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return new IsChildLootCondition(JSONUtils.getBoolean(json, "inverse", false));
        }
    }
}
