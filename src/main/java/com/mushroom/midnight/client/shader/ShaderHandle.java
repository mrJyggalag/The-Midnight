package com.mushroom.midnight.client.shader;

import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderUniform;

public interface ShaderHandle extends AutoCloseable {
    ShaderDefault DEFAULT_UNIFORM = new ShaderDefault();

    ShaderUniform get(String key);

    void use();

    @Override
    void close();
}
