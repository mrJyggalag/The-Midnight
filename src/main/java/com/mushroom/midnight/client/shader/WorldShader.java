package com.mushroom.midnight.client.shader;

import com.mushroom.midnight.Midnight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldShader implements ISelectiveResourceReloadListener {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private final ResourceLocation location;

    private final Map<String, ResourceLocation> textures = new HashMap<>();

    private ShaderManager manager;
    private boolean initialized;

    public WorldShader(ResourceLocation location) {
        this.location = location;
        ((IReloadableResourceManager) MC.getResourceManager()).registerReloadListener(this);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(VanillaResourceType.SHADERS)) {
            if (this.manager != null) {
                this.manager.deleteShader();
            }
            for (ResourceLocation texture : this.textures.values()) {
                this.prepareTexture(texture);
            }
            this.initialized = false;
        }
    }

    public WorldShader withTextureSampler(String key, ResourceLocation texture) {
        this.prepareTexture(texture);
        this.textures.put(key, texture);
        return this;
    }

    private void prepareTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
    }

    public ShaderHandle use(Consumer<ShaderHandle> setup) {
        ShaderHandle handle = this.makeHandle();
        setup.accept(handle);
        handle.get("FogMode").set(GL11.glGetInteger(GL11.GL_FOG_MODE), 0, 0, 0);

        handle.use();

        return handle;
    }

    private ShaderHandle makeHandle() {
        ShaderManager manager = this.get();
        if (manager != null) {
            return new PresentShaderHandle(manager);
        }
        return new VoidShaderHandle();
    }

    private ShaderManager get() {
        if (!this.initialized) {
            this.initialize();
        }
        return this.manager;
    }

    private void initialize() {
        if (this.initialized) {
            return;
        }

        try {
            Minecraft mc = Minecraft.getMinecraft();
            this.manager = new ShaderManager(mc.getResourceManager(), this.location.toString());

            for (Map.Entry<String, ResourceLocation> entry : this.textures.entrySet()) {
                ITextureObject texture = mc.getTextureManager().getTexture(entry.getValue());
                this.manager.addSamplerTexture(entry.getKey(), texture);
            }
        } catch (IOException e) {
            Midnight.LOGGER.error("Failed to load shader {}", this.location, e);
        } finally {
            this.initialized = true;
        }
    }

    public boolean isAvailable() {
        if (!OpenGlHelper.shadersSupported) {
            return false;
        }
        return !this.initialized || this.manager != null;
    }
}
