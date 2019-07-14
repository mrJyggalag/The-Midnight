package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ShadeSquirreModel;
import com.mushroom.midnight.common.entity.creature.ShadeSquirreEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ShadeSquirreRenderer extends MobRenderer<ShadeSquirreEntity, ShadeSquirreModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/shade_squrrie.png");

    public ShadeSquirreRenderer(EntityRendererManager manager) {
        super(manager, new ShadeSquirreModel(), 0.4f);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ShadeSquirreEntity entity) {
        return TEXTURE;
    }
}
