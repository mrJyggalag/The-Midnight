package com.mushroom.midnight.common.entity.util;

import net.minecraft.nbt.NBTTagCompound;

public class ToggleAnimation {
    private final int length;

    private int speed = 1;

    private boolean state;
    private int timer;

    public ToggleAnimation(int length) {
        this.length = length;
    }

    public void update() {
        if (this.state) {
            this.timer = Math.min(this.timer + this.speed, this.length);
        } else {
            this.timer = Math.max(this.timer - this.speed, 0);
        }
    }

    public void set(boolean state) {
        this.state = state;
    }

    public boolean get() {
        return this.state;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return this.timer;
    }

    public NBTTagCompound serialize(NBTTagCompound compound) {
        compound.setBoolean("state", this.state);
        compound.setShort("timer", (short) this.timer);
        compound.setByte("speed", (byte) this.speed);
        return compound;
    }

    public void deserialize(NBTTagCompound compound) {
        this.state = compound.getBoolean("state");
        this.timer = compound.getShort("timer");
        this.speed = compound.getByte("speed");
    }

    @Override
    public String toString() {
        return "ToggleAnimation{state=" + this.state + ", timer=" + this.timer + '}';
    }
}
