package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelNightStag;
import com.mushroom.midnight.common.entity.creature.EntityNightStag;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderNightStag extends RenderLiving<EntityNightStag> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nightstag.png");

    public RenderNightStag(RenderManager rendermanager) {
        super(rendermanager, new ModelNightStag(), 0f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityNightStag entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityNightStag entity, float partialTicks) {
        super.preRenderCallback(entity, partialTicks);
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
