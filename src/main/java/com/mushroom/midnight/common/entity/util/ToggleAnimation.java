package com.mushroom.midnight.common.entity.util;

import net.minecraft.nbt.CompoundNBT;

public class ToggleAnimation {
    private final int length;

    private int rate = 1;

    private boolean state;
    private int timer;

    public ToggleAnimation(int length) {
        this.length = length;
    }

    public void update() {
        if (this.state) {
            this.timer = Math.min(this.timer + this.rate, this.length);
        } else {
            this.timer = Math.max(this.timer - this.rate, 0);
        }
    }

    public void set(boolean state) {
        this.state = state;
    }

    public boolean get() {
        return this.state;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return this.timer;
    }

    public float getScale() {
        return this.timer / (float) this.length;
    }

    public CompoundNBT serialize(CompoundNBT compound) {
        compound.putBoolean("state", this.state);
        compound.putShort("timer", (short) this.timer);
        compound.putByte("rate", (byte) this.rate);
        return compound;
    }

    public void deserialize(CompoundNBT compound) {
        this.state = compound.getBoolean("state");
        this.timer = compound.getShort("timer");
        this.rate = compound.getByte("rate");
    }

    @Override
    public String toString() {
        return "ToggleAnimation{state=" + this.state + ", timer=" + this.timer + '}';
    }
}
