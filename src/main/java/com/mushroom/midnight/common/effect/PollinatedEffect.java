package com.mushroom.midnight.common.effect;

import com.mushroom.midnight.common.helper.Helper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Collection;

public class PollinatedEffect extends GenericEffect {
    public PollinatedEffect() {
        super(false, 0);
        setBeneficial();
        // TODO provide an effect of glowing red trail
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        World world = entity.world;
        if (!world.isRemote) {
            BlockPos pos = entity.getPosition().add(world.rand.nextInt(3) - 1, world.rand.nextInt(3) - 1, world.rand.nextInt(3) - 1);
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock().isReplaceable(world, pos) && Helper.isGroundForBoneMeal(world.getBlockState(pos.down()).getBlock())) {
                final Collection<Biome> biomes = ForgeRegistries.BIOMES.getValuesCollection();
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
