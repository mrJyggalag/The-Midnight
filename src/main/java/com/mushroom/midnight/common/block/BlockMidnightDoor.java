package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightDoor extends BlockDoor {
    private final Supplier<Item> itemSupplier;

    public BlockMidnightDoor(Supplier<Item> itemSupplier) {
        this(false, itemSupplier);
    }

    public BlockMidnightDoor(boolean metallic, Supplier<Item> itemSupplier) {
        super(metallic ? Material.IRON : Material.WOOD);
        this.setHardness(metallic ? 5.0F : 3.0F);
        this.setSoundType(metallic ? SoundType.METAL : SoundType.WOOD);
        this.setCreativeTab(ModTabs.DECORATION_TAB);
        this.itemSupplier = itemSupplier;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : this.itemSupplier.get();
    }

    @Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        return new ItemStack(this.itemSupplier.get());
    }
}
