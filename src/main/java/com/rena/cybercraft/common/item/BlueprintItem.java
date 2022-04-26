package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.IBlueprint;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlueprintItem extends Item implements IBlueprint {

    public BlueprintItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("blueprintItem"))
            {
                GameSettings settings = Minecraft.getInstance().options;
                if (settings.setKey(settings.keyShift);
                {
                    ItemStack blueprintItem = new ItemStack(tagCompound.getCompound("blueprintItem"));
                    if (!blueprintItem.isEmpty() && CybercraftAPI.canDeconstruct(blueprintItem))
                    {
                        NonNullList<ItemStack> items = NNLUtil.copyList(CybercraftAPI.getComponents(blueprintItem));
                        tooltip.add(new TranslationTextComponent(I18n.get("tooltip.cybercraft.blueprint", blueprintItem.getDisplayName())));
                        for (ItemStack item : items)
                        {
                            if (!item.isEmpty())
                            {
                                tooltip.add(new TranslationTextComponent(item.getCount() + " x " + item.getDisplayName()));
                            }
                        }
                        return;
                    }
                }
                else
                {
                    tooltip.add(new TranslationTextComponent(I18n.get("tooltip.cybercraft.shift_prompt")).withStyle(TextFormatting.DARK_GRAY));
                    return;
                }
            }
        }
        tooltip.add(new TranslationTextComponent(I18n.get("tooltip.cybercraft.craft_blueprint")).withStyle(TextFormatting.DARK_GRAY));
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.allowdedIn(group)) {
            list.add(CybercraftAPI.withMetaData(new ItemStack(this, 1), 1));
        }
    }

    public static ItemStack getBlueprintForItem(ItemStack stack)
    {
        if (!stack.isEmpty() && CybercraftAPI.canDeconstruct(stack))
        {
            ItemStack toBlue = stack.copy();


            toBlue.setCount(1);
            if (toBlue.isDamageableItem())
            {
                toBlue.setDamageValue(0);
            }
            toBlue.setTag(null);

            ItemStack ret = new ItemStack(ItemInit.BLUEPRINT.get());
            CompoundNBT tagCompound = new CompoundNBT();
            tagCompound.put("blueprintItem", toBlue.save(new CompoundNBT()));

            ret.setTag(tagCompound);
            return ret;
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("blueprintItem"))
            {
                ItemStack blueprintItem = new ItemStack(tagCompound.getCompound("blueprintItem"));
                if (!blueprintItem.isEmpty())
                {
                    return (new TranslationTextComponent(I18n.get("item.cybercraft.blueprint.not_blank.name", blueprintItem.getDisplayName()))).trim();
                }
            }
        }
        return (new TranslationTextComponent("" + I18n.get(this.getDescriptionId(stack) + ".name"))).trim();
    }

    @Override
    public ItemStack getResult(ItemStack stack, NonNullList<ItemStack> items) {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("blueprintItem"))
            {
                ItemStack blueprintItem = new ItemStack(tagCompound.getCompound("blueprintItem"));
                if (!blueprintItem.isEmpty() && CybercraftAPI.canDeconstruct(blueprintItem))
                {
                    NonNullList<ItemStack> requiredItems = NNLUtil.copyList(CybercraftAPI.getComponents(blueprintItem));
                    for (ItemStack requiredItem : requiredItems) {
                        ItemStack required = requiredItem.copy();
                        boolean satisfied = false;
                        for (ItemStack crafting : items) {
                            if (!crafting.isEmpty() && !required.isEmpty()) {
                                if (crafting.getItem() == required.getItem() && crafting.getDamageValue() == required.getDamageValue() && (!required.hasTag() || (ItemStack.tagMatches(required, crafting)))) {
                                    required.shrink(crafting.getCount());
                                }
                                if (required.getCount() <= 0) {
                                    satisfied = true;
                                    break;
                                }
                            }
                        }
                        if (!satisfied) return ItemStack.EMPTY;
                    }

                    return blueprintItem;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> consumeItems(ItemStack stack, NonNullList<ItemStack> items) {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("blueprintItem"))
            {
                ItemStack blueprintItem = new ItemStack(tagCompound.getCompound("blueprintItem"));
                if (!blueprintItem.isEmpty() && CybercraftAPI.canDeconstruct(blueprintItem))
                {
                    NonNullList<ItemStack> requiredItems = NNLUtil.copyList(CybercraftAPI.getComponents(blueprintItem));
                    NonNullList<ItemStack> newCrafting = NonNullList.create();
                    newCrafting.addAll(items);
                    for (ItemStack requiredItem : requiredItems) {
                        ItemStack required = requiredItem.copy();
                        for (int c = 0; c < newCrafting.size(); c++) {
                            ItemStack crafting = newCrafting.get(c);
                            if (!crafting.isEmpty() && !required.isEmpty()) {
                                if (crafting.getItem() == required.getItem() && crafting.getDamageValue() == required.getDamageValue() && (!required.hasTag() || (ItemStack.tagMatches(required, crafting)))) {
                                    int toSubtract = Math.min(required.getCount(), crafting.getCount());
                                    required.shrink(toSubtract);
                                    crafting.shrink(toSubtract);
                                    if (crafting.getCount() <= 0) {
                                        crafting = ItemStack.EMPTY;
                                    }
                                    newCrafting.set(c, crafting);
                                }
                                if (required.getCount() <= 0) {
                                    break;
                                }
                            }
                        }
                    }

                    return newCrafting;
                }
            }
        }
        throw new IllegalStateException("Consuming items when items shouldn't be consumed!");
    }

    @Override
    public NonNullList<ItemStack> getRequirementsForDisplay(ItemStack stack)
    {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("blueprintItem"))
            {
                ItemStack blueprintItem = new ItemStack(tagCompound.getCompound("blueprintItem"));
                if (!blueprintItem.isEmpty() && CybercraftAPI.canDeconstruct(blueprintItem))
                {
                    return CybercraftAPI.getComponents(blueprintItem);
                }
            }
        }

        return NonNullList.create();
    }

    @Override
    public ItemStack getIconForDisplay(ItemStack stack)
    {
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("blueprintItem"))
            {
                return new ItemStack(tagCompound.getCompound("blueprintItem"));
            }
        }

        return ItemStack.EMPTY;
    }
}
