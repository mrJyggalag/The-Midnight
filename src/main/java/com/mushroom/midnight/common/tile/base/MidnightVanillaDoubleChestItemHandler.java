package com.mushroom.midnight.common.tile.base;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;

// TODO make it when able to launch midnight
public class MidnightVanillaDoubleChestItemHandler extends WeakReference<TileEntityMidnightChest> implements IItemHandlerModifiable {
    public MidnightVanillaDoubleChestItemHandler(TileEntityMidnightChest referent) {
        super(referent);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {

    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return false;
    }
    /*public static final MidnightVanillaDoubleChestItemHandler NO_ADJACENT_CHESTS_INSTANCE = new MidnightVanillaDoubleChestItemHandler(null, null, false);
    private final boolean mainChestIsUpper;
    private final TileEntityMidnightChest mainChest;
    private final int hashCode;

    public MidnightVanillaDoubleChestItemHandler(@Nullable TileEntityMidnightChest mainChest, @Nullable TileEntityMidnightChest other, boolean mainChestIsUpper) {
        super(other);
        this.mainChest = mainChest;
        this.mainChestIsUpper = mainChestIsUpper;
        hashCode = Objects.hashCode(mainChestIsUpper ? mainChest : other) * 31 + Objects.hashCode(!mainChestIsUpper ? mainChest : other);
    }

    @Nullable
    public static MidnightVanillaDoubleChestItemHandler get(TileEntityMidnightChest chest) {
        World world = chest.getWorld();
        BlockPos pos = chest.getPos();
        if (world == null || pos == null || !world.isBlockLoaded(pos)) {
            return null; // Still loading
        }
        Block blockType = chest.getBlockType();
        Direction[] horizontals = Direction.HORIZONTALS;
        for (int i = horizontals.length - 1; i >= 0; i--) { // Use reverse order so we can return early
            Direction Direction = horizontals[i];
            BlockPos blockpos = pos.offset(Direction);
            Block block = world.getBlockState(blockpos).getBlock();
            if (block == blockType) {
                TileEntity otherTE = world.getTileEntity(blockpos);
                if (otherTE instanceof TileEntityMidnightChest) {
                    TileEntityMidnightChest otherChest = (TileEntityMidnightChest) otherTE;
                    return new MidnightVanillaDoubleChestItemHandler(chest, otherChest, Direction != Direction.WEST && Direction != Direction.NORTH);
                }
            }
        }
        return NO_ADJACENT_CHESTS_INSTANCE; //All alone
    }

    @Nullable
    public TileEntityMidnightChest getChest(boolean accessingUpper) {
        if (accessingUpper == mainChestIsUpper) {
            return mainChest;
        } else {
            return getOtherChest();
        }
    }

    @Nullable
    private TileEntityMidnightChest getOtherChest() {
        TileEntityMidnightChest tileEntityChest = get();
        return tileEntityChest != null && !tileEntityChest.isInvalid() ? tileEntityChest : null;
    }

    @Override
    public int getSlots() {
        return 27 * 2;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        boolean accessingUpperChest = slot < 27;
        int targetSlot = accessingUpperChest ? slot : slot - 27;
        TileEntityMidnightChest chest = getChest(accessingUpperChest);
        return chest != null ? chest.getStackInSlot(targetSlot) : ItemStack.EMPTY;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        boolean accessingUpperChest = slot < 27;
        int targetSlot = accessingUpperChest ? slot : slot - 27;
        TileEntityMidnightChest chest = getChest(accessingUpperChest);
        if (chest != null) {
            IItemHandler singleHandler = chest.getSingleChestHandler();
            if (singleHandler instanceof IItemHandlerModifiable) {
                ((IItemHandlerModifiable) singleHandler).setStackInSlot(targetSlot, stack);
            }
        }
        chest = getChest(!accessingUpperChest);
        if (chest != null) {
            chest.markDirty();
        }
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        boolean accessingUpperChest = slot < 27;
        int targetSlot = accessingUpperChest ? slot : slot - 27;
        TileEntityMidnightChest chest = getChest(accessingUpperChest);
        if (chest == null) {
            return stack;
        }
        int starting = stack.getCount();
        ItemStack ret = chest.getSingleChestHandler().insertItem(targetSlot, stack, simulate);
        if (ret.getCount() != starting && !simulate) {
            chest = getChest(!accessingUpperChest);
            if (chest != null) {
                chest.markDirty();
            }
        }
        return ret;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        boolean accessingUpperChest = slot < 27;
        int targetSlot = accessingUpperChest ? slot : slot - 27;
        TileEntityMidnightChest chest = getChest(accessingUpperChest);
        if (chest == null) {
            return ItemStack.EMPTY;
        }
        ItemStack ret = chest.getSingleChestHandler().extractItem(targetSlot, amount, simulate);
        if (!ret.isEmpty() && !simulate) {
            chest = getChest(!accessingUpperChest);
            if (chest != null) {
                chest.markDirty();
            }
        }
        return ret;
    }

    @Override
    public int getSlotLimit(int slot) {
        boolean accessingUpperChest = slot < 27;
        return getChest(accessingUpperChest).getInventoryStackLimit();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        boolean accessingUpperChest = slot < 27;
        int targetSlot = accessingUpperChest ? slot : slot - 27;
        TileEntityMidnightChest chest = getChest(accessingUpperChest);
        if (chest != null) {
            return chest.getSingleChestHandler().isItemValid(targetSlot, stack);
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MidnightVanillaDoubleChestItemHandler that = (MidnightVanillaDoubleChestItemHandler) o;
        if (hashCode != that.hashCode) {
            return false;
        }
        final TileEntityMidnightChest otherChest = getOtherChest();
        if (mainChestIsUpper == that.mainChestIsUpper) {
            return Objects.equal(mainChest, that.mainChest) && Objects.equal(otherChest, that.getOtherChest());
        } else {
            return Objects.equal(mainChest, that.getOtherChest()) && Objects.equal(otherChest, that.mainChest);
        }
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public boolean needsRefresh() {
        if (this == NO_ADJACENT_CHESTS_INSTANCE) {
            return false;
        }
        TileEntityMidnightChest tileEntityChest = get();
        return tileEntityChest == null || tileEntityChest.isInvalid();
    }*/
}
