package com.rena.cybercraft.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICybercraft {

    EnumSlot getSlot(ItemStack stack);
    int installedStackSize(ItemStack stack);
    boolean isIncompatible(ItemStack stack, ItemStack comparison);
    boolean isEssential(ItemStack stack);
    List<ITextComponent> getInfo(ItemStack stack);
    int getCapacity(ItemStack wareStack);
    NonNullList<Item> requiredInstalledItems();


    Quality getQuality();

    Item withQuality(Quality quality);
    boolean canHoldQuality(Quality quality);

    class Quality
    {
        private static Map<String, Quality> mapping = new HashMap<>();
        public static List<Quality> qualities = new ArrayList<>();
        private String unlocalizedName;
        private String nameModifier;
        private String spriteSuffix;

        public Quality(String unlocalizedName)
        {
            this(unlocalizedName, null, null);
        }

        public Quality(String unlocalizedName, String nameModifier, String spriteSuffix)
        {
            this.unlocalizedName = unlocalizedName;
            this.nameModifier = nameModifier;
            this.spriteSuffix = spriteSuffix;
            mapping.put(unlocalizedName, this);
            qualities.add(this);
        }

        public String getUnlocalizedName()
        {
            return unlocalizedName;
        }

        public static Quality getQualityFromString(String name)
        {
            if (mapping.containsKey(name))
            {
                return mapping.get(name);
            }
            return null;
        }

        public String getNameModifier()
        {
            return nameModifier;
        }

        public String getSpriteSuffix()
        {
            return spriteSuffix;
        }
    }

    // @TODO rename to BodyRegion since it's more a type/category than an actual inventory slot
    enum EnumSlot
    {
        EYES(12, "eyes"),
        CRANIUM(11, "cranium"),
        HEART(14, "heart"),
        LUNGS(15, "lungs"),
        LOWER_ORGANS(17, "lower_organs"),
        SKIN(18, "skin"),
        MUSCLE(19, "muscle"),
        BONE(20, "bone"),
        ARM(21, "arm", true, true),
        HAND(22, "hand", true, false),
        LEG(23, "leg", true, true),
        FOOT(24, "foot", true, false);

        private final int slotNumber;
        private final String name;
        private final boolean sidedSlot;
        private final boolean hasEssential;

        EnumSlot(int slot, String name, boolean sidedSlot, boolean hasEssential)
        {
            this.slotNumber = slot;
            this.name = name;
            this.sidedSlot = sidedSlot;
            this.hasEssential = hasEssential;
        }

        EnumSlot(int slot, String name)
        {
            this(slot, name, false, true);
        }

        public int getSlotNumber()
        {
            return slotNumber;
        }

        public static EnumSlot getSlotByPage(int page)
        {
            for (EnumSlot slot : values())
            {
                if (slot.getSlotNumber() == page)
                {
                    return slot;
                }
            }
            return null;
        }

        public String getName()
        {
            return name;
        }

        public boolean isSided()
        {
            return sidedSlot;
        }

        public boolean hasEssential()
        {
            return hasEssential;
        }
    }

    void onAdded(LivingEntity livingEntity, ItemStack stack);
    void onRemoved(LivingEntity livingEntity, ItemStack stack);

    interface ISidedLimb
    {
        EnumSide getSide(ItemStack stack);

        enum EnumSide
        {
            LEFT,
            RIGHT;
        }
    }

    int getEssenceCost(ItemStack stack);

}
