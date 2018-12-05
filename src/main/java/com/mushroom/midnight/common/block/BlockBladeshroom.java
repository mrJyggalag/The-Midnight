package com.mushroom.midnight.common.block;

import com.mushroom.midnight.common.registry.ModBlocks;
import com.mushroom.midnight.common.registry.ModItems;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBladeshroom extends BlockMidnightPlant {
    private static final PropertyBool HAS_CAP = PropertyBool.create("has_cap");
    private static final int REGROW_CHANCE = 10;

    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.5625, 0.9375);
    private static final AxisAlignedBB CAPLESS_BOUNDS = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);

    public BlockBladeshroom() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(HAS_CAP, false));
        this.setTickRandomly(true);
        this.setCreativeTab(null);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getBlock() == ModBlocks.NIGHTSTONE;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(HAS_CAP)) {
            player.addItemStackToInventory(new ItemStack(ModItems.BLADESHROOM_CAP));
            world.setBlockState(pos, state.withProperty(HAS_CAP, false));
            player.attackEntityFrom(DamageSource.CACTUS, 1.0F);

            return true;
        }

        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(HAS_CAP)) {
            drops.add(new ItemStack(ModItems.BLADESHROOM_CAP));
        }

        Random random = world instanceof World ? ((World) world).rand : RANDOM;
        drops.add(new ItemStack(ModItems.BLADESHROOM_SPORES, this.quantityDropped(state, fortune, random)));
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int quantity = 0;
        if (state.getValue(HAS_CAP)) {
            quantity += 1;
        }
        if (random.nextInt(3) == 0) {
            quantity += 1;
        }
        return quantity;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(HAS_CAP) ? BOUNDS : CAPLESS_BOUNDS;
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.NONE;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (state.getValue(HAS_CAP)) {
            entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (random.nextInt(REGROW_CHANCE) == 0) {
            world.setBlockState(pos, state.withProperty(HAS_CAP, true));
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HAS_CAP);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HAS_CAP) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HAS_CAP, meta != 0);
    }
}
