package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.common.util.ItemHandlerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;

public class MergeInventory implements IInventory, INBTSerializable<CompoundNBT> {

    public static ItemStack removeItem(IInventory inv, int index, int count) {
        return index >= 0 && index < inv.getContainerSize() && !(inv.getItem(index)).isEmpty() && count > 0 ? (inv.getItem(index)).split(count) : ItemStack.EMPTY;
    }

    private List<IItemHandler> inventories = new ArrayList<>();

    @Override
    public int getContainerSize() {
        int size = 0;
        for (IItemHandler inv : inventories)
            size += inv.getSlots();
        return size;
    }

    public void appendInventory(IInventory inv) {
        inventories.add(new InvWrapper(inv));
    }

    public void appendInventory(IItemHandler inv) {
        inventories.add(inv);
    }

    public void removeInventory(IItemHandler inv) {
        inventories.remove(inv);
    }

    public void removeInventory(IInventory inv) {
        for (IItemHandler handler : inventories) {
            if (handler instanceof InvWrapper) {
                if (((InvWrapper) handler).getInv().equals(inv)) {
                    inventories.remove(handler);
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        for (IItemHandler inv : inventories) {
            if (!ItemHandlerHelper.isEmpty(inv))
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        for (int i = 0; i < inventories.size(); i++) {
            if (index >= getStartIndex(i) && index < inventories.get(i).getSlots()) {
                return inventories.get(i).getStackInSlot(index - getStartIndex(i));
            }
        }
        return ItemStack.EMPTY;
    }

    private int getStartIndex(int indexInventories) {
        int startIndex = 0;
        for (int i = 0; i < indexInventories; i++) {
            startIndex += inventories.get(i).getSlots();
        }
        return startIndex;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        for (int i = 0; i < inventories.size(); i++) {
            if (index >= getStartIndex(i) && index < inventories.get(i).getSlots()) {
                ItemStack foundStack = ItemHandlerHelper.removeItem(inventories.get(i), index - getStartIndex(i), count);
                if (!foundStack.isEmpty()) {
                    setChanged();
                }
                return foundStack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        ItemStack stack = getItem(i);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            setItem(i, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        for (int i = 0; i < inventories.size(); i++) {
            if (index >= getStartIndex(i) && index < inventories.get(i).getSlots()) {
                inventories.get(i).insertItem(index - getStartIndex(i), itemStack, false);
            }
        }
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public void clearContent() {
        for (IItemHandler inv : inventories) {
            ItemHandlerHelper.clear(inv);
        }
        setChanged();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("invs", this.inventories.size());
        for (int i = 0; i < this.inventories.size(); i++) {
            CompoundNBT handler = ItemHandlerHelper.serializeNBT(this.inventories.get(i));
            nbt.put("handler" + i, handler);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        int inventoriesSize = nbt.getInt("invs");
        for (int i = 0; i < inventoriesSize; i++) {
            this.inventories.add(ItemHandlerHelper.deserializeNBT(nbt.getCompound("handler" + i)));
        }
    }
}
