package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelSkulk;
import com.mushroom.midnight.common.entity.creature.EntitySkulk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderSkulk<T extends EntitySkulk> extends RenderLiving<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/skulk.png");

    public RenderSkulk(RenderManager rendermanager) {
        super(rendermanager, new ModelSkulk(), 0.4f);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        boolean isTransparent = false;
        if (entity.isStealth()) {
            if (entity.getDistanceSq(Minecraft.getMinecraft().player) >= 8d) {
                return;
            }
            isTransparent = true;
            GlStateManager.Profile.TRANSPARENT_MODEL.apply();
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        if (isTransparent) {
            GlStateManager.Profile.TRANSPARENT_MODEL.clean();
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }
}
