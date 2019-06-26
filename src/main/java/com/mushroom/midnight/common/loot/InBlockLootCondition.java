package com.mushroom.midnight.common.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mushroom.midnight.Midnight.MODID;

public class InBlockLootCondition implements ILootCondition {
    private final Block block;

    public InBlockLootCondition(Block block) {
        this.block = block;
    }

    @Override
    public boolean test(LootContext context) {
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        return entity != null && context.getWorld().getBlockState(entity.getPosition()).getBlock() == block;
    }

    public static class Serializer extends ILootCondition.AbstractSerializer<InBlockLootCondition> {
        public Serializer() {
            super(new ResourceLocation(MODID, "in_block"), InBlockLootCondition.class);
        }

        @Override
        public void serialize(JsonObject json, InBlockLootCondition value, JsonSerializationContext context) {
            json.addProperty("block", value.block.getRegistryName().toString());
        }

        @Override
        public InBlockLootCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            if (!json.has("block")) {
                throw new JsonSyntaxException("Missing block tag, expected to find a block registry name");
            }
            ResourceLocation rl = new ResourceLocation(json.get("block").getAsString());
            Block block = ForgeRegistries.BLOCKS.getValue(rl);
            if (block == null) {
                throw new JsonSyntaxException("Invalid block tag. " + rl + " does not exist in the block registry.");
            }
            return new InBlockLootCondition(block);
        }
    }
}
