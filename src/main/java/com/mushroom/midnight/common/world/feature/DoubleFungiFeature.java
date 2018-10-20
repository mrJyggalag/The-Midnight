package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class DoubleFungiFeature extends MidnightAbstractFeature {
    private static final IBlockState[] FUNGI_STATES = new IBlockState[] {
            ModBlocks.DOUBLE_NIGHTSHROOM.getDefaultState(),
            ModBlocks.DOUBLE_DEWSHROOM.getDefaultState(),
            ModBlocks.DOUBLE_VIRIDSHROOM.getDefaultState()
    };

    @Override
    public boolean generate(World world, Random rand, BlockPos origin) {
        IBlockState state = FUNGI_STATES[rand.nextInt(FUNGI_STATES.length)];
        if (((BlockBush) ModBlocks.NIGHTSHROOM).canBlockStay(world, origin, state)) {
            world.setBlockState(origin, state.withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.LOWER), 2 | 16);
            world.setBlockState(origin.up(), state.withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2 | 16);
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.SHROOM;
    }
}
