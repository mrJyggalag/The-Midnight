package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.item.UnstableFruitItem;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class UnstableBushBloomedBlock extends MidnightPlantBlock implements IGrowable {
    public static final BooleanProperty HAS_FRUIT = BooleanProperty.create("has_fruit");
    private final Supplier<Item> fruitSupplier;

    public UnstableBushBloomedBlock(Block.Properties properties, Supplier<Item> fruitSupplier) {
        super(properties, false);
        this.fruitSupplier = fruitSupplier;
        setDefaultState(this.stateContainer.getBaseState().with(HAS_FRUIT, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!state.get(HAS_FRUIT)) {
            return false;
        }

        if (!world.isRemote && world instanceof ServerWorld) {
            List<ItemStack> drops = getDrops(state, (ServerWorld) world, pos, null, player, player.getHeldItem(hand));
            if (drops.isEmpty()) {
                return false;
            }

            int fruitCount = world.rand.nextInt(3) + 1;
            for (int i = 0; i < fruitCount; i++) {
                ItemStack stack = drops.get(world.rand.nextInt(drops.size()));
                ItemEntity item = new ItemEntity(world, pos.getX() + world.rand.nextFloat(), pos.getY(), pos.getZ() + world.rand.nextFloat(), stack);
                world.addEntity(item);
                item.move(MoverType.SELF, new Vec3d(world.rand.nextFloat() * 0.12f - 0.06f, -0.06f, world.rand.nextFloat() * 0.12f - 0.06f));
            }

            world.setBlockState(pos, MidnightBlocks.UNSTABLE_BUSH.getDefaultState().with(UnstableBushBlock.STAGE, UnstableBushBlock.MAX_STAGE), 2);
        }

        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HAS_FRUIT);
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(HAS_FRUIT);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return !state.get(HAS_FRUIT);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(HAS_FRUIT, true), 2);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(10) == 0)) {
            this.grow(world, rand, pos, state);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(this.fruitSupplier.get());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        int fruitType = this.fruitSupplier.get() instanceof UnstableFruitItem ? ((UnstableFruitItem) this.fruitSupplier.get()).color.ordinal() : 0;
        if (rand.nextInt(10) == 0) {
            MidnightParticles.UNSTABLE_BUSH.spawn(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, rand.nextFloat() * 0.1d - 0.05d, rand.nextFloat() * 0.03d, rand.nextFloat() * 0.1d - 0.05d, fruitType);
        }
    }

    /*@Override
    public int quantityDropped(BlockState state, int fortune, Random random) { // json drop or getDrops()
        return random.nextInt(3) + (state.get(HAS_FRUIT) ? 3 : 0);
    }*/
}
