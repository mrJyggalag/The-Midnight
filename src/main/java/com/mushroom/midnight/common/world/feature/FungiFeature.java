package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.GeneratablePlant;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class FungiFeature extends MidnightAbstractFeature {
    public static final IBlockState[] FUNGI_STATES = new IBlockState[] {
            ModBlocks.NIGHTSHROOM.getDefaultState(),
            ModBlocks.DEWSHROOM.getDefaultState(),
            ModBlocks.VIRIDSHROOM.getDefaultState()
    };

    public static final IBlockState[] BOG_FUNGI_STATES = new IBlockState[] {
            ModBlocks.NIGHTSHROOM.getDefaultState(),
            ModBlocks.DEWSHROOM.getDefaultState(),
            ModBlocks.VIRIDSHROOM.getDefaultState(),
            ModBlocks.BOGSHROOM.getDefaultState()
    };

    private final IBlockState[] fungiStates;

    public FungiFeature(IBlockState[] fungiStates) {
        this.fungiStates = fungiStates;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        IBlockState state = this.fungiStates[rand.nextInt(this.fungiStates.length)];
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
