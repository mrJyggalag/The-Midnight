package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.effect.DarknessEffect;
import com.mushroom.midnight.common.effect.DragonGuardEffect;
import com.mushroom.midnight.common.effect.ConfusionEffect;
import com.mushroom.midnight.common.effect.PollinatedEffect;
import com.mushroom.midnight.common.effect.UnstableFallEffect;
import com.mushroom.midnight.common.effect.StunnedEffect;
import com.mushroom.midnight.common.effect.TormentedEffect;
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
    public static final Potion POLLINATED = MobEffects.GLOWING;
    public static final Potion DRAGON_GUARD = MobEffects.FIRE_RESISTANCE;
    public static final Potion DARKNESS = MobEffects.BLINDNESS;
    public static final Potion TORMENTED = MobEffects.POISON;
    public static final Potion UNSTABLE_FALL = MobEffects.JUMP_BOOST;
    public static final Potion CONFUSION = MobEffects.BLINDNESS;

    @SubscribeEvent
    public static void onRegisterEffects(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(
                RegUtil.withName(new StunnedEffect(), "stunned").withIcon("stunned"),
                RegUtil.withName(new PollinatedEffect(), "pollinated").withIcon("pollinated"),
                RegUtil.withName(new DragonGuardEffect(), "dragon_guard").withIcon("dragons_guard"),
                RegUtil.withName(new DarknessEffect(), "darkness").withIcon("darkness"),
                RegUtil.withName(new TormentedEffect(), "tormented").withIcon("tormented"),
                RegUtil.withName(new UnstableFallEffect(), "unstable_fall").withIcon("unstable"),
                RegUtil.withName(new ConfusionEffect(), "confusion").withIcon("confusion")
        );
    }
}
