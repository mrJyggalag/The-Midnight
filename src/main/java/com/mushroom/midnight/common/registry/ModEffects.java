package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.effect.StunnedEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
@GameRegistry.ObjectHolder(Midnight.MODID)
public class ModEffects {
    public static final Potion STUNNED = MobEffects.BLINDNESS;

    @SubscribeEvent
    public static void onRegisterEffects(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(
                RegUtil.withName(new StunnedEffect(), "stunned").registerModifiers()
        );
    }
}
