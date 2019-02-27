package com.mushroom.midnight.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.mushroom.midnight.Midnight.MODID;

@SideOnly(Side.CLIENT)
public class TEISRShield extends TileEntityItemStackRenderer {
    private static ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/blocks/hardened_clay_stained_purple.png");
    private final ModelShield modelShield = new ModelShield(); // TODO model rockshroom

    @Override
    public void renderByItem(ItemStack stack) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1f, -1f, -1f);
        this.modelShield.render();
        GlStateManager.popMatrix();
    }
}
