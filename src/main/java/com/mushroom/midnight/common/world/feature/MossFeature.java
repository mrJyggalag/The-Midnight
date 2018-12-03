package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.common.block.BlockDeceitfulMoss;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class MossFeature extends MidnightAbstractFeature {
    private final IBlockState state;

    public MossFeature(IBlockState state) {
        this.state = state;
    }

    @Override
    public boolean placeFeature(World world, Random rand, BlockPos origin) {
        EnumFacing facing = EnumFacing.VALUES[rand.nextInt(EnumFacing.VALUES.length)];
        if (this.state.getBlock().canPlaceBlockOnSide(world, origin, facing)) {
            this.setBlockAndNotifyAdequately(world, origin, this.state.withProperty(BlockDeceitfulMoss.FACING, facing));
            return true;
        }
        return false;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.GRASS;
    }
}
