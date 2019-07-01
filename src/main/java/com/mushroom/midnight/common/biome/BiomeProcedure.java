package com.mushroom.midnight.common.biome;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.VoroniZoomLayer;

import java.util.function.LongFunction;

public final class BiomeProcedure<A extends IArea> {
    private final A noise;
    private final A block;

    private BiomeProcedure(A noise, A block) {
        this.noise = noise;
        this.block = block;
    }

    public static <A extends IArea, C extends IExtendedNoiseRandom<A>> BiomeProcedure<A> of(IAreaFactory<A> noise, LongFunction<C> contextFactory) {
        IAreaFactory<A> block = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(1234), noise);
        return new BiomeProcedure<>(noise.make(), block.make());
    }

    public A getNoise() {
        return this.noise;
    }

    public A getBlock() {
        return this.block;
    }
}
