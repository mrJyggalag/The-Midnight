package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.entity.creature.EntityCrystalBug;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBloomCrystal extends BlockCrystal {
    public BlockBloomCrystal() {
        super();
        setLightLevel(1f);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (rand.nextFloat() < 0.005f && world.getEntitiesWithinAABB(EntityCrystalBug.class, new AxisAlignedBB(pos).grow(6d, 4d, 6d)).size() == 0) {
            EntityCrystalBug crystal_bug;
            try {
                crystal_bug = new EntityCrystalBug(world);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            crystal_bug.setPositionAndRotation(pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, world.rand.nextFloat() * 360f, 0f);
            world.spawnEntity(crystal_bug);
        }
    }
}
