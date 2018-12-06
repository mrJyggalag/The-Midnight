package com.mushroom.midnight.client;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.block.BlockDarkWater;
import com.mushroom.midnight.common.block.BlockMiasmaFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Midnight.MODID, value = Side.CLIENT)
public class FluidImmersionRenderer {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final ResourceLocation DARK_WATER_OVERLAY = new ResourceLocation(Midnight.MODID, "textures/effects/dark_water_overlay.png");

    public static IBlockState immersedState = Blocks.AIR.getDefaultState();

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START && MC.player != null) {
            immersedState = ActiveRenderInfo.getBlockStateAtEntityViewpoint(MC.world, MC.player, event.renderTickTime);
        }
    }

    @SubscribeEvent
    public static void onSetupFogDensity(EntityViewRenderEvent.RenderFogEvent.FogDensity event) {
        if (immersedState.getBlock() instanceof BlockMiasmaFluid) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            event.setDensity(2.0F);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (immersedState.getBlock() instanceof BlockDarkWater) {
            renderOverlay(DARK_WATER_OVERLAY);
            event.setCanceled(true);
        }
    }

    private static void renderOverlay(ResourceLocation texture) {
        MC.getTextureManager().bindTexture(texture);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.pushMatrix();

        float brightness = MC.player.getBrightness();
        GlStateManager.color(brightness, brightness, brightness, 0.5F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        float scrollX = -MC.player.rotationYaw / 64.0F;
        float scrollY = MC.player.rotationPitch / 64.0F;

        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        builder.pos(-1.0, -1.0, -0.5).tex(4.0F + scrollX, 4.0F + scrollY).endVertex();
        builder.pos(1.0, -1.0, -0.5).tex(scrollX, 4.0F + scrollY).endVertex();
        builder.pos(1.0, 1.0, -0.5).tex(scrollX, scrollY).endVertex();
        builder.pos(-1.0, 1.0, -0.5).tex(4.0F + scrollX, scrollY).endVertex();
        tessellator.draw();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }
}
