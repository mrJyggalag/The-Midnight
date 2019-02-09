package com.mushroom.midnight.common.util;

/**
 * @author pau101
 */
@FunctionalInterface
public interface Curve {
    static Curve nearest() {
        return x -> x < 0.5 ? 0.0 : 1.0;
    }

    static Curve linear() {
        return x -> x;
    }

    static Curve sine() {
        return x -> (Math.cos(Math.PI * x) - 1.0) / -2.0;
    }

    double get(double x);

    default double interpolate(double a, double b, double x) {
        double t = this.get(x);
        return (1.0 - t) * a + t * b;
    }
}
