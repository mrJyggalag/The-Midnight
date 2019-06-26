package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.GeneratablePlant;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class DoubleFungiFeature extends MidnightAbstractFeature {
    public static final BlockState[] FUNGI_STATES = new BlockState[] {
            MidnightBlocks.DOUBLE_NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_DEWSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_VIRIDSHROOM.getDefaultState()
    };

    public static final BlockState[] BOG_FUNGI_STATES = new BlockState[] {
            MidnightBlocks.DOUBLE_NIGHTSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_DEWSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_VIRIDSHROOM.getDefaultState(),
            MidnightBlocks.DOUBLE_BOGSHROOM.getDefaultState()
    };

    private final BlockState[] fungiStates;

    public DoubleFungiFeature(BlockState[] fungiStates) {
        this.fungiStates = fungiStates;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        BlockState state = this.fungiStates[rand.nextInt(this.fungiStates.length)];
        if (GeneratablePlant.canGenerate(world, origin, state)) {
            world.setBlockState(origin, state.with(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.LOWER), 2 | 16);
            world.setBlockState(origin.up(), state.with(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2 | 16);
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.SHROOM;
    }
}
