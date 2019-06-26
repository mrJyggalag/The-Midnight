package com.mushroom.midnight.common.block;

import com.mushroom.midnight.Midnight;
import com.mushroom.midnight.client.particle.MidnightParticles;
import com.mushroom.midnight.common.network.GuiHandler;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItemGroups;
import com.mushroom.midnight.common.tile.base.TileEntityMidnightFurnace;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.Random;

public class MidnightFurnaceBlock extends BlockContainer {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private final boolean isBurning;
    private static boolean keepInventory;

    public MidnightFurnaceBlock(boolean isBurning) {
        super(Material.ROCK);
        this.isBurning = isBurning;

        this.setHardness(3.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, Direction.NORTH));
        this.setCreativeTab(MidnightItemGroups.DECORATION);

        if (isBurning) {
            this.setLightLevel(0.4F);
        }
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MidnightBlocks.MIDNIGHT_FURNACE);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, BlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, BlockState state) {
        if (!worldIn.isRemote) {
            BlockState BlockState = worldIn.getBlockState(pos.north());
            BlockState BlockState1 = worldIn.getBlockState(pos.south());
            BlockState BlockState2 = worldIn.getBlockState(pos.west());
            BlockState BlockState3 = worldIn.getBlockState(pos.east());
            Direction Direction = state.get(FACING);

            if (Direction == Direction.NORTH && BlockState.isFullBlock() && !BlockState1.isFullBlock()) {
                Direction = Direction.SOUTH;
            } else if (Direction == Direction.SOUTH && BlockState1.isFullBlock() && !BlockState.isFullBlock()) {
                Direction = Direction.NORTH;
            } else if (Direction == Direction.WEST && BlockState2.isFullBlock() && !BlockState3.isFullBlock()) {
                Direction = Direction.EAST;
            } else if (Direction == Direction.EAST && BlockState3.isFullBlock() && !BlockState2.isFullBlock()) {
                Direction = Direction.WEST;
            }

            worldIn.setBlockState(pos, state.with(FACING, Direction), 2);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("incomplete-switch")
    @Override
    public void randomDisplayTick(BlockState state, World worldIn, BlockPos pos, Random rand) {
        if (this.isBurning) {
            Direction facing = state.get(FACING);
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double z = pos.getZ() + 0.5D;
            double outwardOffset = 0.52D;
            double sidewardOffset = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (facing) {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x - outwardOffset, y, z + sidewardOffset, 0.0D, 0.0D, 0.0D);
                    MidnightParticles.FURNACE_FLAME.spawn(worldIn, x - outwardOffset, y, z + sidewardOffset, 0d, 0d, 0d);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + outwardOffset, y, z + sidewardOffset, 0.0D, 0.0D, 0.0D);
                    MidnightParticles.FURNACE_FLAME.spawn(worldIn, x + outwardOffset, y, z + sidewardOffset, 0d, 0d, 0d);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + sidewardOffset, y, z - outwardOffset, 0.0D, 0.0D, 0.0D);
                    MidnightParticles.FURNACE_FLAME.spawn(worldIn, x + sidewardOffset, y, z - outwardOffset, 0d, 0d, 0d);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + sidewardOffset, y, z + outwardOffset, 0.0D, 0.0D, 0.0D);
                    MidnightParticles.FURNACE_FLAME.spawn(worldIn, x + sidewardOffset, y, z + outwardOffset, 0d, 0d, 0d);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity playerIn, EnumHand hand, Direction facing, float hitX, float hitY, float hitZ) {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntityMidnightFurnace) {
            playerIn.openGui(Midnight.instance, GuiHandler.MIDNIGHT_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
            playerIn.addStat(Stats.FURNACE_INTERACTION);
        }
        return true;
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        BlockState BlockState = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active) {
            worldIn.setBlockState(pos, MidnightBlocks.MIDNIGHT_FURNACE_LIT.getDefaultState().withProperty(FACING, Blockstate.get(FACING)), 3);
            worldIn.setBlockState(pos, MidnightBlocks.MIDNIGHT_FURNACE_LIT.getDefaultState().withProperty(FACING, Blockstate.get(FACING)), 3);
        } else {
            worldIn.setBlockState(pos, MidnightBlocks.MIDNIGHT_FURNACE.getDefaultState().withProperty(FACING, Blockstate.get(FACING)), 3);
            worldIn.setBlockState(pos, MidnightBlocks.MIDNIGHT_FURNACE.getDefaultState().withProperty(FACING, Blockstate.get(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMidnightFurnace();
    }

    @Override
    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMidnightFurnace) {
                ((TileEntityMidnightFurnace) tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, BlockState state) {
        if (!keepInventory) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMidnightFurnace) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMidnightFurnace) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(MidnightBlocks.MIDNIGHT_FURNACE);
    }

    @Override
    public EnumBlockRenderType getRenderType(BlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        Direction facing = Direction.byIndex(meta);
        if (facing.getAxis() == Direction.Axis.Y) {
            facing = Direction.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return (state.get(FACING)).getIndex();
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || (layer == BlockRenderLayer.CUTOUT && this.isBurning);
    }

    @Override
    public int getPackedLightmapCoords(BlockState state, IBlockAccess source, BlockPos pos) {
        if (this.isBurning && MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
            return source.getCombinedLight(pos, 15);
        }
        return source.getCombinedLight(pos, 0);
    }
}

