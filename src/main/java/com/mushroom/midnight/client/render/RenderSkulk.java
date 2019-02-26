package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelSkulk;
import com.mushroom.midnight.common.entity.creature.EntitySkulk;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderSkulk<T extends EntitySkulk> extends RenderLiving<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/skulk.png");

    public RenderSkulk(RenderManager rendermanager) {
        super(rendermanager, new ModelSkulk(), 0.4f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }
}
