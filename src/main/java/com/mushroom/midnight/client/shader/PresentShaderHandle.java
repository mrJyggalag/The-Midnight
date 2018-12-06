package com.mushroom.midnight.client.shader;

import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.shader.ShaderUniform;

public class PresentShaderHandle implements ShaderHandle {
    private final ShaderManager handle;

    PresentShaderHandle(ShaderManager handle) {
        this.handle = handle;
    }

    @Override
    public ShaderUniform get(String key) {
        return this.handle.getShaderUniformOrDefault(key);
    }

    @Override
    public void use() {
        this.handle.useShader();
    }

    @Override
    public void close() {
        this.handle.endShader();
    }
}
