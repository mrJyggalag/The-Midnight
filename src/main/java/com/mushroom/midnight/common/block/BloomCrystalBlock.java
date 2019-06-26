package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.entity.creature.CrystalBugEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MapColor;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BloomCrystalBlock extends CrystalBlock {
    public BloomCrystalBlock() {
        super(MapColor.PINK);
        setLightLevel(1f);
    }

    @Override
    public void updateTick(World world, BlockPos pos, BlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        if (rand.nextFloat() < 0.005f && world.getEntitiesWithinAABB(CrystalBugEntity.class, new AxisAlignedBB(pos).grow(6d, 4d, 6d)).size() == 0) {
            CrystalBugEntity crystal_bug;
            try {
                crystal_bug = new CrystalBugEntity(world);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            crystal_bug.setPositionAndRotation(pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, world.rand.nextFloat() * 360f, 0f);
            world.addEntity(crystal_bug);
        }
    }
}
