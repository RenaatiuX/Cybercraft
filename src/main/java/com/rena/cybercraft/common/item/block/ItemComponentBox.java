package com.rena.cybercraft.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemComponentBox extends CybercraftItemBlock{
    public ItemComponentBox(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStackIn = player.getItemInHand(hand);
        player.openItemGui(itemStackIn, hand);
        return new ActionResult<>(ActionResultType.PASS, itemStackIn);
    }

    @Override
    public ActionResultType useOn(ItemUseContext currentItem) {
        PlayerEntity playerentity = currentItem.getPlayer();
        ItemStack itemstack = currentItem.getItemInHand();
        Hand hand = currentItem.getHand();
        if (playerentity.isShiftKeyDown())
        {
            ActionResultType res = super.useOn(currentItem);
            if (res == ActionResultType.SUCCESS && playerentity.isCreative())
            {
                playerentity.inventory.items.set(playerentity.inventory.selected, ItemStack.EMPTY);
            }
            return res;
        }
        else
        {
            playerentity.openItemGui(itemstack, hand);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltip) {
        text.add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.component_box")).withStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.component_box2")).withStyle(TextFormatting.GRAY));
    }
}
