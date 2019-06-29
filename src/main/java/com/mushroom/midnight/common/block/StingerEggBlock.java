package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.entity.creature.StingerEntity;
import com.mushroom.midnight.common.registry.MidnightEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StingerEggBlock extends PileOfEggsBlock {
    public StingerEggBlock() {
        super();
    }

    @Override
    protected MobEntity createEntityForEgg(World world, BlockPos pos, BlockState state) {
        StingerEntity stinger = MidnightEntities.STINGER.create(world);
        if (stinger != null) {
            stinger.setGrowingAge(-1);
        }
        return stinger;
    }
}
