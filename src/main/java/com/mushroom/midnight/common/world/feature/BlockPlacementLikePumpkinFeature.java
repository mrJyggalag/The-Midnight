package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
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
        int chance = Math.round(random.nextInt(2));
        Midnight.LOGGER.info(chance);
        if (chance == 1) {
            if (world.getBlockState(origin.down()).getBlock() == this.placeon && this.togenerate.canPlaceBlockAt(world, origin)) {
                this.setBlockAndNotifyAdequately(world, origin, this.togenerate.getDefaultState());
            }
        }

        return true;
    }

    @Override
    public DecorateBiomeEvent.Decorate.EventType getEventType() {
        return DecorateBiomeEvent.Decorate.EventType.PUMPKIN;
    }
}
