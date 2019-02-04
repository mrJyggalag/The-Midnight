package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.entity.creature.EntityStinger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStingerEgg extends BlockPileOfEggs {
    public BlockStingerEgg() {
        super();
    }

    @Override
    protected EntityLiving createEntityForEgg(World world, BlockPos pos, IBlockState state) {
        EntityStinger stinger = new EntityStinger(world);
        stinger.setGrowingAge(-1200);
        return stinger;
    }
}
