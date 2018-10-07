package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface RifterCapturedCapability extends ICapabilityProvider {
    static boolean isCaptured(Entity entity) {
        RifterCapturedCapability capability = entity.getCapability(Midnight.rifterCapturedCap, null);
        return capability != null && capability.isCaptured();
    }

    void setCaptured(boolean captured);

    boolean isCaptured();

    class Impl implements RifterCapturedCapability {
        private boolean captured;

        @Override
        public void setCaptured(boolean captured) {
            this.captured = captured;
        }

        @Override
        public boolean isCaptured() {
            return this.captured;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == Midnight.rifterCapturedCap;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == Midnight.rifterCapturedCap) {
                return Midnight.rifterCapturedCap.cast(this);
            }
            return null;
        }
    }
}
