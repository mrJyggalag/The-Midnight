package com.mushroom.midnight.common.util;

import com.mushroom.midnight.Midnight;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Midnight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SessionLocal<T> {
    private static final Map<SessionLocal<?>, Object> REGISTRY = new WeakHashMap<>();
    private static final Object PLACEHOLDER = new Object();

    private final Supplier<T> initializer;
    private T sessionValue;

    private SessionLocal(Supplier<T> initializer) {
        this.initializer = initializer;
    }

    public static <T> SessionLocal<T> register(Supplier<T> initializer) {
        SessionLocal<T> sessionLocal = new SessionLocal<>(initializer);
        REGISTRY.put(sessionLocal, PLACEHOLDER);

        return sessionLocal;
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        invalidateSession();
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        invalidateSession();
    }

    public static void invalidateSession() {
        for (SessionLocal<?> sessionLocal : REGISTRY.keySet()) {
            sessionLocal.invalidate();
        }
    }

    @Nonnull
    public T get() {
        if (this.sessionValue == null) {
            this.sessionValue = this.initializer.get();
        }
        return this.sessionValue;
    }

    public void invalidate() {
        this.sessionValue = null;
    }
}
