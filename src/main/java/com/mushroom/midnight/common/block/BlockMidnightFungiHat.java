package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BlockMidnightFungiHat extends Block implements IModelProvider {
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    private final Supplier<Block> saplingSupplier;

    public BlockMidnightFungiHat(Supplier<Block> saplingSupplier) {
        super(Material.WOOD);
        this.saplingSupplier = saplingSupplier;
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(Midnight.BUILDING_TAB);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(UP, false)
                .withProperty(DOWN, false)
                .withProperty(NORTH, false)
                .withProperty(EAST, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false)
        );
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(5) == 0 ? 1 : 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this.saplingSupplier.get());
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(UP, this.isInside(world, pos.up()))
                .withProperty(DOWN, this.isInside(world, pos.down()))
                .withProperty(NORTH, this.isInside(world, pos.north()))
                .withProperty(EAST, this.isInside(world, pos.east()))
                .withProperty(SOUTH, this.isInside(world, pos.south()))
                .withProperty(WEST, this.isInside(world, pos.west()));
    }

    private boolean isInside(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == ModBlocks.MUSHROOM_INSIDE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    public static List<EnumFacing> getOuterSides(IBlockState state) {
        List<EnumFacing> outerSides = new ArrayList<>();
        if (!state.getValue(UP)) {
            outerSides.add(EnumFacing.UP);
        }
        if (!state.getValue(DOWN)) {
            outerSides.add(EnumFacing.DOWN);
        }
        if (!state.getValue(NORTH)) {
            outerSides.add(EnumFacing.NORTH);
        }
        if (!state.getValue(EAST)) {
            outerSides.add(EnumFacing.EAST);
        }
        if (!state.getValue(SOUTH)) {
            outerSides.add(EnumFacing.SOUTH);
        }
        if (!state.getValue(WEST)) {
            outerSides.add(EnumFacing.WEST);
        }
        return outerSides;
    }

    @Override
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return 0.8F;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType placementType) {
        return false;
    }
}
