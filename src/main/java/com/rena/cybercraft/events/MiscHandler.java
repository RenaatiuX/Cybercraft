package com.rena.cybercraft.events;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.IDeconstructable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class MiscHandler {

    public static final MiscHandler INSTANCE = new MiscHandler();

    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public void handleCybercraftTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (CybercraftAPI.isCybercraft(stack))
        {
            ICybercraft ware = CybercraftAPI.getCybercraft(stack);
            ICybercraft.Quality quality = ware.getQuality();


            if (Screen.hasShiftDown())
            {
                List<ITextComponent> info = ware.getInfo(stack);
                if (info != null)
                {
                    event.getToolTip().addAll(info);
                }

                NonNullList<Item> requirements = ware.requiredInstalledItems();
                if (requirements.size() > 0)
                {
                    String joined = "";
                    for (int indexRequirement = 0; indexRequirement < requirements.size(); indexRequirement++)
                    {
                        String toAdd = "";

                        for (int indexSubRequirement = 0; indexSubRequirement < requirements.get(indexRequirement).size(); indexSubRequirement++)
                        {
                            if (indexSubRequirement != 0)
                            {
                                toAdd += " " + I18n.get("cybercraft.tooltip.joiner_or") + " ";
                            }
                            toAdd += I18n.get(requirements.get(indexRequirement).get(indexSubRequirement).getDisplayName() + ".name");

                        }

                        if (indexRequirement != 0)
                        {
                            joined += I18n.get("cybercraft.tooltip.joiner") + " ";
                        }
                        joined += toAdd;

                    }
                    event.getToolTip().add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.requires") + " " + joined).withStyle(TextFormatting.AQUA));
                }
                event.getToolTip().add(new TranslationTextComponent(I18n.get("cybercraft.slot." + ware.getSlot(stack).getName())).withStyle(TextFormatting.RED));


                if (quality != null)
                {
                    event.getToolTip().add(new TranslationTextComponent(I18n.get(quality.getUnlocalizedName())));
                }
            }
            else
            {
                event.getToolTip().add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.shift_prompt")).withStyle(TextFormatting.DARK_GRAY));
            }
        }
        else if (stack.getItem() instanceof IDeconstructable)
        {
            if (event.getToolTip().size() > 1)
            {
                event.getToolTip().add(1, new TranslationTextComponent(I18n.get("cybercraft.tooltip.can_deconstruct")).withStyle(TextFormatting.DARK_GRAY));
            }
            else
            {
                event.getToolTip().add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.can_deconstruct")).withStyle(TextFormatting.DARK_GRAY));
            }
        }
    }*/
}
