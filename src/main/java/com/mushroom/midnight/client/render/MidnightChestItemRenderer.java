package com.mushroom.midnight.client.render;

import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.tile.base.MidnightChestTileEntity;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class MidnightChestItemRenderer extends ItemStackTileEntityRenderer {
    private final MidnightChestTileEntity chest = new MidnightChestTileEntity();

    public MidnightChestItemRenderer(MidnightChestBlock.MidnightChestModel chestModel) {
        chest.setChestModel(chestModel);
    }

    @Override
    public void renderByItem(ItemStack stack) {
        TileEntityRendererDispatcher.instance.renderAsItem(this.chest);
    }
}
