package com.mushroom.midnight.common.util;

public class BitFlags {
    private byte inner;

    public BitFlags(byte inner) {
        this.inner = inner;
    }

    public BitFlags() {
        this((byte) 0);
    }

    public BitFlags withBit(int bit, boolean enabled) {
        if (enabled) {
            this.inner |= 1 << bit;
        } else {
            this.inner &= ~(1 << bit);
        }
        return this;
    }

    public boolean getBit(int bit) {
        return (this.inner >>> bit & 1) != 0;
    }

    public byte toInner() {
        return this.inner;
    }
}
