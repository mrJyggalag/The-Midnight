package com.mushroom.midnight.common.config;

import com.mushroom.midnight.Midnight;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Midnight.MODID, name = Midnight.MODID)
@Config.LangKey("midnight.config.title")
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class MidnightConfig {
    @Config.Name("midnight_dimension_id")
    @Config.LangKey("config.midnight.midnight_dimension_id")
    @Config.Comment("The ID to be used for the Midnight Dimension")
    @Config.RequiresMcRestart
    public static int midnightDimensionId = -23;

    @SubscribeEvent
    public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Midnight.MODID)) {
            ConfigManager.sync(Midnight.MODID, Config.Type.INSTANCE);
        }
    }
}
