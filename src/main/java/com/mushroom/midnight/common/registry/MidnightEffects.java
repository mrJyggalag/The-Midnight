package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.effect.ConfusionEffect;
import com.mushroom.midnight.common.effect.DarknessEffect;
import com.mushroom.midnight.common.effect.DragonGuardEffect;
import com.mushroom.midnight.common.effect.PollinatedEffect;
import com.mushroom.midnight.common.effect.StunnedEffect;
import com.mushroom.midnight.common.effect.TormentedEffect;
import com.mushroom.midnight.common.effect.UnstableFallEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Midnight.MODID)
public class MidnightEffects {
    public static final Effect STUNNED = Effects.BLINDNESS;
    public static final Effect POLLINATED = Effects.GLOWING;
    public static final Effect DRAGON_GUARD = Effects.FIRE_RESISTANCE;
    public static final Effect DARKNESS = Effects.BLINDNESS;
    public static final Effect TORMENTED = Effects.POISON;
    public static final Effect UNSTABLE_FALL = Effects.JUMP_BOOST;
    public static final Effect CONFUSION = Effects.BLINDNESS;

    @SubscribeEvent
    public static void onRegisterEffects(RegistryEvent.Register<Effect> event) {
        RegUtil.generic(event.getRegistry())
                .add("stunned", new StunnedEffect())
                .add("pollinated", new PollinatedEffect())
                .add("dragon_guard", new DragonGuardEffect())
                .add("darkness", new DarknessEffect())
                .add("tormented", new TormentedEffect())
                .add("unstable_fall", new UnstableFallEffect())
                .add("confusion", new ConfusionEffect());
    }
}
