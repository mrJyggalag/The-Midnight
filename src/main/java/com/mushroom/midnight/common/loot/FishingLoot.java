package com.mushroom.midnight.common.loot;

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

@Mod.EventBusSubscriber(modid = MODID)
public class FishingLoot {
    private static ResourceLocation MIDNIGHT_FISHING = new ResourceLocation(MODID, "fishing");

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName() == MIDNIGHT_FISHING) {
            LootTable vanillaTable = event.getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING_JUNK);
            if (vanillaTable != LootTable.EMPTY_LOOT_TABLE) {
                ObfuscationReflectionHelper.setPrivateValue(LootTable.class, vanillaTable, false, "isFrozen");
                addConditionToAllMainPools(vanillaTable, context -> !Helper.isMidnightDimension(context.getWorld()));
                LootPool midnightPool = event.getTable().getPool("midnight_fishing");
                vanillaTable.addPool(midnightPool);
                addConditionToPool(context -> Helper.isMidnightDimension(context.getWorld()), midnightPool);
                ObfuscationReflectionHelper.setPrivateValue(LootTable.class, vanillaTable, true, "isFrozen");
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
