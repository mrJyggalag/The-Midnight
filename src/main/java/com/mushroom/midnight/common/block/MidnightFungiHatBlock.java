package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
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

    public MidnightFungiHatBlock(Supplier<Block> saplingSupplier, Supplier<Item> powderSupplier, MaterialColor materialColor) {
        super(Block.Properties.create(Material.WOOD, materialColor).sound(SoundType.WOOD).hardnessAndResistance(0.5f, 0f));
        this.saplingSupplier = saplingSupplier;
        this.powderSupplier = powderSupplier;
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
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
                .with(DOWN, this.isInside(world, pos.down()))
                .with(NORTH, this.isInside(world, pos.north()))
                .with(EAST, this.isInside(world, pos.east()))
                .with(SOUTH, this.isInside(world, pos.south()))
                .with(WEST, this.isInside(world, pos.west()));
    }

    private boolean isInside(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == MidnightBlocks.MUSHROOM_INSIDE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
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
    public float getAmbientOcclusionLightValue(BlockState state) {
        return 0.8F;
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, IBlockReader world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, @Nullable EntityType<?> entityType) {
        return false;
    }
}
