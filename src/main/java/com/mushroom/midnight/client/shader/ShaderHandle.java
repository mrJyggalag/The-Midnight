package com.mushroom.midnight.client.shader;

import net.minecraft.client.shader.ShaderDefault;

public interface ShaderHandle extends AutoCloseable {
    ShaderDefault DEFAULT_UNIFORM = new ShaderDefault();

    ShaderDefault getUniform(String key);

    void use();

    @Override
    void close();
}
