package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelDeceitfulSnapper;
import com.mushroom.midnight.common.entity.creature.EntityDeceitfulSnapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderDeceitfulSnapper extends RenderLiving<EntityDeceitfulSnapper> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/deceitful_snapper.png");

    public RenderDeceitfulSnapper(RenderManager rendermanager) {
        super(rendermanager, new ModelDeceitfulSnapper(), 0f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityDeceitfulSnapper entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityDeceitfulSnapper entity, float partialTicks) {
        GlStateManager.translate(0f, 1.4f, -0.05f);
        GlStateManager.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    protected float getDeathMaxRotation(EntityDeceitfulSnapper entity) {
        return 180f;
    }
}
