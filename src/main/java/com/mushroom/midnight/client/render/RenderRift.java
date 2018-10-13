package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.entity.EntityRift;
import com.mushroom.midnight.common.entity.RiftBridge;
import com.mushroom.midnight.common.entity.RiftGeometry;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.vecmath.Point2f;

public class RenderRift extends Render<EntityRift> {
    public RenderRift(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityRift entity, double x, double y, double z, float entityYaw, float partialTicks) {
        RiftBridge bridge = entity.getBridge();
        if (bridge == null) {
            return;
        }

        float openProgress = bridge.getOpenAnimation(partialTicks);
        float unstableTime = bridge.getUnstableAnimation(partialTicks);

        if (openProgress > 0.0F) {
            float time = entity.ticksExisted + partialTicks;

            float openAnimation = openProgress / EntityRift.OPEN_TIME;
            openAnimation = (float) (1.0F - Math.pow(1.0 - openAnimation, 3.0));
            openAnimation = MathHelper.clamp(openAnimation, 0.0F, 1.0F);

            float unstableAnimation = unstableTime / EntityRift.UNSTABLE_TIME;
            unstableAnimation = (float) (1.0F - Math.pow(1.0 - unstableAnimation, 2.0));
            unstableAnimation = MathHelper.clamp(unstableAnimation, 0.0F, 1.0F);

            RiftGeometry geometry = entity.getGeometry();
            Point2f[] ring = geometry.computePath(openAnimation, unstableAnimation, time);

            GlStateManager.pushMatrix();

            GlStateManager.translate(x, y + entity.height / 2.0F, z);
            GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuffer();

            GlStateManager.disableCull();
            GlStateManager.disableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

            builder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

            builder.pos(0.0, 0.0, 0.0).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
            for (int i = 0; i < ring.length + 1; i++) {
                Point2f point = ring[i % ring.length];
                builder.pos(point.x, point.y, 0.0F).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
            }

            tessellator.draw();

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, OpenGlHelper.lastBrightnessX, OpenGlHelper.lastBrightnessY);

            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();

            GlStateManager.popMatrix();
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRift entity) {
        return null;
    }
}
