package com.rena.cybercraft.common.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandlerHelper {


    public static final boolean isEmpty(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    public static ItemStack removeItem(IItemHandler inv, int index, int count) {
        return index >= 0 && index < inv.getSlots() && !(inv.getStackInSlot(index)).isEmpty() && count > 0 ? (inv.getStackInSlot(index)).split(count) : ItemStack.EMPTY;
    }

    public static void clear(IItemHandler handler){
        for (int i = 0;i<handler.getSlots();i++){
            handler.insertItem(i, ItemStack.EMPTY, false);
        }
    }

    public static final CompoundNBT serializeNBT(IItemHandler inv)
    {
        ListNBT nbtTagList = new ListNBT();
        for (int i = 0; i < inv.getSlots(); i++)
        {
            if (!inv.getStackInSlot(i).isEmpty())
            {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt("Slot", i);
                inv.getStackInSlot(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size",  inv.getSlots());
        return nbt;
    }

    public static final IItemHandler deserializeNBT(CompoundNBT nbt) {
        ItemStackHandler inv = new ItemStackHandler();
        inv.deserializeNBT(nbt);
        return inv;
    }
}
