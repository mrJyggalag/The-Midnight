package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.network.MessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AnimationCapability implements ICapabilityProvider {
    private AnimationType animationType = null;
    private int maxTicks, prevTick, currentTick;
    public enum AnimationType { ATTACK }

    public void setAnimation(Entity animable, AnimationType animationType, int duration) {
        this.animationType = animationType;
        this.maxTicks = duration;
        this.currentTick = 0;
        if (!animable.world.isRemote && this.animationType != null & this.maxTicks > 0) {
            Midnight.NETWORK.sendToAllTracking(new MessageAnimation(animable, animationType, duration), animable);
        }
    }

    public AnimationType getAnimationType() {
        return this.animationType;
    }

    public int getDuration() {
        return maxTicks;
    }

    public float getProgress(float partialTicks) {
        return (this.prevTick + (this.currentTick - this.prevTick) * partialTicks) / this.maxTicks;
    }

    public void updateAnimation() {
        if (this.animationType != null) {
            this.prevTick = this.currentTick;
            if (this.currentTick >= this.maxTicks) {
                this.currentTick = this.maxTicks = 0;
                this.animationType = null;
            } else {
                this.currentTick++;
            }
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Midnight.animationCap;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Midnight.animationCap) {
            return Midnight.animationCap.cast(this);
        }
        return null;
    }
}
