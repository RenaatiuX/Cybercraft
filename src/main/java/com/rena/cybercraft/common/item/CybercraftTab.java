package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.ICybercraftTabItem;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.events.CreativeMenuHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CybercraftTab extends ItemGroup{

    public CybercraftTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.BRAIN.get());
    }

   @Override
    public void fillItemList(NonNullList<ItemStack> list) {
        Map<ICybercraftTabItem.EnumCategory, List<ItemStack>> subLists = new EnumMap(ICybercraftTabItem.EnumCategory.class);
        for (ICybercraftTabItem.EnumCategory category : ICybercraftTabItem.EnumCategory.values())
        {
            subLists.put(category, new ArrayList<>());
        }
        NonNullList<ItemStack> unsorted = NonNullList.create();

        ICybercraft.Quality q = CreativeMenuHandler.pageSelected == 0 ? CybercraftAPI.QUALITY_SCAVENGED : CybercraftAPI.QUALITY_MANUFACTURED;

        for (Item item : ForgeRegistries.ITEMS)
        {
            if (item == null)
            {
                continue;
            }
            for (ItemGroup tab : item.getCreativeTabs())
            {
                if (tab == this)
                {
                    if (item instanceof ICybercraftTabItem)
                    {
                        NonNullList<ItemStack> tempList = NonNullList.create();
                        item.fillItemCategory(this, tempList);

                        for (ItemStack stack : tempList)
                        {
                            if (!stack.isEmpty())
                            {
                                if (CybercraftAPI.isCybercraft(stack))
                                {
                                    ICybercraft ware = CybercraftAPI.getCybercraft(stack);
                                    if (ware.canHoldQuality(stack, q))
                                    {
                                        stack = ware.setQuality(stack, q);
                                    }
                                }
                                ICybercraftTabItem.EnumCategory cat = ((ICybercraftTabItem) stack.getItem()).getCategory(stack);
                                subLists.get(cat).add(stack);
                            }
                        }
                    }
                    else
                    {
                        item.fillItemCategory(this, unsorted);
                    }
                }
            }
        }

        for (ICybercraftTabItem.EnumCategory category : ICybercraftTabItem.EnumCategory.values())
        {
            List<ItemStack> toAdd = subLists.get(category);
            list.addAll(toAdd);
        }

        list.addAll(unsorted);
    }
}
