package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.ICybercraft.ISidedLimb;
import net.minecraft.item.ItemStack;

public class BodyPartItem extends CybercraftItem implements ISidedLimb {

    public BodyPartItem(Properties properties, EnumSlot slot, Quality q) {
        super(properties, slot, q);
    }

    public BodyPartItem(Properties properties, EnumSlot slot){
        this(properties, slot, null);
    }

    @Override
    public boolean isEssential(ItemStack stack) {
        return true;
    }

    @Override
    public int getEssenceCost(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        if (stack.getDamageValue() <= 7)
        {
            return CybercraftAPI.getCybercraft(other).isEssential(other);
        }

        ICybercraft ware = CybercraftAPI.getCybercraft(other);

        if (ware instanceof ISidedLimb)
        {
            return ware.isEssential(other) && ((ISidedLimb) ware).getSide(other) == this.getSide(stack);
        }
        return false;
    }

    @Override
    public EnumCategory getCategory(ItemStack stack) {
        return EnumCategory.BODYPARTS;
    }

    @Override
    public EnumSide getSide(ItemStack stack) {
        return stack.getDamageValue() % 2 == 0 ? EnumSide.LEFT : EnumSide.RIGHT;
    }

    @Override
    public boolean canHoldQuality(Quality quality) {
        return false;
    }
}
