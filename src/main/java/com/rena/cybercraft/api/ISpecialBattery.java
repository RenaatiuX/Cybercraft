package com.rena.cybercraft.api;

import net.minecraft.item.ItemStack;

public interface ISpecialBattery {

    int add(ItemStack battery, ItemStack power, int amount, boolean simulate);

    int extract(ItemStack battery, int amount, boolean simulate);

    int getStoredEnergy(ItemStack battery);

    int getCapacity(ItemStack battery);

}
