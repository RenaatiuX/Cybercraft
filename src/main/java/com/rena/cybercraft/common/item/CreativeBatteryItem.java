package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ISpecialBattery;
import net.minecraft.item.ItemStack;

public class CreativeBatteryItem extends CybercraftItem implements ISpecialBattery {

    public CreativeBatteryItem(Properties properties, EnumSlot slots) {
        super(properties, slots);
    }

    @Override
    public int add(ItemStack battery, ItemStack power, int amount, boolean simulate) {
        return amount;
    }

    @Override
    public int extract(ItemStack battery, int amount, boolean simulate) {
        return amount;
    }

    @Override
    public int getStoredEnergy(ItemStack battery) {
        return 999999;
    }

    @Override
    public int getCapacity(ItemStack wareStack) {
        return 999999;
    }

    @Override
    public boolean canHoldQuality(ItemStack stack, Quality quality) {
        return quality == CybercraftAPI.QUALITY_MANUFACTURED;
    }
}
