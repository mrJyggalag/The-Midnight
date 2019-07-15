package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.ClientEventHandler;
import com.mushroom.midnight.client.model.ShadeSquirreModel;
import com.mushroom.midnight.common.entity.creature.ShadeSquirreEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

public class ShadeSquirreRenderer extends MobRenderer<ShadeSquirreEntity, ShadeSquirreModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/shade_squrrie.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/shade_squrrie_emissive.png");


    public ShadeSquirreRenderer(EntityRendererManager manager) {
        super(manager, new ShadeSquirreModel(), 0.4f);
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new EmissiveLayerRenderer<>(this, EMISSIVE_TEXTURE, ShadeSquirreRenderer::computeBrightness, ShadeSquirreRenderer::computeColor));
    }

    private static int computeColor(ShadeSquirreEntity entity, float partialTicks) {
        return 0xdccf70;
    }

    private static int computeBrightness(ShadeSquirreEntity entity, float partialTicks) {
        double totalTicks = entity.ticksExisted + partialTicks;

        float flicker = computeFlicker(totalTicks, partialTicks);

        float flickerBrightness = 12000 * flicker;


        return MathHelper.floor(flickerBrightness * 61680 + flickerBrightness * 61680);
    }

    private static float computeFlicker(double totalTicks, float partialTicks) {
        float lerpedFlicker = ClientEventHandler.prevFlicker + (ClientEventHandler.flicker - ClientEventHandler.prevFlicker) * partialTicks;
        float pulse = (float) (Math.sin(totalTicks * 0.2) + 1.0) * 0.4F;
        return MathHelper.clamp(lerpedFlicker + pulse, 0.0F, 1.0F);
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(ShadeSquirreEntity entity) {
        return TEXTURE;
    }
}
