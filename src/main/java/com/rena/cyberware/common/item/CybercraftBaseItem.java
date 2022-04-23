package com.rena.cyberware.common.item;

import com.rena.cyberware.Cybercraft;
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

    /*@Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> itemStack) {
        if (this.allowdedIn(itemGroup)) {
            if (subnames.length == 0) {
                itemStack.add(new ItemStack(this));
            }
            for (int metadata = 0; metadata < subnames.length; metadata++) {
                itemStack.add(new ItemStack(this,1, metadata));
            }
        }
    }*/

    /*public ItemStack getCachedStack(int damage)
    {
        ItemStack itemStack = itemStackCache[damage];
        if ( itemStack != null
                && ( itemStack.getItem() != this
                || itemStack.getCount() != 1
                || getDamage(itemStack) != damage ) )
        {
            Cybercraft.LOGGER.error(String.format("Corrupted item stack cache: found %s as %s:%d, expected %s:%d",
                    itemStack, itemStack.getItem(), itemStack.getDamageValue(),
                    this, damage ));
            itemStack = null;
        }
        if (itemStack == null)
        {
            itemStack = new ItemStack(this, 1, damage);
            itemStackCache[damage] = itemStack;
        }
        return itemStack;
    }*/

}
