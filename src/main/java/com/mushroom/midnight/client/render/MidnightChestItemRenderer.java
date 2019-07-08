package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.tile.base.MidnightChestTileEntity;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;

public class MidnightChestItemRenderer extends ItemStackTileEntityRenderer {
    private final ChestTileEntity shadowrootChest = new MidnightChestTileEntity();

    @Override
    public void renderByItem(ItemStack stack) {
        TileEntityRendererDispatcher.instance.renderAsItem(this.shadowrootChest);
    }
}
