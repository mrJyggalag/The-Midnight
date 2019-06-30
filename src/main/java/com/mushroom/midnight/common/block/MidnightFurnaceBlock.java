package com.mushroom.midnight.common.block;

import net.minecraft.block.FurnaceBlock;

// TODO make it when able to launch midnight
public class MidnightFurnaceBlock extends FurnaceBlock {
    protected MidnightFurnaceBlock(Properties builder) {
        super(builder);
    }

    /*public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private final boolean isBurning;
    private static boolean keepInventory;

    public MidnightFurnaceBlock(Properties props) {
        super(props.hardnessAndResistance(3.5f));
        this.isBurning = isBurning;

        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
        //setCreativeTab(MidnightItemGroups.DECORATION);

        if (isBurning) {
            this.setLightLevel(0.4F);
        }
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(MidnightBlocks.NIGHTSTONE_FURNACE);
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
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double x = (double)pos.getX() + 0.5d;
            double y = (double)pos.getY() + random.nextDouble() * 6d / 16d;
            double z = (double)pos.getZ() + 0.5d;
            if (random.nextDouble() < 0.1d) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction facing = state.get(FACING);
            Direction.Axis facingAxis = facing.getAxis();
            double outwardOffset = 0.52d;
            double sidewardOffset = random.nextDouble() * 0.6d - 0.3d;
            double addX = facingAxis == Direction.Axis.X ? (double)facing.getXOffset() * outwardOffset : sidewardOffset;
            double addY = random.nextDouble() * 6d / 16d;
            double addZ = facingAxis == Direction.Axis.Z ? (double)facing.getZOffset() * outwardOffset : sidewardOffset;
            world.addParticle(ParticleTypes.SMOKE, x + addX, y + addY, z + addZ, 0d, 0d, 0d);
            Minecraft.getInstance().particles.addEffect(new FurnaceFlameParticle(world, x + addX, y + addY, z + addZ, 0d, 0d, 0d));
        }
    }

    @Override
    protected void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof TileEntityMidnightFurnace) {
            player.openGui(Midnight.instance, GuiHandler.MIDNIGHT_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
            player.addStat(Stats.FURNACE_INTERACTION);
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active) {
            worldIn.setBlockState(pos, MidnightBlocks.MIDNIGHT_FURNACE_LIT.getDefaultState().with(FACING, state.get(FACING)), 3);
            worldIn.setBlockState(pos, MidnightBlocks.MIDNIGHT_FURNACE_LIT.getDefaultState().with(FACING, state.get(FACING)), 3);
        } else {
            worldIn.setBlockState(pos, MidnightBlocks.NIGHTSTONE_FURNACE.getDefaultState().with(FACING, state.get(FACING)), 3);
            worldIn.setBlockState(pos, MidnightBlocks.NIGHTSTONE_FURNACE.getDefaultState().with(FACING, state.get(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityMidnightFurnace();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlayer().getHorizontalFacing().getOpposite());
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
        return new ItemStack(MidnightBlocks.NIGHTSTONE_FURNACE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        Direction facing = Direction.byIndex(meta);
        if (facing.getAxis() == Direction.Axis.Y) {
            facing = Direction.NORTH;
        }

        return this.getDefaultState().with(FACING, facing);
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean canRenderInLayer(BlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || (layer == BlockRenderLayer.CUTOUT && this.isBurning);
    }

    @Override
    public int getPackedLightmapCoords(BlockState state, IEnviromentBlockReader worldIn, BlockPos pos) {
        if (this.isBurning && MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
            return source.getCombinedLight(pos, 15);
        }
        return source.getCombinedLight(pos, 0);
    }*/
}

