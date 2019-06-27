package com.mushroom.midnight.common.registry;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.effect.StunnedEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Midnight.MODID)
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
                .add("stunned", new StunnedEffect().withIcon("stunned"))
                .add("pollinated", new StunnedEffect().withIcon("pollinated"))
                .add("dragon_guard", new StunnedEffect().withIcon("dragons_guard"))
                .add("darkness", new StunnedEffect().withIcon("darkness"))
                .add("tormented", new StunnedEffect().withIcon("tormented"))
                .add("unstable_fall", new StunnedEffect().withIcon("unstable"))
                .add("confusion", new StunnedEffect().withIcon("confusion"));
    }
}
