package com.mushroom.midnight;

import com.mushroom.midnight.common.CommonProxy;
import com.mushroom.midnight.common.capability.RiftCooldownCapability;
import com.mushroom.midnight.common.capability.RifterCapturedCapability;
import com.mushroom.midnight.common.capability.VoidStorage;
import com.mushroom.midnight.common.network.MessageCaptureEntity;
import com.mushroom.midnight.common.registry.ModBiomes;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModDimensions;
import com.mushroom.midnight.common.registry.ModItems;
import com.mushroom.midnight.common.util.EntityUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Midnight.MODID, name = Midnight.NAME, version = Midnight.VERSION)
@Mod.EventBusSubscriber(modid = Midnight.MODID)
public class Midnight {

    public static final String MODID = "midnight";
    public static final String NAME = "Midnight";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID);
    public static final CreativeTabs MIDNIGHT_TAB = new CreativeTabs(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.APPLE);
        }
    };

    @SidedProxy(serverSide = "com.mushroom.midnight.common.CommonProxy", clientSide = "com.mushroom.midnight.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Midnight instance;

    @CapabilityInject(RiftCooldownCapability.class)
    public static Capability<RiftCooldownCapability> riftCooldownCap = null;

    @CapabilityInject(RifterCapturedCapability.class)
    public static Capability<RifterCapturedCapability> rifterCapturedCap = null;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(RiftCooldownCapability.class, new VoidStorage<>(), RiftCooldownCapability::new);
        CapabilityManager.INSTANCE.register(RifterCapturedCapability.class, new VoidStorage<>(), RifterCapturedCapability::new);

        NETWORK.registerMessage(MessageCaptureEntity.Handler.class, MessageCaptureEntity.class, 0, Side.CLIENT);

        EntityUtil.onPreInit();
        ModDimensions.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModBiomes.onInit();
        ModItems.onInit();
        ModBlocks.onInit();

        proxy.onInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
