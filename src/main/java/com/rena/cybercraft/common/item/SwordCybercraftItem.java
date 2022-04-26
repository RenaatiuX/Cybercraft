package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.IDeconstructable;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.NonNullList;

import static com.rena.cybercraft.api.CybercraftAPI.withMetaData;

public class SwordCybercraftItem extends SwordItem implements IDeconstructable {

    public SwordCybercraftItem(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
    }

    @Override
    public boolean canDestroy(ItemStack stack) {
        return true;
    }

    @Override
    public NonNullList<ItemStack> getComponents(ItemStack stack) {
        return NNLUtil.fromArray(new ItemStack[]
                {
                        withMetaData(new ItemStack(Items.IRON_INGOT, 2), 0),
                        withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                        withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4)
                });
    }
}
