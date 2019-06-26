package com.mushroom.midnight.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.CrystalBugModel;
import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CrystalBugRenderer extends LivingRenderer<CrystalBugEntity, CrystalBugModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/crystal_bug.png");

    public CrystalBugRenderer(EntityRendererManager manager) {
        super(manager, new CrystalBugModel(), 0.2f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(CrystalBugEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(CrystalBugEntity entity, float partialTicks) {
        if (entity.isStanding()) {
            GlStateManager.translatef(0f, 0.3f, 0f);
        } else {
            GlStateManager.translatef(0f, 0.4f, 0f);
        }
        GlStateManager.scalef(0.3f, 0.3f, 0.3f);
    }

    @Override
    protected float getDeathMaxRotation(CrystalBugEntity entity) {
        return 180f;
    }

    @Override
    public void setLightmap(CrystalBugEntity entity) {
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
    }
}
