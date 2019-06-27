package com.mushroom.midnight.common.tile.base;

import com.mushroom.midnight.common.block.MidnightFurnaceBlock;
import com.mushroom.midnight.common.container.MidnightFurnaceContainer;
import com.mushroom.midnight.common.registry.MidnightBlocks;
import com.mushroom.midnight.common.registry.MidnightItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityMidnightFurnace extends FurnaceTileEntity {

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.burnTime = TileEntityMidnightFurnace.getItemBurnTime(this.items.get(1));
    }

    @Override
    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("tile.midnight.midnight_furnace.name");
    }

    @Override
    public MidnightFurnaceContainer createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
        return new MidnightFurnaceContainer(playerIn.inventory, this);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 1) {
            ItemStack itemstack = this.items.get(1);
            return TileEntityMidnightFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
        return index != 2;
    }

    public static boolean isItemFuel(ItemStack stack) {
        return TileEntityMidnightFurnace.getItemBurnTime(stack) > 0;
    }

    public static int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
            if (burnTime >= 0) {
                return burnTime;
            }
            Item item = stack.getItem();
            if (item == Item.getItemFromBlock(MidnightBlocks.SHADOWROOT_SLAB) || item == Item.getItemFromBlock(MidnightBlocks.DARK_WILLOW_SLAB) || item == Item.getItemFromBlock(MidnightBlocks.DEAD_WOOD_SLAB)) {
                return 150;
            } else if (item == Item.getItemFromBlock(MidnightBlocks.DARK_PEARL_BLOCK)) {
                return 16000;
            } else if (item == MidnightItems.DARK_STICK) {
                return 100;
            } else if (item == MidnightItems.DARK_PEARL) {
                return 1600;
            } else {
                return FurnaceTileEntity.getBurnTimes().getOrDefault(stack.getItem(), 0);
            }
        }
    }

    @Override
    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (isBurning()) {
            --furnaceBurnTime;
        }
        if (!world.isRemote) {
            ItemStack itemstack = furnaceItemStacks.get(1);
            if (isBurning() || !itemstack.isEmpty() && !(furnaceItemStacks.get(0)).isEmpty()) {
                if (!isBurning() && canSmelt()) {
                    furnaceBurnTime = TileEntityMidnightFurnace.getItemBurnTime(itemstack);
                    currentItemBurnTime = furnaceBurnTime;
                    if (isBurning()) {
                        flag1 = true;
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                furnaceItemStacks.set(1, item1);
                            }
                        }
                    }
                }
                if (isBurning() && canSmelt()) {
                    ++cookTime;
                    if (cookTime == totalCookTime) {
                        cookTime = 0;
                        totalCookTime = getCookTime(furnaceItemStacks.get(0));
                        smeltItem();
                        flag1 = true;
                    }
                } else {
                    cookTime = 0;
                }
            } else if (!isBurning() && cookTime > 0) {
                cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
            }
            if (flag != isBurning()) {
                flag1 = true;
                MidnightFurnaceBlock.setState(isBurning(), world, pos);
            }
        }
        if (flag1) {
            markDirty();
        }
    }

    private boolean canSmelt() {
        if ((furnaceItemStacks.get(0)).isEmpty()) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks.get(0));
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = furnaceItemStacks.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }
}
