package com.mushroom.midnight.common.loot;

import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;

import static com.mushroom.midnight.Midnight.LOGGER;
import static com.mushroom.midnight.Midnight.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class FishingLoot {
    private static final Field fieldPools;
    private static final Field poolConditions;
    static {
        fieldPools = ReflectionHelper.findField(LootTable.class, "pools", "field_186466_c");
        poolConditions = ReflectionHelper.findField(LootPool.class, "poolConditions", "field_186454_b");
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName() == LootTableList.GAMEPLAY_FISHING) {
            addConditionToAllMainPools(event.getTable(), (rand, context) -> !Helper.isMidnightDimension(context.getWorld()));
            LootTable midnightTable = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(MODID, "fishing"));
            if (midnightTable == null) {
                LOGGER.warn("The Midnight fishing loottable is absent");
            } else {
                LootPool midnightPool = midnightTable.getPool("midnight_fishing");
                event.getTable().addPool(midnightPool);
                addConditionToPool((rand, context) -> Helper.isMidnightDimension(context.getWorld()), midnightPool);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToAllMainPools(LootTable table, LootCondition condition) {
        List<LootPool> pools = null;
        try {
            pools = (List<LootPool>) fieldPools.get(table);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (pools != null) {
            for (LootPool pool : pools) {
                addConditionToPool(condition, pool);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addConditionToPool(LootCondition condition, LootPool pool) {
        try {
            List<LootCondition> conditions = (List<LootCondition>) poolConditions.get(pool);
            conditions.add(condition);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
