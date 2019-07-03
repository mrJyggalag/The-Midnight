package com.mushroom.midnight.common.loot;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FishingLoot {
    private static ResourceLocation MIDNIGHT_FISHING = new ResourceLocation(MODID, "fishing");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(LootTables.GAMEPLAY_FISHING)) {
            addConditionToAllMainPools(event.getTable(), context -> !Helper.isMidnightDimension(context.getWorld()));
            LootTable midnightFishing = event.getLootTableManager().getLootTableFromLocation(MIDNIGHT_FISHING);
            if (midnightFishing == LootTable.EMPTY_LOOT_TABLE) { // TODO fix me, i'm not loaded
                Midnight.LOGGER.warn("The loottable for midnight fishing is absent");
            } else {
                LootPool midnightPool = midnightFishing.getPool("midnight_fishing");
                event.getTable().addPool(midnightPool);
                addConditionToPool(context -> Helper.isMidnightDimension(context.getWorld()), midnightPool);
            }

        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToAllMainPools(LootTable table, ILootCondition condition) {
        List<LootPool> pools = null;
        pools = ObfuscationReflectionHelper.getPrivateValue(LootTable.class, table, "field_186466_c");
        if (pools != null) {
            for (LootPool pool : pools) {
                addConditionToPool(condition, pool);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToPool(ILootCondition condition, LootPool pool) {
        List<ILootCondition> conditions = ObfuscationReflectionHelper.getPrivateValue(LootPool.class, pool, "field_186454_b");
        conditions.add(condition);
    }
}
