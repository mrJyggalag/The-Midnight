package com.mushroom.midnight.client;

import com.mushroom.midnight.client.model.ModModelRegistry;
import com.mushroom.midnight.common.CommonProxy;

public class ClientProxy extends CommonProxy {
    @Override
    public void onInit() {
        super.onInit();
        ModModelRegistry.onInit();
    }
}
