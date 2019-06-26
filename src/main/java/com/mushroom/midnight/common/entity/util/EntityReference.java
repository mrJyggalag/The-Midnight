package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.Midnight;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

public class EntityReference<T extends Entity> {
    private final World world;

    @Nullable
    private UUID entityId;

    @Nullable
    private WeakReference<T> cachedEntity;

    private long lastLookupTime;

    public EntityReference(World world) {
        this.world = world;
    }

    public void set(T entity) {
        this.entityId = entity.getUniqueID();
        this.cachedEntity = new WeakReference<>(entity);
    }

    public Optional<T> deref(boolean forceLoad) {
        if (this.entityId == null) {
            return Optional.empty();
        }

        T cached = this.getCachedEntity();
        if (cached != null && !cached.isAlive()) {
            this.cachedEntity = null;
            return Optional.empty();
        }

        if (cached != null) {
            return Optional.of(cached);
        }

        long totalWorldTime = this.world.getTotalWorldTime();
        if (forceLoad || totalWorldTime - this.lastLookupTime > 20) {
            Optional<Entity> entity = this.world.loadedEntityList.stream()
                    .filter(e -> e.getUniqueID().equals(this.entityId))
                    .findFirst();
            entity.ifPresent(this::updateCachedEntity);
            this.lastLookupTime = totalWorldTime;
        }

        return Optional.ofNullable(this.getCachedEntity());
    }

    public boolean isPresent() {
        return this.deref(false).isPresent();
    }

    @SuppressWarnings("unchecked")
    private void updateCachedEntity(Entity entity) {
        try {
            this.cachedEntity = new WeakReference<>((T) entity);
        } catch (ClassCastException e) {
            Midnight.LOGGER.warn("Entity contained reference to entity of unexpected type! {}", entity);
            this.cachedEntity = null;
        }
    }

    @Nullable
    private T getCachedEntity() {
        return this.cachedEntity != null ? this.cachedEntity.get() : null;
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        if (this.entityId != null) {
            compound.putString("uuid", this.entityId.toString());
        }
        return compound;
    }

    public void deserialize(CompoundNBT compound) {
        if (compound.hasKey("uuid", Constants.NBT.TAG_STRING)) {
            this.entityId = UUID.fromString(compound.getString("uuid"));
        } else {
            this.entityId = null;
        }
    }
}
