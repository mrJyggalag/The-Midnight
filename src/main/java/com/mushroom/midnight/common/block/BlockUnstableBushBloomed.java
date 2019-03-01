package com.mushroom.midnight.common.block;

import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.item.ItemUnstableFruit;
import com.mushroom.midnight.common.registry.ModBlocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class BlockUnstableBushBloomed extends BlockMidnightPlant implements IGrowable {
    public static final PropertyBool HAS_FRUIT = PropertyBool.create("has_fruit");
    private final Supplier<Item> fruitSupplier;

    public BlockUnstableBushBloomed(Supplier<Item> fruitSupplier) {
        super(PlantBehaviorType.FLOWER, true);
        this.fruitSupplier = fruitSupplier;
        setDefaultState(blockState.getBaseState().withProperty(HAS_FRUIT, false));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(HAS_FRUIT)) {
            if (!world.isRemote) {
                int fruitCount = world.rand.nextInt(3) + 1;
                for (int i = 0; i < fruitCount; i++) {
                    EntityItem entityitem = new EntityItem(world, (double) (pos.getX() + world.rand.nextFloat()), pos.getY(), (double) (pos.getZ() + world.rand.nextFloat()), new ItemStack(fruitSupplier.get()));
                    world.spawnEntity(entityitem);
                    entityitem.move(MoverType.SELF, (double) (world.rand.nextFloat() * 0.12f - 0.06f), -0.06f, (double) (world.rand.nextFloat() * 0.12f - 0.06f));
                }
                world.setBlockState(pos, ModBlocks.UNSTABLE_BUSH.getDefaultState().withProperty(BlockUnstableBush.STAGE, 3), 2);
            }
            return true;
        }
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HAS_FRUIT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(HAS_FRUIT, (meta & 1) == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HAS_FRUIT) ? 1 : 0;
    }

    @Override
    public boolean canSilkHarvest() {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return fruitSupplier.get();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return random.nextInt(3) + (state.getValue(HAS_FRUIT) ? 3 : 0);
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return !state.getValue(HAS_FRUIT);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        return !state.getValue(HAS_FRUIT);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state.withProperty(HAS_FRUIT, true), 2);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!canBlockStay(world, pos, state)) {
            world.destroyBlock(pos, true);
        } else {
            if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(10) == 0)) {
                grow(world, rand, pos, state);
                ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
            }
        }
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModBlocks.UNSTABLE_BUSH);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        int fruitType = this.fruitSupplier.get() instanceof ItemUnstableFruit ? ((ItemUnstableFruit) this.fruitSupplier.get()).fruitColor.ordinal() : 0;
        if (rand.nextInt(10) == 0) {
            MidnightParticles.UNSTABLE_BUSH.spawn(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, rand.nextFloat() * 0.1d - 0.05d, rand.nextFloat() * 0.03d, rand.nextFloat() * 0.1d - 0.05d, fruitType);
        }
    }
}
