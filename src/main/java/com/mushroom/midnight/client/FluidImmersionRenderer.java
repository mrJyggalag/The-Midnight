package com.mushroom.midnight.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.fluid.DarkWaterFluid;
import com.mushroom.midnight.common.fluid.MiasmaFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class FluidImmersionRenderer {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final ResourceLocation DARK_WATER_OVERLAY = new ResourceLocation(Midnight.MODID, "textures/effects/dark_water_overlay.png");

    public static IFluidState immersedFluid = Fluids.EMPTY.getDefaultState();

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START && CLIENT.player != null) {
            ActiveRenderInfo activeRenderInfo = CLIENT.gameRenderer.getActiveRenderInfo();
            immersedFluid = activeRenderInfo.getFluidState();
        }
    }

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        if (immersedFluid.getFluid() instanceof MiasmaFluid) {
            GlStateManager.fogMode(GlStateManager.FogMode.EXP);
            event.setDensity(2.0F);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (immersedFluid.getFluid() instanceof DarkWaterFluid) {
            renderOverlay(DARK_WATER_OVERLAY);
            event.setCanceled(true);
        }
    }

    private static void renderOverlay(ResourceLocation texture) {
        CLIENT.getTextureManager().bindTexture(texture);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.pushMatrix();

        float brightness = CLIENT.player.getBrightness();
        GlStateManager.color4f(brightness, brightness, brightness, 0.5F);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        float scrollX = -CLIENT.player.rotationYaw / 64.0F;
        float scrollY = CLIENT.player.rotationPitch / 64.0F;

        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        builder.pos(-1.0, -1.0, -0.5).tex(4.0F + scrollX, 4.0F + scrollY).endVertex();
        builder.pos(1.0, -1.0, -0.5).tex(scrollX, 4.0F + scrollY).endVertex();
        builder.pos(1.0, 1.0, -0.5).tex(scrollX, scrollY).endVertex();
        builder.pos(-1.0, 1.0, -0.5).tex(4.0F + scrollX, scrollY).endVertex();
        tessellator.draw();

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }
}
