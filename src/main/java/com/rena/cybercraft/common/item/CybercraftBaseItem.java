package com.rena.cybercraft.common.item;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CybercraftBaseItem extends Item {

    public String[] subnames;
    private ItemStack[] itemStackCache;

    public CybercraftBaseItem(Properties properties, String... subnames) {
        super(properties);
        this.subnames = subnames;
        itemStackCache = new ItemStack[Math.max(subnames.length, 1)];
    }



    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> itemStack) {
        if (this.allowdedIn(itemGroup)) {
            if (subnames.length == 0) {
                itemStack.add(new ItemStack(this));
            }
            for (int metadata = 0; metadata < subnames.length; metadata++) {
                itemStack.add(CybercraftAPI.withMetaData(new ItemStack(this,1), metadata));
            }
        }
    }

    public ItemStack getCachedStack(int metadata)
    {
        ItemStack itemStack = itemStackCache[metadata];
        if ( itemStack != null && ( itemStack.getItem() != this || itemStack.getCount() != 1 || CybercraftAPI.getMetaData(itemStack) == metadata ))
        {
            Cybercraft.LOGGER.error(String.format("Corrupted item stack cache: found %s as %s:%d, expected %s:%d",
                    itemStack, itemStack.getItem(), CybercraftAPI.getMetaData(itemStack),
                    this, metadata ));
            itemStack = null;
        }
        if (itemStack == null)
        {
            itemStack = CybercraftAPI.withMetaData(new ItemStack(this, 1), metadata);
            itemStackCache[metadata] = itemStack;
        }
        return itemStack;
    }

}
