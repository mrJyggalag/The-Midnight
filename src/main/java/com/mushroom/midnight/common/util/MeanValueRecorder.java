package com.mushroom.midnight.common.util;

public class MeanValueRecorder {
    private final int sampleCount;
    private final float[] samples;

    public MeanValueRecorder(int sampleCount) {
        this.sampleCount = sampleCount;
        this.samples = new float[sampleCount];
    }

    public void record(float sample) {
        System.arraycopy(this.samples, 1, this.samples, 0, this.sampleCount - 1);
        this.samples[this.sampleCount - 1] = sample;
    }

    public float computeMean() {
        double total = 0.0;
        for (float value : this.samples) {
            total += value;
        }
        return (float) (total / this.sampleCount);
    }
}
