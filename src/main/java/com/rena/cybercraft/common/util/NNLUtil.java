package com.rena.cybercraft.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

public class NNLUtil {
    public static NonNullList<ItemStack> copyList(NonNullList<ItemStack> nnl) {
        NonNullList<ItemStack> nnlCopy = NonNullList.create();
        nnlCopy.addAll(nnl);
        return nnlCopy;
    }

    public static NonNullList<ItemStack> deepCopyList(Iterable<ItemStack> nnl) {
        NonNullList<ItemStack> copy = NonNullList.create();
        for (ItemStack stack : nnl) {
            copy.add(stack.copy());
        }
        return copy;
    }

    public static CompoundNBT saveAllItemsWithEmpty(CompoundNBT nbt, NonNullList<ItemStack> stacks) {
        ListNBT listnbt = new ListNBT();

        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack itemstack = stacks.get(i);
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putByte("Slot", (byte) i);
            itemstack.save(compoundnbt);
            listnbt.add(compoundnbt);
        }

        if (!listnbt.isEmpty()) {
            nbt.put("Items", listnbt);
        }

        return nbt;
    }

    public static NonNullList<ItemStack> fromArray(@Nonnull ItemStack[] array) {
        NonNullList<ItemStack> nnl = NonNullList.create();
        for (ItemStack stack : array) {
            nnl.add(stack);
        }
        return nnl;
    }

    public static NonNullList<NonNullList<ItemStack>> fromArray(@Nonnull ItemStack[][] array) {
        NonNullList<NonNullList<ItemStack>> nnlRoot = NonNullList.create();
        for (ItemStack[] arraySub : array) {
            NonNullList<ItemStack> nnlSub = NonNullList.create();
            for (ItemStack stack : arraySub) {
                nnlSub.add(stack);
            }
            nnlRoot.add(nnlSub);
        }
        return nnlRoot;
    }

    public static NonNullList<ItemStack> initListOfSize(int size) {
        NonNullList<ItemStack> nnl = NonNullList.create();
        for (int index = 0; index < size; index++) {
            nnl.add(ItemStack.EMPTY);
        }
        return nnl;
    }
}
