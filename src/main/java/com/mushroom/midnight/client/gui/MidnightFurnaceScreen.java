package com.mushroom.midnight.client.gui;

import com.mushroom.midnight.common.container.MidnightFurnaceContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
// TODO make it when able to launch midnight
public class MidnightFurnaceScreen extends ContainerScreen<MidnightFurnaceContainer> {
    public MidnightFurnaceScreen(MidnightFurnaceContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
    /*private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");
    private final PlayerInventory playerInventory;
    private final IInventory tileFurnace;

    public MidnightFurnaceScreen(MidnightFurnaceContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.playerInventory = playerInventory;
        tileFurnace = container;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.title.getFormattedText();
        font.drawString(s, xSize / 2.0F - font.getStringWidth(s) / 2.0F, 6, 4210752);
        font.drawString(playerInventory.getDisplayName().getFormattedText(), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        blit(i, j, 0, 0, xSize, ySize);
        if (TileEntityMidnightFurnace.isBurning(tileFurnace)) {
            int k = getBurnLeftScaled(13);
            blit(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        int l = getCookProgressScaled(24);
        blit(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled(int pixels) {
        int i = tileFurnace.getField(2);
        int j = tileFurnace.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int i = tileFurnace.getField(1);
        if (i == 0) { i = 200; }
        return tileFurnace.getField(0) * pixels / i;
    }*/
}
