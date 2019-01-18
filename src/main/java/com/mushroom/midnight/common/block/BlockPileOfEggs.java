package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.IModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings({ "WeakerAccess", "deprecation" })
public abstract class BlockPileOfEggs extends Block implements IModelProvider {
    protected static final AxisAlignedBB bound_one_egg = new AxisAlignedBB(0.1875d, 0d, 0.1875d, 0.75d, 0.4375d, 0.75d);
    protected static final AxisAlignedBB bound_several_eggs = new AxisAlignedBB(0.0625d, 0d, 0.0625d, 0.9375d, 0.4375d, 0.9375d);
    protected static final PropertyInteger EGGS = PropertyInteger.create("eggs", 1, 4);

    protected BlockPileOfEggs() {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(EGGS, 1));
        setCreativeTab(Midnight.DECORATION_TAB);
    }

    protected abstract EntityLiving createEntityForEgg(World world, BlockPos pos, IBlockState state);

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (canTrample(entity)) {
            onTrample(world, pos, entity, 0.1f);
        }
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        if (canTrample(entity)) {
            onTrample(world, pos, entity, 1f);
        }
        super.onFallenUpon(world, pos, entity, fallDistance);
    }

    protected boolean canTrample(Entity entity) {
        return true;
    }

    protected void onTrample(World world, BlockPos pos, Entity entity, float chance) {
        IBlockState state = world.getBlockState(pos);
        if (!world.isRemote && canTrample(entity) && (chance >= 1f || world.rand.nextFloat() <= chance)) {
            breakEggs(world, pos, state);
        }
    }

    protected void breakEggs(World world, BlockPos pos, IBlockState state) {
        world.playSound(null, pos, SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.rand.nextFloat() * 0.2F);
        int eggs = state.getValue(EGGS);
        if (eggs <= 1) {
            world.destroyBlock(pos, false);
        } else {
            world.setBlockState(pos, state.withProperty(EGGS, --eggs), 2);
            world.playEvent(2001, pos, Block.getStateId(state));
        }
        EntityLiving creature;
        try {
            creature = createEntityForEgg(world, pos, state);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        creature.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), world.rand.nextFloat() * 360f, 0f);
        world.spawnEntity(creature);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, tile, stack);
        breakEggs(world, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(EGGS, world.rand.nextInt(4) + 1);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getBoundingBox(state, world, pos);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return (state.getValue(EGGS) > 1 ? bound_several_eggs : bound_one_egg);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, EGGS);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(EGGS, meta + 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(EGGS) - 1;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
