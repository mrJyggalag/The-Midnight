package com.mushroom.midnight.common.world.feature;

import com.mushroom.midnight.Midnight;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import java.util.Random;

public class MelonLikeFeature extends MidnightAbstractFeature {
    private final Block togenerate;
    private final Block placeon;
    private final int chanceon;


    public MelonLikeFeature(Block blocktogenerate, Block blocktoplaceon, int numberofchance) {
        this.togenerate = blocktogenerate;
        this.placeon = blocktoplaceon;
        this.chanceon = numberofchance;
    }

    @Override
    public boolean placeFeature(World world, Random random, BlockPos origin) {
        int chance = Math.round(random.nextInt(this.chanceon));
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
