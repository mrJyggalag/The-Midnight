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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;
import java.util.function.Supplier;

public class UnstableBushBloomedBlock extends MidnightPlantBlock implements IGrowable {
    public static final BooleanProperty HAS_FRUIT = BooleanProperty.create("has_fruit");
    private final Supplier<Item> fruitSupplier;
    protected static final AxisAlignedBB BOUND = new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 1d);

    public UnstableBushBloomedBlock(Supplier<Item> fruitSupplier) {
        super(PlantBehaviorType.FLOWER, true);
        this.fruitSupplier = fruitSupplier;
        this.setDefaultState(this.stateContainer.getBaseState().with(HAS_FRUIT, false));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!state.get(HAS_FRUIT)) {
            return false;
        }

        if (!world.isRemote) {
            int fruitCount = world.rand.nextInt(3) + 1;
            for (int i = 0; i < fruitCount; i++) {
                ItemEntity item = new ItemEntity(world, pos.getX() + world.rand.nextFloat(), pos.getY(), pos.getZ() + world.rand.nextFloat(), new ItemStack(this.fruitSupplier.get()));
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
    @SuppressWarnings("deprecation")
    public boolean canSilkHarvest() {
        return false;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return this.fruitSupplier.get();
    }

    @Override
    public int quantityDropped(BlockState state, int fortune, Random random) {
        return random.nextInt(3) + (state.get(HAS_FRUIT) ? 3 : 0);
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, BlockState state, boolean isClient) {
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
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        if (!this.canBlockStay(world, pos, state)) {
            world.destroyBlock(pos, true);
        } else {
            if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(10) == 0)) {
                this.grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
            }
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

    @Override
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockAccess world, BlockPos pos) {
        return BOUND;
    }
}
