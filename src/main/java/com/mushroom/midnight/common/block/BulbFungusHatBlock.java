package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.Random;

public class BulbFungusHatBlock extends Block {

    public BulbFungusHatBlock(MapColor mapColor) {
        super(Material.WOOD, mapColor);
        setHardness(0.2f);
        setSoundType(SoundType.SLIME);
        setCreativeTab(MidnightItemGroups.BUILDING);
        setLightLevel(0.1f);
    }

    @Override
    protected ItemStack getSilkTouchDrop(BlockState state) {
        return new ItemStack(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(5) == 0 ? 1 : 0;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MidnightBlocks.BULB_FUNGUS);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public int getPackedLightmapCoords(BlockState state, IBlockAccess source, BlockPos pos) {
        return source.getCombinedLight(pos, 13);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state) {
        return 0f;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockAccess world, BlockPos pos, MobEntity.SpawnPlacementType placementType) {
        return false;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSneaking()) {
            super.onFallenUpon(world, pos, entity, fallDistance);
        } else {
            entity.fall(fallDistance, 0f);
        }
    }

    @Override
    public void onLanded(World world, Entity entity) {
        if (entity.isSneaking()) {
            super.onLanded(world, entity);
        } else if (entity.motionY < 0d) {
            entity.motionY = -entity.motionY;
            if (!(entity instanceof LivingEntity)) {
                entity.motionY *= 0.8d;
            }
        }
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (Math.abs(entity.motionY) < 0.1d && !entity.isSneaking()) {
            double d0 = 0.4d + Math.abs(entity.motionY) * 0.2d;
            entity.motionX *= d0;
            entity.motionZ *= d0;
        }
        super.onEntityWalk(world, pos, entity);
    }
}
