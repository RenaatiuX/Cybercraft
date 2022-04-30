package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.ICybercraftTabItem;
import com.rena.cybercraft.api.item.IDeconstructable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CybercraftItem extends CybercraftBaseItem implements ICybercraft, ICybercraftTabItem, IDeconstructable {

    private EnumSlot slot;
    private int essence;
    private final Quality q;
    private int weight;

    /**
     *
     * @param properties - the properties of this item
     * @param slot - the slot this will fit in
     * @param q - the Quality this stack has, can be null if no quality is assigned
     */
    public CybercraftItem(Properties properties, EnumSlot slot, @Nullable Quality q) {
        super(properties);
        this.slot = slot;
        this.q = q;
    }

   public CybercraftItem setWeight(int weight) {
        this.weight = weight;
         return this;
    }


    public CybercraftItem setEssenceCost(int essence){
        this.essence = essence;
        return this;
    }

    @Override
    public EnumSlot getSlot(ItemStack stack) {
        return this.slot;
    }

    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        return essence;
    }

    @Override
    public int installedStackSize(ItemStack stack) {
        return 1;
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
    public NonNullList<Item> requiredInstalledItems() {
        return NonNullList.create();
    }

    @Override
    public Quality getQuality() {
        if (q == null)
            return CybercraftAPI.QUALITY_MANUFACTURED;
        return q;
    }

    @Override
    public Item withQuality(Quality quality) {
        if (!canHoldQuality(quality)){
            return this;
        }
        if (quality == q)
            return this;
        String path = "";
        if (quality == CybercraftAPI.QUALITY_MANUFACTURED){
            path += this.getRegistryName().getPath().replace(q.getSpriteSuffix(), "");
            path = path.substring(0, path.length() - 1);
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.getRegistryName().getNamespace(), path));
        }
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.getRegistryName().getNamespace(), this.getRegistryName().getPath() + "_" + quality.getSpriteSuffix()));
    }

    @Override
    public boolean canHoldQuality(Quality quality) {
        return true;
    }

    @Override
    public void onAdded(LivingEntity livingEntity, ItemStack stack) {}

    @Override
    public void onRemoved(LivingEntity livingEntity, ItemStack stack) {}

    @Override
    public int getEssenceCost(ItemStack stack) {
        int cost = getUnmodifiedEssenceCost(stack);
        if (getQuality() == CybercraftAPI.QUALITY_SCAVENGED){
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
        return true;
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
    public ITextComponent getName(ItemStack stack) {
        Quality q = getQuality();
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
            String toTranslate = hasCustomPowerMessage(stack) ? "cybercraft.tooltip." + this.getRegistryName().getPath() + ".power_consumption" : "cybercraft.tooltip.power_consumption";
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
                toAddPowerProduction.append(new StringTextComponent("" + cost));
        }

        if (hasPowerProduction)
        {
            String toTranslate = hasCustomPowerMessage(stack) ? "cybercraft.tooltip." + this.getRegistryName().getPath() + ".power_production" : "cybercraft.tooltip.power_production";
            list.add(new TranslationTextComponent(toTranslate).append(toAddPowerProduction).withStyle(TextFormatting.GREEN));
        }

        if (getCapacity(stack) > 0)
        {
            String toTranslate = hasCustomCapacityMessage(stack) ? "cybercraft.tooltip." + this.getRegistryName().getPath() + ".capacity" : "cybercraft.tooltip.capacity";
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

            toAddEssence.append(new StringTextComponent("" + Math.abs(cost)));
        }

        if (hasEssenceCost)
        {
            list.add(new TranslationTextComponent(essenceCostNegative ? "cybercraft.tooltip.essence" : "cybercraft.tooltip.essence_add").append(toAddEssence).withStyle(TextFormatting.DARK_PURPLE));
        }

    }
}
