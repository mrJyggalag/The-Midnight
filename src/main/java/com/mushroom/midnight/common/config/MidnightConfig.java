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
    @Config.Name("general")
    @Config.LangKey("config_cat.midnight.general")
    @Config.Comment("All the options that can only be set on the server.")
    public static CatGeneral general = new CatGeneral();

    @Config.Name("client")
    @Config.LangKey("config_cat.midnight.client")
    @Config.Comment("All the options that can be modified by players on server.")
    public static CatClient client = new CatClient();

    public static class CatGeneral {
        @Config.Name("midnight_dimension_id")
        @Config.LangKey("config.midnight.midnight_dimension_id")
        @Config.Comment("The ID to be used for the Midnight Dimension. Default= -23")
        @Config.RequiresMcRestart
        public int midnightDimensionId = -23;

        @Config.Name("bladeshroom_damage_chance")
        @Config.LangKey("config.midnight.bladeshroom_damage_chance")
        @Config.Comment("The chance to take a small amount of damage when picking a bladeshroom cap as a percentage. If 0, this is disabled. Default= 5")
        @Config.RangeInt(min=0, max=100)
        public int bladeshroomDamageChance = 5;

        @Config.Name("can_respawn_in_midnight")
        @Config.LangKey("config.midnight.can_respawn_in_midnight")
        @Config.Comment("If true, players will respawn in Midnight after dying in the dimension. Default= false")
        public boolean canRespawnInMidnight = false;

        @Config.Name("allow_lightning_damage")
        @Config.LangKey("config.midnight.allow_lightning_damage")
        @Config.Comment("Allows the lightning in Midnight to burn blocks and do damage to entities. Default= false")
        public boolean allowLightningDamage = false;

        @Config.Name("rift_spawn_rarity")
        @Config.LangKey("config.midnight.rift_spawn_rarity")
        @Config.Comment("The rarity that a rift appears. Larger numbers increase rarity. If 0, rifts don't spawn. Default= 1000")
        @Config.RangeInt(min=0, max=10000)
        public int riftSpawnRarity = 1000;

        @Config.Name("rifter_spawn_rarity")
        @Config.LangKey("config.midnight.rifter_spawn_rarity")
        @Config.Comment("The rarity that rifts spawn rifters. Larger numbers increase rarity. If 0, rifters don't spawn. Default= 1000")
        @Config.RangeInt(min=0, max=10000)
        public int rifterSpawnRarity = 1000;

        @Config.Name("max_rifter_by_rift")
        @Config.LangKey("config.midnight.max_rifter_by_rift")
        @Config.Comment("The maximum amount of rifters that spawn from a rift. Default= 1")
        @Config.RangeInt(min=1, max=5)
        public int maxRifterByRift = 1;

        @Config.Name("allow_rifter_teleport")
        @Config.LangKey("config.midnight.allow_rifter_teleport")
        @Config.Comment("Allows rifters to teleport to players when they aren't being looked at. Default= true")
        public boolean allowRifterTeleport = true;

        @Config.Name("monster_spawn_rate")
        @Config.LangKey("config.midnight.monster_spawn_rate")
        @Config.Comment("Spawn rate of entities of the 'monster' type in midnight (eg. Rifter, Hunter). Default= 400")
        @Config.RangeInt(min=1)
        public int monsterSpawnRate = 400;

        @Config.Name("rifter_capture_tamed_animal")
        @Config.LangKey("config.midnight.rifter_capture_tamed_animal")
        @Config.Comment("Allows rifters to capture tamed entities and drag them into rifts. Default= false")
        public boolean rifterCaptureTamedAnimal = false;

        @Config.Name("capturable_entities")
        @Config.RequiresWorldRestart
        @Config.LangKey("config.midnight." + "capturable_entities")
        @Config.Comment("Mobs on this whitelist can be captured by rifters, players and animals are already captured by default. Format is mod id:name of creature, just use the modid to whitelist everything from it. Default= empty")
        public String[] capturableEntities = {
        };

        @Config.Name("not_capturable_animals")
        @Config.RequiresWorldRestart
        @Config.LangKey("config.midnight." + "not_capturable_animals")
        @Config.Comment("Mobs on this blacklist cannot be captured by rifters. Format is mod id:name of creature, just use the mod id to blacklist everything from it. Default= iceandfire, midnight:nightstag")
        public String[] notCapturableAnimals = {
                "iceandfire",
                "midnight:nightstag"
        };
    }

    public static class CatClient {
        @Config.Name("hide_vignette_effect")
        @Config.LangKey("config.midnight.hide_vignette_effect")
        @Config.Comment("Hides the vignette effect in the darker areas of Midnight. Default= true")
        public boolean hideVignetteEffect = true;

        @Config.Name("rift_shaders")
        @Config.LangKey("config.midnight.rift_shaders")
        @Config.Comment("If true, rifts will be rendered with custom shaders for standard effects. If false, a simpler (motionless) texture will be rendered. Default= true")
        public boolean riftShaders = true;
    }

    @SubscribeEvent
    public static void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Midnight.MODID)) {
            ConfigManager.sync(Midnight.MODID, Config.Type.INSTANCE);
        }
    }
}
