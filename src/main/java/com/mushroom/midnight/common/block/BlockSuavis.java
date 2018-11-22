package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSuavis extends Block {

    public BlockSuavis(Material material, MapColor color) {
        super(Material.GOURD, MapColor.LIGHT_BLUE);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.RAW_SUAVIS;
    }

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);

        EntityAreaEffectCloud entity = new EntityAreaEffectCloud(world, pos.getX(), pos.getY(), pos.getZ());
        entity.setRadius(3.0F);
        entity.setRadiusOnUse(-0.5F);
        entity.setWaitTime(10);
        entity.setRadiusPerTick(-entity.getRadius() / (float) entity.getDuration());
        entity.setPotion(PotionTypes.EMPTY);
        entity.addEffect(new PotionEffect(MobEffects.NAUSEA, 20 * 30, 0, false, true));
        setLightLevel(15);

        world.spawnEntity(entity);
    }
}
