package com.rena.cyberware.common.item;

import com.rena.cyberware.core.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CybercraftTab {

    public static final ItemGroup CYBERCRAFT = new ItemGroup("cybercraft") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.BRAIN.get());
        }
    };

}
