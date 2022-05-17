package com.rena.cybercraft.common.tileentities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MergeInventory implements IInventory {

    public static ItemStack removeItem(IInventory inv, int index, int count) {
        return index >= 0 && index < inv.getContainerSize() && !(inv.getItem(index)).isEmpty() && count > 0 ? (inv.getItem(index)).split(count) : ItemStack.EMPTY;
    }

    private List<IInventory> inventories = new ArrayList<>();

    @Override
    public int getContainerSize() {
        int size = 0;
        for (IInventory inv : inventories)
            size += inv.getContainerSize();
        return size;
    }

    public void appendInventory(IInventory inv){
        inventories.add(inv);
    }

    public void removeInventory(IInventory inv){
        inventories.remove(inv);
    }

    @Override
    public boolean isEmpty() {
        for (IInventory inv : inventories) {
            if (!inv.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        for (int i = 0; i < inventories.size(); i++) {
            if (index >= getStartIndex(i) && index < inventories.get(i).getContainerSize()){
                return inventories.get(i).getItem(index - getStartIndex(i));
            }
        }
        return ItemStack.EMPTY;
    }

    private int getStartIndex(int indexInventories) {
        int startIndex = 0;
        for (int i = 0; i < indexInventories; i++) {
            startIndex += inventories.get(i).getContainerSize();
        }
        return startIndex;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        for (int i = 0; i < inventories.size(); i++) {
            if (index >= getStartIndex(i) && index < inventories.get(i).getContainerSize()){
                ItemStack foundStack = removeItem(inventories.get(i), index - getStartIndex(i), count);
                if (!foundStack.isEmpty()){
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
            if (index >= getStartIndex(i) && index < inventories.get(i).getContainerSize()) {
                inventories.get(i).setItem(index - getStartIndex(i), itemStack);
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
        for (IInventory inv : inventories){
            inv.clearContent();
        }
        setChanged();
    }
}
