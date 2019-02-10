package com.mushroom.midnight.common.util;

import java.util.Arrays;

/**
 * @author pau101
 */
public class RegionInterpolator {
    private final Region[] regions;
    private final Curve curve;

    public RegionInterpolator(Region[] regions, Curve curve) {
        this.regions = regions;
        this.curve = curve;
    }

    public static Region region(double start, double end, double density, double curveRange) {
        return new Region(start, end, density, curveRange);
    }

    public double get(double y) {
        int transitionCount = this.regions.length - 1;
        for (int n = 0; n < transitionCount; n++) {
            Region left = this.regions[n];
            Region right = this.regions[n + 1];
            double lower = Math.max(left.start, left.end - left.curveRange);
            double upper = Math.min(right.end, right.start + right.curveRange);
            if (y < upper || n == transitionCount - 1) {
                double t = y < lower ? 0.0 : y >= upper ? 1.0 : (y - lower) / (upper - lower);
                return this.curve.interpolate(left.density, right.density, t);
            }
        }

        throw new InternalError(String.format("%s: %.2f%n", Arrays.toString(this.regions), y));
    }

    public static class Region {
        private final double start;
        private final double end;
        private final double density;
        private final double curveRange;

        Region(double start, double end, double density, double curveRange) {
            this.start = start;
            this.end = end;
            this.density = density;
            this.curveRange = curveRange;
        }

        @Override
        public String toString() {
            return String.format("{start:%.2f,end:%.2f,density:%.2f}", this.start, this.end, this.density);
        }
    }
}
