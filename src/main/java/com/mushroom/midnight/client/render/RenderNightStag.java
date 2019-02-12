package com.mushroom.midnight.client.render;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.model.ModelNightStag;
import com.mushroom.midnight.common.entity.creature.EntityNightStag;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class RenderNightStag extends RenderLiving<EntityNightStag> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nightstag.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(Midnight.MODID, "textures/entities/nightstag_emissive.png");

    private static final int FLICK_BRIGHT_LOW = 50;
    private static final int FLICK_BRIGHT_UP = 200;

    private static final int PULSE_BRIGHT_LOW = 200;
    private static final int PULSE_BRIGHT_UP = 240;

    private static float flicker;
    private static float prevFlicker;

    public RenderNightStag(RenderManager renderManager) {
        super(renderManager, new ModelNightStag(), 0.0F);
        this.addLayer(new LayerRendererEmissive<>(new ModelNightStag(), EMISSIVE_TEXTURE, RenderNightStag::computeBrightness, RenderNightStag::computeColor));
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            prevFlicker = flicker;

            double targetFlicker = Math.random();
            targetFlicker *= targetFlicker;

            flicker += (targetFlicker - flicker) * 0.5;
        }
    }

    private static int computeColor(EntityNightStag entity, float partialTicks) {
        switch (entity.getAntlerType()) {
            case 1:
                return 0xA63E3E; // red
            case 2:
                return 0x596E5B; // green
            case 3:
                return 0x616D90; // blue
            case 4:
                return 0x90A1D1; // light blue
            case 5:
                return 0xD13DC5; // pink
            case 6:
                return 0xD77CD0; // light pink
            case 7:
                return 0xE1CEE1; // white
            case 8:
                return 0x1A256C; // dark blue
            case 0: default:
                return 0x8051B6; // purple
        }
    }

    private static int computeBrightness(EntityNightStag entity, float partialTicks) {
        double totalTicks = entity.ticksExisted + partialTicks;

        float flicker = computeFlicker(totalTicks, partialTicks);
        float pulse = (float) ((Math.sin(totalTicks * 0.125) + 1.0) * 0.5F);

        float health = entity.getHealth() / entity.getMaxHealth();

        float flickerBrightness = FLICK_BRIGHT_LOW + (FLICK_BRIGHT_UP - FLICK_BRIGHT_LOW) * flicker;
        float healthyBrightness = PULSE_BRIGHT_LOW + (PULSE_BRIGHT_UP - PULSE_BRIGHT_LOW) * pulse;

        return MathHelper.floor(flickerBrightness * (1.0F - health) + healthyBrightness * health);
    }

    private static float computeFlicker(double totalTicks, float partialTicks) {
        float lerpedFlicker = prevFlicker + (flicker - prevFlicker) * partialTicks;
        float pulse = (float) (Math.sin(totalTicks * 0.2) + 1.0) * 0.4F;
        return MathHelper.clamp(lerpedFlicker + pulse, 0.0F, 1.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityNightStag entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityNightStag entity, float partialTicks) {
        super.preRenderCallback(entity, partialTicks);
        if (entity.isChild()) {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
