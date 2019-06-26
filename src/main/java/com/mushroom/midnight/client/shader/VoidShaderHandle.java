package com.mushroom.midnight.client.shader;

import net.minecraft.client.shader.ShaderDefault;

public class VoidShaderHandle implements ShaderHandle {
    @Override
    public ShaderDefault getUniform(String key) {
        return DEFAULT_UNIFORM;
    }

    @Override
    public void use() {
    }

    @Override
    public void close() {
    }
}
