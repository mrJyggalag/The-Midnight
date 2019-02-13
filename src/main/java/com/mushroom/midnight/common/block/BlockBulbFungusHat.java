package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBulbFungusHat extends Block implements IModelProvider {

    public BlockBulbFungusHat(MapColor mapColor) {
        super(Material.WOOD, mapColor);
        setHardness(0.2f);
        setSoundType(SoundType.SLIME);
        setCreativeTab(ModTabs.BUILDING_TAB);
        setLightLevel(0.1f);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.BULB_FUNGUS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        int skyLight = 14;
        int blockLight = 14;
        return skyLight << 20 | blockLight << 4;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return 0f;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType placementType) {
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
            if (!(entity instanceof EntityLivingBase)) {
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
