package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelNova;
import com.mushroom.midnight.common.entity.creature.EntityNova;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderNova extends RenderLiving<EntityNova> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nova.png");

    public RenderNova(RenderManager rendermanager) {
        super(rendermanager, new ModelNova(), 0.6f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityNova entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityNova entity, float partialTicks) {
        GlStateManager.translate(0f, -0.25f + (MathHelper.sin((entity.ticksExisted + partialTicks) * 0.14f - 0.5f) / 8), 0f);
        GlStateManager.scale(0.8f, 0.8f, 0.8f);
    }
}
