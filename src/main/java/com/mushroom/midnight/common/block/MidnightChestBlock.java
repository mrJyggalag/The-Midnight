package com.mushroom.midnight.common.block;

import net.minecraft.block.ChestBlock;

// TODO make it when able to launch midnight
public class MidnightChestBlock extends ChestBlock {

    protected MidnightChestBlock(Properties properties) {
        super(properties);
    }
    /*public enum ChestModel {
        SHADOWROOT("shadowroot");
        private String name;
        ChestModel(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public static ChestModel getDefault() {
            return ChestModel.SHADOWROOT;
        }
    }
    private final ChestModel chestModel;

    public MidnightChestBlock(Properties properties, ChestModel chestModel) {
        super(properties.hardnessAndResistance(2.5f, 0f).sound(SoundType.WOOD)); //, Type.BASIC
        this.chestModel = chestModel;
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new TileEntityMidnightChest(chestModel);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        if (!blockState.canProvidePower()) {
            return 0;
        } else {
            int i = 0;
            TileEntity tileentity = blockAccess.getTileEntity(pos);
            if (tileentity instanceof TileEntityMidnightChest) {
                i = ((TileEntityMidnightChest) tileentity).numPlayersUsing;
            }
            return MathHelper.clamp(i, 0, 15);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        Direction direction = Direction.byHorizontalIndex(MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
        state = state.with(FACING, direction);
        BlockPos blockpos = pos.north();
        BlockPos blockpos1 = pos.south();
        BlockPos blockpos2 = pos.west();
        BlockPos blockpos3 = pos.east();
        boolean flag = this == worldIn.getBlockState(blockpos).getBlock();
        boolean flag1 = this == worldIn.getBlockState(blockpos1).getBlock();
        boolean flag2 = this == worldIn.getBlockState(blockpos2).getBlock();
        boolean flag3 = this == worldIn.getBlockState(blockpos3).getBlock();
        if (!flag && !flag1 && !flag2 && !flag3) {
            worldIn.setBlockState(pos, state, 3);
        } else if (direction.getAxis() != Direction.Axis.X || !flag && !flag1) {
            if (direction.getAxis() == Direction.Axis.Z && (flag2 || flag3)) {
                if (flag2) {
                    worldIn.setBlockState(blockpos2, state, 3);
                } else {
                    worldIn.setBlockState(blockpos3, state, 3);
                }

                worldIn.setBlockState(pos, state, 3);
            }
        } else {
            if (flag) {
                worldIn.setBlockState(blockpos, state, 3);
            } else {
                worldIn.setBlockState(blockpos1, state, 3);
            }
            worldIn.setBlockState(pos, state, 3);
        }
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityMidnightChest) {
                ((TileEntityMidnightChest) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, p_220069_6_);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityMidnightChest) {
            tileentity.updateContainingBlockInfo();
        }
    }

    @Nullable
    public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (!(tileentity instanceof TileEntityMidnightChest)) {
            return null;
        } else {
            ILockableContainer ilockablecontainer = (TileEntityMidnightChest) tileentity;
            if (!allowBlocking && this.isBlocked(worldIn, pos)) {
                return null;
            } else {
                for (Direction Direction : Direction.Plane.HORIZONTAL) {
                    BlockPos blockpos = pos.offset(Direction);
                    Block block = worldIn.getBlockState(blockpos).getBlock();

                    if (block == this) {
                        if (!allowBlocking && this.isBlocked(worldIn, blockpos)) { // Forge: fix MC-99321
                            return null;
                        }
                        TileEntity tileentity1 = worldIn.getTileEntity(blockpos);
                        if (tileentity1 instanceof TileEntityMidnightChest) {
                            if (Direction != Direction.WEST && Direction != Direction.NORTH) {
                                ilockablecontainer = new InventoryLargeChest("tile.midnight." + this.chestModel.getName() + "_double_chest.name", ilockablecontainer, (TileEntityMidnightChest) tileentity1);
                            } else {
                                ilockablecontainer = new InventoryLargeChest("tile.midnight." + this.chestModel.getName() + "_double_chest.name", (TileEntityMidnightChest) tileentity1, ilockablecontainer);
                            }
                        }
                    }
                }
                return ilockablecontainer;
            }
        }
    }

    private boolean isBlocked(World worldIn, BlockPos pos) {
        return this.isBelowSolidBlock(worldIn, pos);
    }

    private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), Direction.DOWN);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }*/
}
