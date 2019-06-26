package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class MidnightStairsBlock extends StairsBlock {
    private final Supplier<BlockState> parentSupplier;

    public MidnightStairsBlock(Supplier<BlockState> parentSupplier) {
        super(Blocks.AIR.getDefaultState());
        this.parentSupplier = parentSupplier;
        this.setCreativeTab(MidnightItemGroups.BUILDING);
    }

    @Override
    public Material getMaterial(BlockState state) {
        return this.parentSupplier.get().getMaterial();
    }

    @Override
    public float getBlockHardness(BlockState state, World world, BlockPos pos) {
        return this.parentSupplier.get().getBlockHardness(world, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        BlockState parentState = this.parentSupplier.get();
        return parentState.getBlock().getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public SoundType getSoundType(BlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        BlockState parentState = this.parentSupplier.get();
        return parentState.getBlock().getSoundType(parentState, world, pos, entity);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        BlockState parentState = this.parentSupplier.get();
        parentState.getBlock().randomDisplayTick(parentState, world, pos, rand);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getPackedLightmapCoords(BlockState state, IBlockAccess source, BlockPos pos) {
        return this.parentSupplier.get().getPackedLightmapCoords(source, pos);
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        BlockState parentState = this.parentSupplier.get();
        return parentState.getBlock().canRenderInLayer(parentState, layer);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.parentSupplier.get().getBlock().getRenderLayer();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(BlockState state, World world, BlockPos pos) {
        return this.parentSupplier.get().getSelectedBoundingBox(world, pos);
    }

    @Override
    public MapColor getMapColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return this.parentSupplier.get().getMapColor(world, pos);
    }

    @Override
    public boolean canCollideCheck(BlockState state, boolean hitIfLiquid) {
        return parentSupplier.get().getBlock().canCollideCheck(state, hitIfLiquid);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockAccess world, BlockPos pos, Direction direction, IPlantable plantable) {
        return parentSupplier.get().getBlock().canSustainPlant(state, world, pos, direction, plantable);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, Direction face) {
        return parentSupplier.get().getBlock().getFireSpreadSpeed(world, pos, face);
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, Direction face) {
        return parentSupplier.get().getBlock().getFlammability(world, pos, face);
    }
}
