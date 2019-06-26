package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.GeneratablePlant;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class FungiFeature extends MidnightAbstractFeature {
    public static final BlockState[] FUNGI_STATES = new BlockState[] {
            MidnightBlocks.NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DEWSHROOM.getDefaultState(),
            MidnightBlocks.VIRIDSHROOM.getDefaultState()
    };

    public static final BlockState[] BOG_FUNGI_STATES = new BlockState[] {
            MidnightBlocks.NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DEWSHROOM.getDefaultState(),
            MidnightBlocks.VIRIDSHROOM.getDefaultState(),
            MidnightBlocks.BOGSHROOM.getDefaultState()
    };

    private final BlockState[] fungiStates;

    public FungiFeature(BlockState[] fungiStates) {
        this.fungiStates = fungiStates;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        BlockState state = this.fungiStates[rand.nextInt(this.fungiStates.length)];
        if (GeneratablePlant.canGenerate(world, origin, state)) {
            this.setBlockAndNotifyAdequately(world, origin, state);
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.SHROOM;
    }
}
