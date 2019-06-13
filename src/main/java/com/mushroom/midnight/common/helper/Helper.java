package com.mushroom.midnight.common.helper;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;

public class Helper {
    public static boolean isMidnightDimension(@Nullable World world) {
        return world != null && world.provider.getDimensionType() == ModDimensions.MIDNIGHT;
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
        return block == ModBlocks.NIGHTSTONE || block == ModBlocks.MIDNIGHT_GRASS || block == ModBlocks.MIDNIGHT_MYCELIUM || block == Blocks.GRASS;
    }

    public static boolean isGroundForMidnightPlant(Block block) {
        return isGroundForBoneMeal(block) || block == ModBlocks.MIDNIGHT_DIRT || block == ModBlocks.DECEITFUL_MUD || block == ModBlocks.DECEITFUL_PEAT || block == Blocks.FARMLAND || block == Blocks.DIRT;
    }
}
