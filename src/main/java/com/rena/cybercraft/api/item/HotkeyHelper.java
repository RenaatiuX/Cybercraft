package com.rena.cybercraft.api.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class HotkeyHelper {

    public static void assignHotkey(ICybercraftUserData cybercraftUserData, ItemStack stack, int key) {
        removeHotkey(cybercraftUserData, stack);

        cybercraftUserData.addHotkey(key, stack);
        CybercraftAPI.getCybercraftNBT(stack).putInt("hotkey", key);
    }

    public static void removeHotkey(ICybercraftUserData cybercraftUserData, int key) {
        ItemStack stack = cybercraftUserData.getHotkey(key);
        removeHotkey(cybercraftUserData, stack);
    }

    public static void removeHotkey(ICybercraftUserData cybercraftUserData, ItemStack stack) {
        int hotkey = getHotkey(stack);

        if (hotkey != -1) {
            cybercraftUserData.removeHotkey(hotkey);
            CybercraftAPI.getCybercraftNBT(stack).remove("hotkey");
        }
    }

    public static int getHotkey(ItemStack stack) {
        if (stack.isEmpty()) return -1;

        CompoundNBT tagCompound = CybercraftAPI.getCybercraftNBT(stack);
        if (!tagCompound.contains("hotkey")) {
            return -1;
        }
        return tagCompound.getInt("hotkey");
    }
}
