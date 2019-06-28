package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.block.MidnightChestBlock;
import com.mushroom.midnight.common.block.MidnightChestBlock.ChestModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileEntityMidnightChest extends TileEntityLockableLoot implements ITickable {
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(27, ItemStack.EMPTY);
    public boolean adjacentChestChecked;
    public TileEntityMidnightChest adjacentChestZNeg;
    public TileEntityMidnightChest adjacentChestXPos;
    public TileEntityMidnightChest adjacentChestXNeg;
    public TileEntityMidnightChest adjacentChestZPos;
    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;
    private BlockChest.Type cachedChestType;
    private final ChestModel chestModel;

    public TileEntityMidnightChest() {
        this.chestModel = ChestModel.getDefault();
    }

    public TileEntityMidnightChest(ChestModel chestModel) {
        this.chestModel = chestModel;
    }

    public TileEntityMidnightChest(BlockChest.Type typeIn) {
        this.cachedChestType = typeIn;
        this.chestModel = ChestModel.getDefault();
    }

    public ChestModel getChestModel() {
        return this.chestModel;
    }

    public boolean isSameChest(ChestModel chestModel) {
        return this.chestModel == chestModel;
    }

    @Override
    public String getName() {
        return hasCustomName() ? customName : "tile.midnight." + chestModel.getName() + "_chest.name";
    }

    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(2, 2, 2));
    }

    protected boolean isChestAt(BlockPos posIn) {
        if (this.world != null) {
            Block block = this.world.getBlockState(posIn).getBlock();
            if (block instanceof MidnightChestBlock && ((BlockChest) block).chestType == getChestType()) {
                TileEntity tile = this.world.getTileEntity(posIn);
                if (tile instanceof TileEntityMidnightChest) {
                    return isSameChest(((TileEntityMidnightChest) tile).getChestModel());
                }
            }
        }
        return false;
    }

    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            if (this.world == null || !this.world.isAreaLoaded(this.pos, 1)) {
                return; // Forge: prevent loading unloaded chunks when checking neighbors
            }
            this.adjacentChestChecked = true;
            this.adjacentChestXNeg = this.getAdjacentChest(Direction.WEST);
            this.adjacentChestXPos = this.getAdjacentChest(Direction.EAST);
            this.adjacentChestZNeg = this.getAdjacentChest(Direction.NORTH);
            this.adjacentChestZPos = this.getAdjacentChest(Direction.SOUTH);
        }
    }

    @Nullable
    protected TileEntityMidnightChest getAdjacentChest(Direction side) {
        BlockPos blockpos = this.pos.offset(side);
        if (isChestAt(blockpos)) {
            TileEntity tileentity = this.world.getTileEntity(blockpos);
            if (tileentity instanceof TileEntityMidnightChest) {
                TileEntityMidnightChest tileentitychest = (TileEntityMidnightChest) tileentity;
                tileentitychest.setNeighbor(this, Dist.getOpposite());
                return tileentitychest;
            }
        }
        return null;
    }

    public int getSizeInventory() {
        return 27;
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.chestContents) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void readFromNBT(CompoundNBT compound) {
        super.readFromNBT(compound);
        this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.chestContents);
        }

        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
    }

    public CompoundNBT writeToNBT(CompoundNBT compound) {
        super.writeToNBT(compound);

        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }

        if (this.hasCustomName()) {
            compound.putString("CustomName", this.customName);
        }

        return compound;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = false;
        doubleChestHandler = null;
    }

    @SuppressWarnings("incomplete-switch")
    protected void setNeighbor(TileEntityMidnightChest chestTe, Direction side) {
        if (chestTe.isInvalid()) {
            this.adjacentChestChecked = false;
        } else if (this.adjacentChestChecked) {
            switch (side) {
                case NORTH:

                    if (this.adjacentChestZNeg != chestTe) {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case SOUTH:

                    if (this.adjacentChestZPos != chestTe) {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case EAST:

                    if (this.adjacentChestXPos != chestTe) {
                        this.adjacentChestChecked = false;
                    }

                    break;
                case WEST:

                    if (this.adjacentChestXNeg != chestTe) {
                        this.adjacentChestChecked = false;
                    }
            }
        }
    }

    public void update() {
        this.checkForAdjacentChests();
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        ++this.ticksSinceSync;

        if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
            this.numPlayersUsing = 0;
            float f = 5.0F;

            for (PlayerEntity PlayerEntity : this.world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double) ((float) i - 5.0F), (double) ((float) j - 5.0F), (double) ((float) k - 5.0F), (double) ((float) (i + 1) + 5.0F), (double) ((float) (j + 1) + 5.0F), (double) ((float) (k + 1) + 5.0F)))) {
                if (PlayerEntity.openContainer instanceof ContainerChest) {
                    IInventory iinventory = ((ContainerChest) PlayerEntity.openContainer).getLowerChestInventory();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest) iinventory).isPartOfLargeChest(this)) {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        float f1 = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double d1 = (double) i + 0.5D;
            double d2 = (double) k + 0.5D;

            if (this.adjacentChestZPos != null) {
                d2 += 0.5D;
            }

            if (this.adjacentChestXPos != null) {
                d1 += 0.5D;
            }

            this.world.playSound(null, d1, (double) j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0) {
                this.lidAngle += 0.1F;
            } else {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f3 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                double d3 = (double) i + 0.5D;
                double d0 = (double) k + 0.5D;

                if (this.adjacentChestZPos != null) {
                    d0 += 0.5D;
                }

                if (this.adjacentChestXPos != null) {
                    d3 += 0.5D;
                }

                this.world.playSound(null, d3, (double) j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }

    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);

            if (this.getChestType() == BlockChest.Type.TRAP) {
                this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), false);
            }
        }
    }

    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator() && this.getBlockType() instanceof BlockChest) {
            --this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);

            if (this.getChestType() == BlockChest.Type.TRAP) {
                this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), false);
            }
        }
    }

    public MidnightVanillaDoubleChestItemHandler doubleChestHandler;

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (doubleChestHandler == null || doubleChestHandler.needsRefresh()) {
                doubleChestHandler = MidnightVanillaDoubleChestItemHandler.get(this);
            }
            if (doubleChestHandler != null && doubleChestHandler != MidnightVanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
                return (T) doubleChestHandler;
            }
        }
        return super.getCapability(capability, facing);
    }

    public IItemHandler getSingleChestHandler() {
        return super.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.checkForAdjacentChests();
    }

    public BlockChest.Type getChestType() {
        if (this.cachedChestType == null) {
            if (this.world == null || !(this.getBlockType() instanceof BlockChest)) {
                return BlockChest.Type.BASIC;
            }

            this.cachedChestType = ((BlockChest) this.getBlockType()).chestType;
        }

        return this.cachedChestType;
    }

    public String getGuiID() {
        return "minecraft:chest";
    }

    public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
        this.fillWithLoot(playerIn);
        return new ContainerChest(playerInventory, this, playerIn);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }
}
