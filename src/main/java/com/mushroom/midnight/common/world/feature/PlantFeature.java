package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class PlantFeature extends MidnightAbstractFeature {
    private final IBlockState state;
    protected final ISpawnPredicate predicate;

    public PlantFeature(IBlockState state, ISpawnPredicate predicate) {
        this.state = state;
        this.predicate = predicate;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        if (this.predicate.canSpawn(world, origin, this.state)) {
            this.setBlockAndNotifyAdequately(world, origin, this.state);
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.GRASS;
    }
}
