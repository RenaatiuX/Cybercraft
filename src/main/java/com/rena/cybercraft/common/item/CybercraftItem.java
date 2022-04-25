package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.ICybercraftTabItem;
import com.rena.cybercraft.api.item.IDeconstructable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

   public CybercraftItem setWeights(int... weight)
    {
        for (int meta = 0; meta < weight.length; meta++)
        {
            ItemStack stack = CybercraftAPI.withMetaData(new ItemStack(this, 1), meta);
            int installedStackSize = installedStackSize(stack);
            stack.setCount(installedStackSize);
            this.setQuality(stack, CybercraftAPI.QUALITY_SCAVENGED);
            //ItemInit.zombieItems.add(new ZombieItem(weight[meta], stack));
        }
        return this;
    }

    public CybercraftItem setEssenceCost(int... essence){
        this.essence = essence;
        return this;
    }

    public CybercraftItem setComponents(NonNullList<NonNullList<ItemStack>> components) {
        this.components = components;
        return this;
    }

    @Override
    public EnumSlot getSlot(ItemStack stack) {
        return slots[Math.min(slots.length - 1, CybercraftAPI.getMetaData(stack))];
    }

    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        return essence[Math.min(this.subnames.length, CybercraftAPI.getMetaData(stack))];
    }

    @Override
    public int installedStackSize(ItemStack stack) {
        return 1;
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
    public List<ITextComponent> getInfo(ItemStack stack) {
        List<ITextComponent> ret = new ArrayList<>();
        this.appendHoverText(stack, null, ret, null);
        return ret;
    }

    @Override
    public int getCapacity(ItemStack wareStack) {
        return 0;
    }

    @Override
    public Quality getQuality(ItemStack stack) {
        Quality q = CybercraftAPI.getQualityTag(stack);
        if (q == null) return CybercraftAPI.QUALITY_MANUFACTURED;
        return q;
    }

    @Override
    public ItemStack setQuality(ItemStack stack, Quality quality) {
        if (quality == CybercraftAPI.QUALITY_MANUFACTURED)
        {
            if (!stack.isEmpty() && stack.hasTag())
            {
                stack.getTag().remove(CybercraftAPI.QUALITY_TAG);
                if (stack.getTag().isEmpty())
                {
                    stack.setTag(null);
                }
            }
            return stack;
        }
        return this.canHoldQuality(stack, quality) ? CybercraftAPI.writeQualityTag(stack, quality) : stack;
    }

    @Override
    public boolean canHoldQuality(ItemStack stack, Quality quality) {
        return true;
    }

    @Override
    public void onAdded(LivingEntity livingEntity, ItemStack stack) {}

    @Override
    public void onRemoved(LivingEntity livingEntity, ItemStack stack) {}

    @Override
    public int getEssenceCost(ItemStack stack) {
        int cost = getUnmodifiedEssenceCost(stack);
        if (getQuality(stack) == CybercraftAPI.QUALITY_SCAVENGED){
            float half = ((float)cost) / 2f;
            if (cost > 0){
                cost += Math.ceil(half);
            }else {
                cost -= Math.ceil(half);
            }
        }
        return cost;
    }

    @Override
    public EnumCategory getCategory(ItemStack stack) {
        return EnumCategory.values()[this.getSlot(stack).ordinal()];
    }

    @Override
    public boolean canDestroy(ItemStack stack) {
        return CybercraftAPI.getMetaData(stack) < this.components.size();
    }

    @Override
    public NonNullList<ItemStack> getComponents(ItemStack stack) {
        return components.get(Math.min(this.components.size() - 1, CybercraftAPI.getMetaData(stack)));
    }

    public int getPowerConsumption(ItemStack stack)
    {
        return 0;
    }

    public int getPowerProduction(ItemStack stack)
    {
        return 0;
    }

    public boolean hasCustomPowerMessage(ItemStack stack)
    {
        return false;
    }

    public boolean hasCustomCapacityMessage(ItemStack stack)
    {
        return false;
    }


    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> itemStack) {
        if (this.getCreativeTabs().contains(itemGroup)){
            if (subnames.length == 0){
                itemStack.add(new ItemStack(this));
            }else{
                for (int meta = 0;meta<subnames.length;meta++){
                    itemStack.add(CybercraftAPI.withMetaData(new ItemStack(this), meta));
                }
            }
        }
        super.fillItemCategory(itemGroup, itemStack);
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        Quality q = getQuality(stack);
        if (q != null && q.getNameModifier() != null)
        {
            return new TranslationTextComponent(q.getNameModifier()).append(super.getName(stack));
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        if (installedStackSize(stack) > 1)
        {
            list.add(new TranslationTextComponent("cybercraft.tooltip.max_install", installedStackSize(stack)).withStyle(TextFormatting.BLUE));
        }

        boolean hasPowerConsumption = false;
        IFormattableTextComponent toAddPowerConsumption = StringTextComponent.EMPTY.copy();
        for (int i = 0; i < installedStackSize(stack); i++)
        {
            ItemStack temp = stack.copy();
            temp.setCount(i+1);
            int cost = this.getPowerConsumption(temp);
            if (cost > 0)
            {
                hasPowerConsumption = true;
            }

            if (i != 0)
            {
                toAddPowerConsumption.append(new TranslationTextComponent("cybercraft.tooltip.joiner", cost));
            }else
                toAddPowerConsumption.append(" " + cost);
        }

        if (hasPowerConsumption)
        {
            String toTranslate = hasCustomPowerMessage(stack) ?
                    "cybercraft.tooltip." + this.getRegistryName().toString().substring(10)
                            + (this.subnames.length > 0 ? "." + CybercraftAPI.getMetaData(stack) : "") + ".power_consumption"
                    :
                    "cybercraft.tooltip.power_consumption";
            list.add(new TranslationTextComponent(toTranslate).append(toAddPowerConsumption).withStyle(TextFormatting.GREEN));
        }

        boolean hasPowerProduction = false;
        IFormattableTextComponent toAddPowerProduction = StringTextComponent.EMPTY.copy();
        for (int i = 0; i < installedStackSize(stack); i++)
        {
            ItemStack temp = stack.copy();
            temp.setCount(i+1);
            int cost = this.getPowerProduction(temp);
            if (cost > 0)
            {
                hasPowerProduction = true;
            }

            if (i != 0)
            {
                toAddPowerProduction.append(new TranslationTextComponent("cybercraft.tooltip.joiner", cost));
            }else
                toAddPowerProduction.append(new TranslationTextComponent("", cost));
        }

        if (hasPowerProduction)
        {
            String toTranslate = hasCustomPowerMessage(stack) ?
                    "cybercraft.tooltip." + this.getRegistryName().toString().substring(10)
                            + (this.subnames.length > 0 ? "." + CybercraftAPI.getMetaData(stack) : "") + ".power_production"
                    :
                    "cybercraft.tooltip.power_production";
            list.add(new TranslationTextComponent(toTranslate).append(toAddPowerProduction).withStyle(TextFormatting.GREEN));
        }

        if (getCapacity(stack) > 0)
        {
            String toTranslate = hasCustomCapacityMessage(stack) ?
                    "cybercraft.tooltip." + this.getRegistryName().toString().substring(10)
                            + (this.subnames.length > 0 ? "." + CybercraftAPI.getMetaData(stack) : "") + ".capacity"
                    :
                    "cybercraft.tooltip.capacity";
            list.add(new TranslationTextComponent(toTranslate, getCapacity(stack)));
        }


        boolean hasEssenceCost = false;
        boolean essenceCostNegative = true;
       IFormattableTextComponent toAddEssence = StringTextComponent.EMPTY.copy();
        for (int i = 0; i < installedStackSize(stack); i++)
        {
            ItemStack temp = stack.copy();
            temp.setCount(i+1);
            int cost = this.getEssenceCost(temp);
            if (cost != 0)
            {
                hasEssenceCost = true;
            }
            if (cost < 0)
            {
                essenceCostNegative = false;
            }

            if (i != 0)
            {
                toAddEssence.append(new TranslationTextComponent("cybercraft.tooltip.joiner", cost));
            }

            toAddEssence.append(new TranslationTextComponent("", Math.abs(cost)));
        }

        if (hasEssenceCost)
        {
            list.add(new TranslationTextComponent(essenceCostNegative ? "cybercraft.tooltip.essence" : "cybercraft.tooltip.essence_add").append(toAddEssence).withStyle(TextFormatting.DARK_PURPLE));
        }

    }
}
