package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.util.MatrixStack;
import net.minecraft.entity.LivingEntity;

public interface TransformationMethod<E extends LivingEntity> {
    void transform(E entity, MatrixStack matrix);
}
