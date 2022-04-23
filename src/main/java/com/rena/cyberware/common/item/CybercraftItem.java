package com.rena.cyberware.common.item;

import com.rena.cyberware.api.CybercraftAPI;
import com.rena.cyberware.api.item.ICybercraft;
import com.rena.cyberware.api.item.ICybercraftTabItem;
import com.rena.cyberware.api.item.IDeconstructable;
import com.rena.cyberware.core.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;

public class CybercraftItem extends CybercraftBaseItem implements ICybercraft, ICybercraftTabItem, IDeconstructable {

    private EnumSlot[] slots;
    private int[] essence;
    private NonNullList<NonNullList<ItemStack>> components;

    public CybercraftItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, subnames);
        this.slots = slots;

        this.essence = new int[subnames.length + 1];
        this.components = NonNullList.create();
    }

    public CybercraftItem(Properties properties, EnumSlot slot, String[] subnames)
    {
        this(properties, new EnumSlot[] { slot }, subnames);
    }

    public CybercraftItem(Properties properties, EnumSlot slot)
    {
        this(properties, slot, new String[0]);
    }

   /* public CybercraftItem setWeights(int... weight)
    {
        for (int meta = 0; meta < weight.length; meta++)
        {
            ItemStack stack = new ItemStack(this, 1, meta);
            int installedStackSize = installedStackSize(stack);
            stack.setCount(installedStackSize);
            this.setQuality(stack, CybercraftAPI.QUALITY_SCAVENGED);
            ItemInit.zombieItems.add(new ZombieItem(weight[meta], stack));
        }
        return this;
    }*/

    @Override
    public EnumSlot getSlot(ItemStack stack) {
        return null;
    }

    @Override
    public int installedStackSize(ItemStack stack) {
        return 0;
    }

    @Override
    public NonNullList<NonNullList<ItemStack>> required(ItemStack stack) {
        return null;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack comparison) {
        return false;
    }

    @Override
    public boolean isEssential(ItemStack stack) {
        return false;
    }

    @Override
    public List<String> getInfo(ItemStack stack) {
        return null;
    }

    @Override
    public int getCapacity(ItemStack wareStack) {
        return 0;
    }

    @Override
    public Quality getQuality(ItemStack stack) {
        return null;
    }

    @Override
    public ItemStack setQuality(ItemStack stack, Quality quality) {
        return null;
    }

    @Override
    public boolean canHoldQuality(ItemStack stack, Quality quality) {
        return false;
    }

    @Override
    public void onAdded(LivingEntity livingEntity, ItemStack stack) {

    }

    @Override
    public void onRemoved(LivingEntity livingEntity, ItemStack stack) {

    }

    @Override
    public int getEssenceCost(ItemStack stack) {
        return 0;
    }

    @Override
    public EnumCategory getCategory(ItemStack stack) {
        return null;
    }

    @Override
    public boolean canDestroy(ItemStack stack) {
        return false;
    }

    @Override
    public NonNullList<ItemStack> getComponents(ItemStack stack) {
        return null;
    }
}
