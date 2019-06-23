package com.mushroom.midnight.common.advancement;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StatelessTrigger implements ICriterionTrigger<StatelessTrigger.Instance> {
    private final ResourceLocation identifier;
    private final Map<PlayerAdvancements, Listeners> listeners = new HashMap<>();

    public StatelessTrigger(ResourceLocation identifier) {
        this.identifier = identifier;
    }

    @Override
    public ResourceLocation getId() {
        return this.identifier;
    }

    @Override
    public void addListener(PlayerAdvancements advancements, Listener<Instance> listener) {
        Listeners listeners = this.listeners.computeIfAbsent(advancements, Listeners::new);
        listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements advancements, Listener<Instance> listener) {
        Listeners listeners = this.listeners.get(advancements);
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
        if (listeners.isEmpty()) {
            this.listeners.remove(advancements);
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements advancements) {
        this.listeners.remove(advancements);
    }

    @Override
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return new Instance(this.identifier);
    }

    public void trigger(EntityPlayerMP player) {
        Listeners listeners = this.listeners.get(player.getAdvancements());
        if (listeners != null) {
            listeners.trigger();
        }
    }

    public static class Instance extends AbstractCriterionInstance {
        Instance(ResourceLocation identifier) {
            super(identifier);
        }
    }

    static class Listeners {
        private final PlayerAdvancements advancements;
        private final Set<Listener<Instance>> listeners = new HashSet<>();

        public Listeners(PlayerAdvancements advancements) {
            this.advancements = advancements;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(Listener<Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger() {
            new ArrayList<>(this.listeners).forEach(listener -> listener.grantCriterion(this.advancements));
        }
    }
}
