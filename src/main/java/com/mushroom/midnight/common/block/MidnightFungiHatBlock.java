package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MidnightFungiHatBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    private final Supplier<Block> saplingSupplier;
    private final Supplier<Item> powderSupplier;

    public MidnightFungiHatBlock(Supplier<Block> saplingSupplier, Supplier<Item> powderSupplier, MapColor mapColor) {
        super(Material.WOOD, mapColor);
        this.saplingSupplier = saplingSupplier;
        this.powderSupplier = powderSupplier;
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MidnightItemGroups.BUILDING);
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
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return rand.nextBoolean() ? Item.getItemFromBlock(this.saplingSupplier.get()) : powderSupplier.get();
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getActualState(BlockState state, IBlockAccess world, BlockPos pos) {
        return state.with(UP, this.isInside(world, pos.up()))
                .withProperty(DOWN, this.isInside(world, pos.down()))
                .withProperty(NORTH, this.isInside(world, pos.north()))
                .withProperty(EAST, this.isInside(world, pos.east()))
                .withProperty(SOUTH, this.isInside(world, pos.south()))
                .withProperty(WEST, this.isInside(world, pos.west()));
    }

    private boolean isInside(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == MidnightBlocks.MUSHROOM_INSIDE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    public static List<Direction> getOuterSides(BlockState state) {
        List<Direction> outerSides = new ArrayList<>();
        if (!state.get(UP)) {
            outerSides.add(Direction.UP);
        }
        if (!state.get(DOWN)) {
            outerSides.add(Direction.DOWN);
        }
        if (!state.get(NORTH)) {
            outerSides.add(Direction.NORTH);
        }
        if (!state.get(EAST)) {
            outerSides.add(Direction.EAST);
        }
        if (!state.get(SOUTH)) {
            outerSides.add(Direction.SOUTH);
        }
        if (!state.get(WEST)) {
            outerSides.add(Direction.WEST);
        }
        return outerSides;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getAmbientOcclusionLightValue(BlockState state) {
        return 0.8F;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockAccess world, BlockPos pos, MobEntity.SpawnPlacementType placementType) {
        return false;
    }
}
