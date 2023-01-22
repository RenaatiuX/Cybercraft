package com.rena.cybercraft.core;

import com.rena.cybercraft.Cybercraft;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class Tags {

    public static final class Items{
        public static final ITag.INamedTag<Item> BLUPRINT_ITEMS = mod("blueprint_items");
        public static final ITag.INamedTag<Item> COMPONENTS = mod("components");
        public static final ITag.INamedTag<Item> BODY_PARTS = mod("body_parts");

        private static ITag.INamedTag<Item> mod(String name){
            return ItemTags.bind(Cybercraft.modLoc(name).toString());
        }
    }
}
