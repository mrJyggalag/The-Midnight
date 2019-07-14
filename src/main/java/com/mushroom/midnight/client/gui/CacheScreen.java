package com.mushroom.midnight.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mushroom.midnight.common.inventory.CacheContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.mushroom.midnight.Midnight.MODID;

@OnlyIn(Dist.CLIENT)
public class CacheScreen extends ContainerScreen<CacheContainer> implements IHasContainer<CacheContainer> {
    private static final ResourceLocation CACHE_BG_TEXTURE = new ResourceLocation(MODID, "textures/gui/gui_cache.png");

    public CacheScreen(CacheContainer container, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(container, playerInventory, iTextComponent);
        this.xSize = 174;
        this.ySize = 127;
        this.passEvents = false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleString = this.title.getFormattedText();
        float startPos = (this.xSize - this.font.getStringWidth(titleString)) / 2f;
        this.font.drawString(titleString, startPos, 8f, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(CACHE_BG_TEXTURE);
        blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
