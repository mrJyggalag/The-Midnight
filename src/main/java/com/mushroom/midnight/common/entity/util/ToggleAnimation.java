package com.mushroom.midnight.common.entity.util;

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
}
