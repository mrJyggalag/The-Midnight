package com.mushroom.midnight.common.capability;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.network.AnimationMessage;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AnimationCapability implements ICapabilityProvider {
    private Type animationType = Type.NONE;
    private int maxTicks, prevTick, currentTick;

    public enum Type {NONE, ATTACK, EAT, CURTSEY, CHARGE}

    public void setAnimation(Entity animatable, Type animationType, int duration) {
        this.animationType = animationType;
        this.maxTicks = duration;
        this.currentTick = 0;
        if (!animatable.world.isRemote) {
            AnimationMessage message = new AnimationMessage(animatable, animationType, duration);
            Midnight.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> animatable), message);
        }
    }

    public Type getAnimationType() {
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
        if (this.animationType != Type.NONE) {
            this.prevTick = this.currentTick;
            if (this.currentTick >= this.maxTicks) {
                this.currentTick = this.maxTicks = 0;
                this.animationType = Type.NONE;
            } else {
                this.currentTick++;
            }
        }
    }

    public void resetAnimation(Entity animable) {
        setAnimation(animable, Type.NONE, 0);
    }

    public boolean isAnimate() {
        return this.animationType != Type.NONE;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return Midnight.ANIMATION_CAP.orEmpty(cap, LazyOptional.of(() -> this));
    }
}
