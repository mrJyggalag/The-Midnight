package com.mushroom.midnight.client.render;

import com.mushroom.midnight.client.model.ModelPlaceholderSkeleton;
import com.mushroom.midnight.common.entity.EntityRifter;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRifter extends RenderBiped<EntityRifter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderRifter(RenderManager renderManager) {
        super(renderManager, new ModelPlaceholderSkeleton(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRifter entity) {
        return TEXTURE;
    }
}
