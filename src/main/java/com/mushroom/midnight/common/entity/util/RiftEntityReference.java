package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.entity.EntityRift;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

public class RiftEntityReference {
    private UUID entityId;
    private int dimension;

    private WeakReference<EntityRift> rift;

    public void set(EntityRift rift) {
        this.rift = new WeakReference<>(rift);
        this.entityId = rift.getUniqueID();
        this.dimension = rift.world.provider.getDimension();
    }

    @Nullable
    public EntityRift compute() {
        EntityRift cached = this.get();
        WorldServer world = DimensionManager.getWorld(this.dimension);

        if (world != null && cached == null) {
            Optional<Entity> entity = world.loadedEntityList.stream()
                    .filter(e -> e instanceof EntityRift)
                    .filter(e -> e.getUniqueID().equals(this.entityId))
                    .findFirst();

            if (entity.isPresent()) {
                EntityRift rift = (EntityRift) entity.get();
                this.rift = new WeakReference<>(rift);
                return rift;
            }
        }

        return cached;
    }

    @Nullable
    public EntityRift get() {
        EntityRift rift = this.rift != null ? this.rift.get() : null;
        if (rift != null && this.isInvalid(rift)) {
            this.rift = null;
            return null;
        }
        return rift;
    }

    private boolean isInvalid(EntityRift rift) {
        if (!rift.world.isRemote) {
            return DimensionManager.getWorld(rift.world.provider.getDimension()) == null;
        }
        return false;
    }

    public boolean hasReference() {
        return this.entityId != null;
    }

    public NBTTagCompound serialize(NBTTagCompound compound) {
        if (this.entityId != null) {
            compound.setUniqueId("id", this.entityId);
            compound.setInteger("dimension", this.dimension);
        }
        return compound;
    }

    public void deserialize(NBTTagCompound compound) {
        if (compound.hasKey("idLeast")) {
            this.entityId = compound.getUniqueId("id");
            this.dimension = compound.getInteger("dimension");
        }
    }
}
