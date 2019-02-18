package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelRacoon;
import com.mushroom.midnight.common.entity.creature.EntityRacoon;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderRacoon<T extends EntityRacoon> extends RenderLiving<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/racoon.png");

    public RenderRacoon(RenderManager rendermanager) {
        super(rendermanager, new ModelRacoon(), 0.4f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }
}
