package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightEffects;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TendrilweedBlock extends MidnightPlantBlock {
    // TODO particle  tendril spore
    public TendrilweedBlock() {
        super(true);
        setTickRandomly(true);
    }

    @Override
    public int tickRate(World world) {
        return 10;
    }

    @Override
    public void randomTick(World world, BlockPos pos, BlockState state, Random random) {
        super.randomTick(world, pos, state, random);
        if (!world.isRemote && random.nextFloat() < 0.01f) {
            BlockPos placePos = pos.add(random.nextInt(5) - 2, random.nextInt(3) - 1, random.nextInt(5) - 2);
            if (MidnightBlocks.TENDRILWEED.canPlaceBlockAt(world, placePos)) {
                int flowers = 0;
                for (BlockPos currentPos : BlockPos.getAllInBox(pos.add(-2, -1, -2), pos.add(2, 1, 2))) {
                    if (world.getBlockState(currentPos).getBlock() == MidnightBlocks.TENDRILWEED) {
                        flowers++;
                    }
                }
                if (flowers < 6) {
                    world.setBlockState(placePos, MidnightBlocks.TENDRILWEED.getDefaultState(), 3);
                }
            }
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity && !entity.world.isRemote && entity.ticksExisted % 20 == 0) {
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(MidnightEffects.POLLINATED, 200, 1, false, true));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canSustainBush(world.getBlockState(pos.down())) && super.canPlaceBlockAt(world, pos);
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return state.getBlock() == MidnightBlocks.NIGHTSTONE;
    }
}
