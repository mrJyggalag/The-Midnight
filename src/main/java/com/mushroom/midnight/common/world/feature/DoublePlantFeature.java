package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class DoublePlantFeature extends MidnightAbstractFeature {
    private final IBlockState state;
    private final ISpawnPredicate predicate;

    public DoublePlantFeature(IBlockState state, ISpawnPredicate predicate) {
        this.state = state;
        this.predicate = predicate;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        if (this.predicate.canSpawn(world, origin, this.state)) {
            world.setBlockState(origin, this.state.withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.LOWER), 2 | 16);
            world.setBlockState(origin.up(), this.state.withProperty(BlockDoublePlant.HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2 | 16);
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.GRASS;
    }
}
