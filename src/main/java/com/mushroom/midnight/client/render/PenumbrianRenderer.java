package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.PenumbrianModel;
import com.mushroom.midnight.common.entity.creature.PenumbrianEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class PenumbrianRenderer extends LivingRenderer<PenumbrianEntity, PenumbrianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/penumbrian.png");

    public PenumbrianRenderer(EntityRendererManager manager) {
        super(manager, new PenumbrianModel(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(PenumbrianEntity entity) {
        return TEXTURE;
    }
}
