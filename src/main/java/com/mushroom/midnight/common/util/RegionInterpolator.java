package com.mushroom.midnight.common.util;

import net.minecraft.util.math.MathHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    // TODO: Remove: test code
    public static void main(String[] args) throws IOException {
        RegionInterpolator.Region[] regions = new RegionInterpolator.Region[] {
                RegionInterpolator.region(0.0, 20.0, 1.0, 8.0),
                RegionInterpolator.region(20.0, 46.0, -1.5, 8.0),
                RegionInterpolator.region(46.0, 62.0, 1.0, 8.0),
                RegionInterpolator.region(62.0, 256.0, -194.0, 194)
        };

        RegionInterpolator interpolator = new RegionInterpolator(regions, Curve.sine());

        int width = 256;
        int height = 4096;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int background = 0xFFFFFF;
                for (Region region : regions) {
                    if (MathHelper.floor(region.center()) == x) {
                        background = 0xFF0000;
                        break;
                    }
                    if (x >= region.start && x <= region.end) {
                        if (region.density > 0.0) {
                            background = 0xA0A0A0;
                        } else {
                            background = 0xFFFFFF;
                        }
                    }
                }
                image.setRGB(x, y, background);
            }
        }

        for (double x = 0; x < width; x += 0.0125) {
            double density = interpolator.get(x);
            double targetY = (density * 6.0) + (height / 2.0);
            image.setRGB(MathHelper.floor(x), MathHelper.clamp(MathHelper.floor(targetY), 0, height), 0x000000);
        }

        ImageIO.write(image, "png", new File("test.png"));
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

        double center() {
            return (this.start + this.end) / 2.0;
        }

        @Override
        public String toString() {
            return String.format("{start:%.2f,end:%.2f,density:%.2f}", this.start, this.end, this.density);
        }
    }
}
