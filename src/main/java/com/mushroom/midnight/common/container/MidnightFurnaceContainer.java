package com.mushroom.midnight.common.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;

// TODO make it when able to launch midnight
public class MidnightFurnaceContainer extends FurnaceContainer {
    public MidnightFurnaceContainer(int p_i50082_1_, PlayerInventory p_i50082_2_) {
        super(p_i50082_1_, p_i50082_2_);
    }

    /*public MidnightFurnaceContainer(PlayerInventory playerInventory, IInventory furnaceInventory) {
        super(playerInventory, furnaceInventory);
        Slot slot = new FurnaceFuelSlot(furnaceInventory, 1, 56, 53) {
            @Override
            public boolean isItemValid(ItemStack stack) { return TileEntityMidnightFurnace.isItemFuel(stack) || stack.getItem() == Items.BUCKET; }
        };
        slot.slotNumber = 1;
        inventorySlots.set(1, slot);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (TileEntityMidnightFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemstack1);
        }
        return itemstack;
    }*/
}
