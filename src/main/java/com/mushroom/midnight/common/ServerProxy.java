package com.mushroom.midnight.common;

import com.mushroom.midnight.common.util.IProxy;
import net.minecraft.entity.Entity;

public class ServerProxy implements IProxy {
    @Override
    public boolean isClientPlayer(Entity entity) {
        return false;
    }
}
