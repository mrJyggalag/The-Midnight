package com.mushroom.midnight.common.effect;

import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;

public class PollinatedEffect extends GenericEffect {
    public PollinatedEffect() {
        super(EffectType.BENEFICIAL, 0);
        // TODO provide an effect of glowing red trail
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        World world = entity.world;
        if (!world.isRemote) {
            BlockPos pos = entity.getPosition().add(world.rand.nextInt(3) - 1, world.rand.nextInt(3) - 1, world.rand.nextInt(3) - 1);
            BlockState state = world.getBlockState(pos);
            if (state.getBlock().isReplaceable(world, pos) && Helper.isGroundForBoneMeal(world.getBlockState(pos.down()).getBlock())) {
                final Collection<Biome> biomes = ForgeRegistries.BIOMES.getValues();
                biomes.stream().skip((int) (biomes.size() * Math.random())).findFirst().ifPresent(b -> {
                    b.plantFlower(world, world.rand, pos);
                    world.playEvent(2005, pos, 0);
                });
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}
