package com.mushroom.midnight.common.helper;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;

public class Helper {
    public static boolean isMidnightDimension(@Nullable World world) {
        return world != null && world.dimension.getType() == MidnightDimensions.midnight();
    }

    public static boolean isNotFakePlayer(@Nullable Entity entity) {
        return !(entity instanceof FakePlayer);
    }

    public static float[] getRGBColorF(int color) {
        float[] rgb = new float[3];
        rgb[0] = ((color >> 16) & 255) / 255f;
        rgb[1] = ((color >> 8) & 255) / 255f;
        rgb[2] = (color & 255) / 255f;
        return rgb;
    }

    public static boolean isGroundForBoneMeal(Block block) {
        return block == MidnightBlocks.NIGHTSTONE || block == MidnightBlocks.GRASS_BLOCK || block == MidnightBlocks.MYCELIUM || block == Blocks.GRASS;
    }

    public static boolean isGroundForMidnightPlant(Block block) {
        return isGroundForBoneMeal(block) || block == MidnightBlocks.DIRT || block == MidnightBlocks.DECEITFUL_MUD || block == MidnightBlocks.DECEITFUL_PEAT || block == Blocks.FARMLAND || block == Blocks.DIRT;
    }

    public static void spawnItemStack(World world, BlockPos pos, Block block) {
        spawnItemStack(world, pos, block.asItem());
    }

    public static void spawnItemStack(World world, BlockPos pos, Item item) {
        spawnItemStack(world, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, item);
    }

    public static void spawnItemStack(World world, double x, double y, double z, Item item) {
        spawnItemStack(world, x, y, z, new ItemStack(item, 1));
    }

    public static void spawnItemStack(World world, BlockPos pos, ItemStack stack) {
        spawnItemStack(world, pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, stack);
    }

    public static void spawnItemStack(World world, double x, double y, double z, ItemStack stack) {
        world.addEntity(new ItemEntity(world, x, y, z, stack));
    }
}
