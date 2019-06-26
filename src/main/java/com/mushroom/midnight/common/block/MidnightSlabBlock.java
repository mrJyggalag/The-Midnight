package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class MidnightSlabBlock extends BlockSlab {
    private final Supplier<BlockState> parentSupplier;

    public MidnightSlabBlock(Supplier<BlockState> parentSupplier) {
        super(Material.ROCK);
        this.parentSupplier = parentSupplier;
        this.setCreativeTab(MidnightItemGroups.BUILDING);
        this.setDefaultState(this.buildDefaultState(this.blockState.getBaseState()));
    }

    protected BlockState buildDefaultState(BlockState baseState) {
        return basestate.with(HALF, EnumBlockHalf.BOTTOM);
    }

    @Override
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int damageDropped(BlockState state) {
        return 0;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.get(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
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
    public MapColor getMapColor(BlockState state, IBlockAccess world, BlockPos pos) {
        return this.parentSupplier.get().getMapColor(world, pos);
    }

    BlockState getParentstate() {
        return parentSupplier.get();
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
