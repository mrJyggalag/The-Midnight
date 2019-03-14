package com.mushroom.midnight.client.render;

import com.mushroom.midnight.client.model.ModelBomb;
import com.mushroom.midnight.common.item.ItemSporeBomb;
import com.mushroom.midnight.common.item.ItemSporeBomb.BombType;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.mushroom.midnight.Midnight.MODID;

@SideOnly(Side.CLIENT)
public class TEISRBomb extends TileEntityItemStackRenderer {
    private final static ResourceLocation TEXTURE_NIGHT = new ResourceLocation(MODID, "textures/items/nightshroom_spore_bomb.png");
    private final static ResourceLocation TEXTURE_DEW = new ResourceLocation(MODID, "textures/items/dewshroom_spore_bomb.png");
    private final static ResourceLocation TEXTURE_VIRID = new ResourceLocation(MODID, "textures/items/viridshroom_spore_bomb.png");
    private final static ResourceLocation TEXTURE_BOG = new ResourceLocation(MODID, "textures/items/bogshroom_spore_bomb.png");
    private final ModelBomb modelBomb = new ModelBomb();

    @Override
    public void renderByItem(ItemStack stack) {
        if (stack.getItem() == ModItems.SPORE_BOMB) {
            WorldClient world = Minecraft.getMinecraft().world;
            float fuseTime = world != null ? ItemSporeBomb.getFuseTime(world, stack) / (float) ItemSporeBomb.MAX_FUSE_TIME : 1f;
            // TODO fusing animation on client
            if (fuseTime < 1f) {
                float ratio = fuseTime * 10f % 1;
                GlStateManager.color(1f, 1f, 1f, ratio < 0.5f ? 0.5f : 1f);
            }
            bindTexture(BombType.fromStack(stack));
            GlStateManager.pushMatrix();
            GlStateManager.enableCull();
            GlStateManager.translate(0.5f, 0.45f, 0.5f);
            GlStateManager.rotate(180f, -1f, 0f, 0f);
            this.modelBomb.render();
            GlStateManager.disableCull();
            GlStateManager.popMatrix();
        }
    }

    private void bindTexture(BombType bombType) {
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        switch (bombType) {
            case NIGHTSHROOM:
                textureManager.bindTexture(TEXTURE_NIGHT);
                break;
            case DEWSHROOM:
                textureManager.bindTexture(TEXTURE_DEW);
                break;
            case VIRIDSHROOM:
                textureManager.bindTexture(TEXTURE_VIRID);
                break;
            case BOGSHROOM:
                textureManager.bindTexture(TEXTURE_BOG);
                break;
        }
    }
}
