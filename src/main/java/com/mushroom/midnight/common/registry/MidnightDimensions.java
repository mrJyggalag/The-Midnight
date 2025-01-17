package com.mushroom.midnight.common.registry;

import com.google.common.base.Preconditions;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.world.MidnightDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MidnightDimensions {
    @SubscribeEvent
    public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
        ModDimension midnight = new ModDimension() {
            @Override
            public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
                return MidnightDimension::new;
            }
        };

        RegUtil.generic(event.getRegistry())
                .add("midnight", midnight);

        DimensionManager.registerDimension(midnight.getRegistryName(), midnight, null, false);
    }

    @Nonnull
    public static DimensionType midnight() {
        DimensionType dimension = DimensionType.byName(new ResourceLocation(Midnight.MODID, "midnight"));
        Preconditions.checkNotNull(dimension, "dimension not yet initialized");
        return dimension;
    }
}
