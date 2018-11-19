package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelHunter;
import com.mushroom.midnight.common.entity.creature.EntityHunter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHunter extends RenderLiving<EntityHunter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/hunter.png");

    public RenderHunter(RenderManager renderManager) {
        super(renderManager, new ModelHunter(), 0.5F);
    }

    @Override
    protected void applyRotations(EntityHunter entity, float age, float rotationYaw, float partialTicks) {
        super.applyRotations(entity, age, rotationYaw, partialTicks);

        float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float roll = entity.prevRoll + (entity.roll - entity.prevRoll) * partialTicks;

        GlStateManager.rotate(roll, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-pitch, 1.0F, 0.0F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityHunter entity) {
        return TEXTURE;
    }
}
