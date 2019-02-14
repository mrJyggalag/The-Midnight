package com.mushroom.midnight.common.util;

import net.minecraft.entity.Entity;

public interface IProxy {
    void onInit();
    boolean isClientPlayer(Entity entity);
}
