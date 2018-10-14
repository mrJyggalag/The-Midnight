package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelRifter;
import com.mushroom.midnight.common.entity.creature.EntityRifter;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRifter extends RenderLiving<EntityRifter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/rifter.png");

    public RenderRifter(RenderManager renderManager) {
        super(renderManager, new ModelRifter(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityRifter entity) {
        return TEXTURE;
    }

}
