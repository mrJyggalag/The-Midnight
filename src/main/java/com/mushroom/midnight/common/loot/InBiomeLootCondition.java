package com.mushroom.midnight.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mushroom.midnight.Midnight.MODID;

public class InBiomeLootCondition implements ILootCondition {
    private final Biome requiredBiome;

    public InBiomeLootCondition(Biome requiredBiome) {
        this.requiredBiome = requiredBiome;
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        if (entity == null) {
            return false;
        }
        Biome biome = context.getWorld().getBiome(entity.getPosition());
        return biome == requiredBiome;
    }

    public static class Serializer extends ILootCondition.AbstractSerializer<InBiomeLootCondition> {
        public Serializer() {
            super(new ResourceLocation(MODID, "in_biome"), InBiomeLootCondition.class);
        }

        @Override
        public void serialize(JsonObject json, InBiomeLootCondition value, JsonSerializationContext context) {
            json.addProperty("biome", value.requiredBiome.getRegistryName().toString());
        }

        @Override
        public InBiomeLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            if (!json.has("biome")) {
                throw new JsonSyntaxException("Missing biome tag, expected to find a biome registry name");
            }
            ResourceLocation biomeResLoc = new ResourceLocation(json.get("biome").getAsString());
            Biome biome = ForgeRegistries.BIOMES.getValue(biomeResLoc);
            if (biome == null) {
                throw new JsonSyntaxException("Invalid biome tag. " + biomeResLoc + " does not exist in the biome registry.");
            }
            return new InBiomeLootCondition(biome);
        }
    }
}
