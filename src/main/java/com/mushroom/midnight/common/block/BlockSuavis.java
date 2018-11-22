package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BlockSuavis extends Block  implements IModelProvider {

    public BlockSuavis() {
        super(Material.GOURD, MapColor.LIGHT_BLUE);
        this.setLightLevel(0.8F);
        this.setCreativeTab(Midnight.DECORATION_TAB);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.RAW_SUAVIS;
    }

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 2), 1, 4);
    }
}
