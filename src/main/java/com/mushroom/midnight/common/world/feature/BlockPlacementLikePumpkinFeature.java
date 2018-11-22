package com.mushroom.midnight.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class BlockPlacementLikePumpkinFeature extends MidnightAbstractFeature {
    private final Block togenerate;
    private final Block placeon;


    public BlockPlacementLikePumpkinFeature (Block blocktogenerate, Block blocktoplaceon) {
        this.togenerate = blocktogenerate;
        this.placeon = blocktoplaceon;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = origin.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if (world.getBlockState(blockpos.down()).getBlock() == this.placeon && this.togenerate.canPlaceBlockAt(world, blockpos))
            {
                world.setBlockState(blockpos, this.togenerate.getDefaultState(), 2);
            }
        }

        return true;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.PUMPKIN;
    }
}
