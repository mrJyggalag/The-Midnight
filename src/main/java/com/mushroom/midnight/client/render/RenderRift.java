package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.entities.EntityRift;
import com.mushroom.midnight.common.entities.RiftGeometry;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.vecmath.Point2f;

public class RenderRift extends Render<EntityRift> {
    public RenderRift(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityRift entity, double x, double y, double z, float entityYaw, float partialTicks) {
        RiftGeometry geometry = entity.getGeometry();
        if (geometry != null) {
            Point2f[] ring = geometry.getRing();

            GlStateManager.pushMatrix();

            GlStateManager.translate(x, y + entity.height / 2.0F, z);
            GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder builder = tessellator.getBuffer();

            GlStateManager.disableCull();
            GlStateManager.disableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            builder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);

            builder.pos(0.0, 0.0, 0.0).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
            for (int i = 0; i < ring.length + 1; i++) {
                Point2f point = ring[i % ring.length];
                builder.pos(point.x, point.y, 0.0F).color(0.0F, 0.0F, 0.0F, 1.0F).endVertex();
            }

            tessellator.draw();

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
