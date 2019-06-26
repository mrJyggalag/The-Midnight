package com.mushroom.midnight.common.helper;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;

public class Helper {
    public static boolean isMidnightDimension(@Nullable World world) {
        return world != null && world.dimension.getType() == MidnightDimensions.MIDNIGHT;
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
        return block == MidnightBlocks.NIGHTSTONE || block == MidnightBlocks.MIDNIGHT_GRASS || block == MidnightBlocks.MIDNIGHT_MYCELIUM || block == Blocks.GRASS;
    }

    public static boolean isGroundForMidnightPlant(Block block) {
        return isGroundForBoneMeal(block) || block == MidnightBlocks.MIDNIGHT_DIRT || block == MidnightBlocks.DECEITFUL_MUD || block == MidnightBlocks.DECEITFUL_PEAT || block == Blocks.FARMLAND || block == Blocks.DIRT;
    }
}
