package com.mushroom.midnight.common.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ResetHookHandler<T> {
    private final T hookedValue;

    private Consumer<T> setValue;
    private Supplier<T> getValue;

    private T replacedValue;

    public ResetHookHandler(T hookedValue) {
        this.hookedValue = hookedValue;
    }

    public ResetHookHandler<T> setValue(Consumer<T> setValue) {
        this.setValue = setValue;
        return this;
    }

    public ResetHookHandler<T> getValue(Supplier<T> getValue) {
        this.getValue = getValue;
        return this;
    }

    public void apply(boolean hooked) {
        if (this.getValue == null || this.setValue == null) {
            throw new IllegalStateException("Cannot apply hook, hook actions not properly set!");
        }

        if (hooked) {
            this.forceHook();
        } else if (this.replacedValue != null) {
            this.resetHook();
        }
    }

    private void forceHook() {
        T currentValue = this.getValue.get();
        boolean hookValid = Objects.equals(currentValue, this.hookedValue);

        if (this.replacedValue == null || !hookValid) {
            this.replacedValue = currentValue;
        }

        if (!hookValid) {
            this.setValue.accept(this.hookedValue);
        }
    }

    private void resetHook() {
        this.setValue.accept(this.replacedValue);
        this.replacedValue = null;
    }
}
