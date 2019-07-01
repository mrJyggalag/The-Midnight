package com.mushroom.midnight.common.world.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

import java.util.function.IntPredicate;

public class EdgeMergeLayer implements IAreaTransformer2, IDimOffset0Transformer {
    private final IntPredicate target;
    private final int replacement;

    public EdgeMergeLayer(IntPredicate target, int replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public int apply(INoiseRandom random, IArea mainSampler, IArea edgeSampler, int x, int y) {
        int main = mainSampler.getValue(this.func_215721_a(x), this.func_215722_b(y));
        int edge = edgeSampler.getValue(this.func_215721_a(x), this.func_215722_b(y));
        if (edge == 1 && this.target.test(main)) {
            return this.replacement;
        }

        return main;
    }
}
