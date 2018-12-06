package com.mushroom.midnight.client.shader;

import net.minecraft.client.shader.ShaderUniform;

public class VoidShaderHandle implements ShaderHandle {
    @Override
    public ShaderUniform get(String key) {
        return DEFAULT_UNIFORM;
    }

    @Override
    public void use() {
    }

    @Override
    public void close() {
    }
}
