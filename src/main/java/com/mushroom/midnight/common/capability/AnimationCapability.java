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
    private AnimationType animationType = AnimationType.NONE;
    private int maxTicks, prevTick, currentTick;
    public enum AnimationType { NONE, ATTACK, EAT, CURTSEY }

    public void setAnimation(Entity animable, AnimationType animationType, int duration) {
        this.animationType = animationType;
        this.maxTicks = duration;
        this.currentTick = 0;
        if (!animable.world.isRemote) {
            Midnight.NETWORK.sendToAllTracking(new MessageAnimation(animable, animationType, duration), animable);
        }
    }

    public AnimationType getAnimationType() {
        return this.animationType;
    }

    public int getDuration() {
        return maxTicks;
    }

    public int getCurrentTick() {
        return this.currentTick;
    }

    public float getProgress(float partialTicks) {
        return (this.prevTick + (this.currentTick - this.prevTick) * partialTicks) / this.maxTicks;
    }

    public void updateAnimation() {
        if (this.animationType != AnimationType.NONE) {
            this.prevTick = this.currentTick;
            if (this.currentTick >= this.maxTicks) {
                this.currentTick = this.maxTicks = 0;
                this.animationType = AnimationType.NONE;
            } else {
                this.currentTick++;
            }
        }
    }

    public void resetAnimation(Entity animable) {
        setAnimation(animable, AnimationType.NONE, 0);
    }

    public boolean isAnimate() {
        return this.animationType != AnimationType.NONE;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == Midnight.ANIMATION_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == Midnight.ANIMATION_CAP) {
            return Midnight.ANIMATION_CAP.cast(this);
        }
        return null;
    }
}
