package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.common.entity.creature.EntityCrystalBug;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStingerEgg extends BlockPileOfEggs {
    public BlockStingerEgg() {
        super();
        setDefaultState(blockState.getBaseState().withProperty(EGGS, 1));
        setCreativeTab(Midnight.DECORATION_TAB);
    }

    @Override
    protected EntityLiving createEntityForEgg(World world, BlockPos pos, IBlockState state) {
        // TODO create entity stinger
        return new EntityCrystalBug(world);
    }
}
