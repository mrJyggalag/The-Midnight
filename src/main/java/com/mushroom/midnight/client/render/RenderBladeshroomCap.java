package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.entity.projectile.EntityBladeshroomCap;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderBladeshroomCap extends Render<EntityBladeshroomCap> {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final ItemStack STACK = new ItemStack(ModItems.BLADESHROOM_CAP);

    public RenderBladeshroomCap(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityBladeshroomCap entity, double x, double y, double z, float yaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        float rotationYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
        float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float spin = entity.prevSpin + (entity.spin - entity.prevSpin) * partialTicks;

        GlStateManager.rotate(rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(rotationPitch, 0.0F, 0.0F, 1.0F);

        GlStateManager.rotate(spin, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);

        GlStateManager.enableRescaleNormal();

        GlStateManager.scale(1.5F, 1.5F, 1.5F);

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        MC.getRenderItem().renderItem(STACK, ItemCameraTransforms.TransformType.GROUND);

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBladeshroomCap entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
