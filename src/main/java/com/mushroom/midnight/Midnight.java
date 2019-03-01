package com.mushroom.midnight;

import com.google.common.reflect.Reflection;
import com.mushroom.midnight.common.capability.AnimationCapability;
import com.mushroom.midnight.common.capability.CavernousBiomeStore;
import com.mushroom.midnight.common.capability.DelegatedStorage;
import com.mushroom.midnight.common.capability.MidnightWorldSpawners;
import com.mushroom.midnight.common.capability.MultiLayerBiomeSampler;
import com.mushroom.midnight.common.capability.NullStorage;
import com.mushroom.midnight.common.capability.RiftTravelCooldown;
import com.mushroom.midnight.common.capability.RifterCapturable;
import com.mushroom.midnight.common.compatibility.CompatibilityThaumcraft;
import com.mushroom.midnight.common.compatibility.SupportMods;
import com.mushroom.midnight.common.loot.InBiomeLootCondition;
import com.mushroom.midnight.common.loot.InBlockLootCondition;
import com.mushroom.midnight.common.loot.IsChildLootCondition;
import com.mushroom.midnight.common.network.GuiHandler;
import com.mushroom.midnight.common.network.MessageAnimation;
import com.mushroom.midnight.common.network.MessageBridgeCreate;
import com.mushroom.midnight.common.network.MessageBridgeRemoval;
import com.mushroom.midnight.common.network.MessageBridgeState;
import com.mushroom.midnight.common.network.MessageCaptureEntity;
import com.mushroom.midnight.common.network.MessageItemActivation;
import com.mushroom.midnight.common.network.MessageRockshroomBroken;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModCavernousBiomes;
import com.mushroom.midnight.common.registry.ModCriterion;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModFluids;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.registry.ModRecipes;
import com.mushroom.midnight.common.registry.ModSurfaceBiomes;
import com.mushroom.midnight.common.registry.ModTabs;
import com.mushroom.midnight.common.registry.RegUtil;
import com.mushroom.midnight.common.util.EntityUtil;
import com.mushroom.midnight.common.util.IProxy;
import com.mushroom.midnight.common.world.generator.MidnightOreGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Midnight.MODID,
        name = Midnight.NAME,
        version = Midnight.VERSION,
        dependencies = "required:forge@[14.23.5.2768,)",
        updateJSON = "https://gist.githubusercontent.com/gegy1000/a3f1f593c43f88d7f01a3560ac32e216/raw/midnight_updates.json"
)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class Midnight {

    public static final String MODID = "midnight";
    public static final String NAME = "Midnight";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID);

    public static final EnumCreatureType MIDNIGHT_MOB = EnumHelper.addCreatureType("midnight_mob", IMob.class, 20, Material.AIR, false, false);
    public static final EnumCreatureType MIDNIGHT_AMBIENT = EnumHelper.addCreatureType("midnight_ambient", EntityAmbientCreature.class, 30, Material.AIR, true, false);

    @SidedProxy(serverSide = "com.mushroom.midnight.common.ServerProxy", clientSide = "com.mushroom.midnight.client.ClientProxy")
    public static IProxy proxy;

    @Mod.Instance(MODID)
    public static Midnight instance;

    @CapabilityInject(RiftTravelCooldown.class)
    public static final Capability<RiftTravelCooldown> RIFT_TRAVEL_COOLDOWN_CAP = RegUtil.injected();

    @CapabilityInject(RifterCapturable.class)
    public static final Capability<RifterCapturable> RIFTER_CAPTURABLE_CAP = RegUtil.injected();

    @CapabilityInject(CavernousBiomeStore.class)
    public static final Capability<CavernousBiomeStore> CAVERNOUS_BIOME_CAP = RegUtil.injected();

    @CapabilityInject(MultiLayerBiomeSampler.class)
    public static final Capability<MultiLayerBiomeSampler> MULTI_LAYER_BIOME_SAMPLER_CAP = RegUtil.injected();

    @CapabilityInject(AnimationCapability.class)
    public static final Capability<AnimationCapability> ANIMATION_CAP = RegUtil.injected();

    @CapabilityInject(MidnightWorldSpawners.class)
    public static final Capability<MidnightWorldSpawners> WORLD_SPAWNERS_CAP = RegUtil.injected();

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(RiftTravelCooldown.class, new NullStorage<>(), RiftTravelCooldown::new);
        CapabilityManager.INSTANCE.register(RifterCapturable.class, new NullStorage<>(), RifterCapturable::new);
        CapabilityManager.INSTANCE.register(CavernousBiomeStore.class, new DelegatedStorage<>(), CavernousBiomeStore::new);
        CapabilityManager.INSTANCE.register(MultiLayerBiomeSampler.class, new NullStorage<>(), MultiLayerBiomeSampler::new);
        CapabilityManager.INSTANCE.register(AnimationCapability.class, new NullStorage<>(), AnimationCapability::new);
        CapabilityManager.INSTANCE.register(MidnightWorldSpawners.class, new NullStorage<>(), MidnightWorldSpawners.Void::new);

        NETWORK.registerMessage(MessageCaptureEntity.Handler.class, MessageCaptureEntity.class, 0, Side.CLIENT);
        NETWORK.registerMessage(MessageBridgeCreate.Handler.class, MessageBridgeCreate.class, 1, Side.CLIENT);
        NETWORK.registerMessage(MessageBridgeState.Handler.class, MessageBridgeState.class, 2, Side.CLIENT);
        NETWORK.registerMessage(MessageBridgeRemoval.Handler.class, MessageBridgeRemoval.class, 3, Side.CLIENT);
        NETWORK.registerMessage(MessageAnimation.Handler.class, MessageAnimation.class, 4, Side.CLIENT);
        NETWORK.registerMessage(MessageRockshroomBroken.Handler.class, MessageRockshroomBroken.class, 5, Side.CLIENT);
        NETWORK.registerMessage(MessageItemActivation.Handler.class, MessageItemActivation.class, 6, Side.CLIENT);

        Reflection.initialize(ModCriterion.class, ModTabs.class);

        EntityUtil.onPreInit();
        ModFluids.register();
        ModDimensions.register();

        LootConditionManager.registerCondition(new InBiomeLootCondition.Serialiser());
        LootConditionManager.registerCondition(new InBlockLootCondition.Serialiser());
        LootConditionManager.registerCondition(new IsChildLootCondition.Serialiser());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new MidnightOreGenerator(), Integer.MAX_VALUE);

        ModSurfaceBiomes.onInit();
        ModCavernousBiomes.onInit();
        ModItems.onInit();
        ModBlocks.onInit();
        ModRecipes.onInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        proxy.onInit();

        if (SupportMods.THAUMCRAFT.isLoaded()) {
            MinecraftForge.EVENT_BUS.register(CompatibilityThaumcraft.instance);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
