package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.item.IBlueprint;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlueprintItem extends Item implements IBlueprint {

    public BlueprintItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public ItemStack getResult(ItemStack stack, NonNullList<ItemStack> items) {
        return null;
    }

    @Override
    public NonNullList<ItemStack> consumeItems(ItemStack stack, NonNullList<ItemStack> items) {
        return null;
    }
}
