package com.mushroom.midnight.common.entity.util;

import com.mushroom.midnight.common.util.MatrixStack;
import net.minecraft.entity.EntityLivingBase;

public interface TransformationMethod<E extends EntityLivingBase> {
    void transform(E entity, MatrixStack matrix);
}
