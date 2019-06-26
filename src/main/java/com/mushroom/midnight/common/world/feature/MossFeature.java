package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.DeceitfulMossBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class MossFeature extends MidnightAbstractFeature {
    private final BlockState state;

    public MossFeature(BlockState state) {
        this.state = state;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        Direction facing = Direction.VALUES[rand.nextInt(Direction.VALUES.length)];
        if (this.state.getBlock().canPlaceBlockOnSide(world, origin, facing)) {
            this.setBlockAndNotifyAdequately(world, origin, this.state.with(DeceitfulMossBlock.FACING, facing));
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.GRASS;
    }
}
