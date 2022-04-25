package com.rena.cybercraft.api.item;

import net.minecraft.item.ItemStack;

public interface ICybercraftTabItem {

    public static enum EnumCategory
    {
        BLOCKS,
        BODYPARTS,
        EYES,
        CRANIUM,
        HEART,
        LUNGS,
        LOWER_ORGANS,
        SKIN,
        MUSCLE,
        BONE,
        ARM,
        HAND,
        LEG,
        FOOT;
    }

    public EnumCategory getCategory(ItemStack stack);

}
