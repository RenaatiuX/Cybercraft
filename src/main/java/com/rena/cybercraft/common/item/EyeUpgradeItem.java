package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IHudjack;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class EyeUpgradeItem extends CybercraftItem implements IMenuItem, IHudjack {

    public EyeUpgradeItem(Properties properties, EnumSlot slots, String ... subnames) {
        super(properties, slots, subnames);
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        return other.getItem() == ItemInit.CYBER_EYES.get();
    }

    @Override
    public boolean isActive(ItemStack stack) {
        return EnableDisableHelper.isEnabled(stack);
    }

    @Override
    public boolean hasMenu(ItemStack stack) {
        return true;
    }

    @Override
    public void use(Entity entity, ItemStack stack) {
        EnableDisableHelper.toggle(stack);
    }

    @Override
    public String getUnlocalizedLabel(ItemStack stack) {
        return EnableDisableHelper.getUnlocalizedLabel(stack);
    }

    private static final float[] f = new float[] { 1F, 0F, 0F };

    @Override
    public float[] getColor(ItemStack stack) {
        return EnableDisableHelper.isEnabled(stack) ? f : null;
    }
}
