package com.rena.cybercraft.api.item;

import com.rena.cybercraft.api.CybercraftAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class EnableDisableHelper {

    public static final String ENABLED_STR = "~enabled";

    public static boolean isEnabled(ItemStack stack) {
        if (stack.isEmpty()) return false;

        CompoundNBT tagCompound = CybercraftAPI.getCybercraftNBT(stack);
        if (!tagCompound.contains(ENABLED_STR)) {
            return true;
        }

        return tagCompound.getBoolean(ENABLED_STR);
    }

    public static void toggle(ItemStack stack) {
        CompoundNBT tagCompound = CybercraftAPI.getCybercraftNBT(stack);
        if (isEnabled(stack)) {
            tagCompound.putBoolean(ENABLED_STR, false);
        } else {
            tagCompound.remove(ENABLED_STR);
        }
    }

    public static String getUnlocalizedLabel(ItemStack stack) {
        if (isEnabled(stack)) {
            return "cybercraft.gui.active.disable";
        } else {
            return "cybercraft.gui.active.enable";
        }
    }

}
