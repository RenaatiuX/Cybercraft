package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ISpecialBattery;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class DenseBatteryItem extends CybercraftItem implements ISpecialBattery {

    public DenseBatteryItem(Properties properties, EnumSlot slots) {
        super(properties, slots);
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other)
    {
        return other.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get()
                && stack.getDamageValue() == LowerOrgansUpgradeItem.META_BATTERY;
    }

    @Override
    public int add(ItemStack battery, ItemStack power, int amount, boolean simulate) {
        if (power == ItemStack.EMPTY)
        {
            int amountToAdd = Math.min(getCapacity(battery) - getStoredEnergy(battery), amount);
            if (!simulate)
            {
                CompoundNBT data = CybercraftAPI.getCybercraftNBT(battery);
                data.putInt("power", data.getInt("power") + amountToAdd);
            }
            return amountToAdd;
        }
        return 0;
    }

    @Override
    public int extract(ItemStack battery, int amount, boolean simulate) {
        int amountToSub = Math.min(getStoredEnergy(battery), amount);
        if (!simulate)
        {
            CompoundNBT data = CybercraftAPI.getCybercraftNBT(battery);
            data.putInt("power", data.getInt("power") - amountToSub);
        }
        return amountToSub;
    }

    @Override
    public int getStoredEnergy(ItemStack battery) {

        CompoundNBT data = CybercraftAPI.getCybercraftNBT(battery);

        if (!data.contains("power"))
        {
            data.putInt("power", 0);
        }
        return data.getInt("power");
    }

    @Override
    public int getCapacity(ItemStack wareStack) {

        return LibConstants.DENSE_BATTERY_CAPACITY;
    }
}
