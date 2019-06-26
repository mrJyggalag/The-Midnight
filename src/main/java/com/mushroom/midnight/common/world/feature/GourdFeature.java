package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class GourdFeature extends MidnightAbstractFeature {
    private final BlockState state;
    private final Block surface;
    private final int chance;

    public GourdFeature(BlockState state, Block surface, int chance) {
        this.state = state;
        this.surface = surface;
        this.chance = chance;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        if (random.nextInt(this.chance) == 0) {
            BlockState surfaceState = world.getBlockState(origin.down());
            if (surfaceState.getBlock() == this.surface && this.state.getBlock().canPlaceBlockAt(world, origin)) {
                this.setBlockAndNotifyAdequately(world, origin, this.state);
            }
        }

        return true;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.PUMPKIN;
    }
}
