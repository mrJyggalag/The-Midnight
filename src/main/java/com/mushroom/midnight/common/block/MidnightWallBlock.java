package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class MidnightWallBlock extends BlockWall {
    private final Supplier<BlockState> parentSupplier;

    public MidnightWallBlock(Supplier<BlockState> parentSupplier) {
        super(Blocks.AIR);
        this.parentSupplier = parentSupplier;
        this.setCreativeTab(MidnightItemGroups.DECORATION);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(UP, false)
                .withProperty(NORTH, false)
                .withProperty(EAST, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false)
        );
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

    @Override
    public int damageDropped(BlockState state) {
        return 0;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }
}
