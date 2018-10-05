package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.EntityRift;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface RiftCooldownCapability extends ICapabilityProvider {
    void update(Entity entity);

    void setCooldown(int cooldown);

    boolean isReady();

    class Impl implements RiftCooldownCapability {
        private int cooldown;

        @Override
        public void update(Entity entity) {
            if (this.cooldown > 0) {
                AxisAlignedBB bounds = entity.getEntityBoundingBox();
                if (entity.world.getEntitiesWithinAABB(EntityRift.class, bounds).isEmpty()) {
                    this.cooldown--;
                }
            }
        }

        @Override
        public void setCooldown(int cooldown) {
            this.cooldown = cooldown;
        }

        @Override
        public boolean isReady() {
            return this.cooldown <= 0;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == Midnight.riftCooldownCap;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == Midnight.riftCooldownCap) {
                return Midnight.riftCooldownCap.cast(this);
            }
            return null;
        }
    }
}
