package com.rena.cybercraft.common.events;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.IDeconstructable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Cybercraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MiscHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public void handleCybercraftTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (CybercraftAPI.isCybercraft(stack))
        {
            ICybercraft ware = CybercraftAPI.getCybercraft(stack);
            ICybercraft.Quality quality = ware.getQuality();


            if (Screen.hasShiftDown()) {
                List<ITextComponent> info = ware.getInfo(stack);
                if (info != null) {
                    event.getToolTip().addAll(info);
                }

                NonNullList<Item> requirements = ware.requiredInstalledItems();
                if (!requirements.isEmpty()) {
                    StringTextComponent joined = new StringTextComponent("");
                    for (int indexRequirement = 0; indexRequirement < requirements.size(); indexRequirement++) {
                        if (indexRequirement != 0) {
                            joined.append(new TranslationTextComponent("cybercraft.tooltip.joiner").append(" "));
                        }
                        joined.append(requirements.get(indexRequirement).getDescription());

                    }
                    event.getToolTip().add(new TranslationTextComponent("cybercraft.tooltip.requires").append(" ").append(joined).withStyle(TextFormatting.AQUA));
                }
                event.getToolTip().add(new TranslationTextComponent("cybercraft.slot." + ware.getSlot(stack).getName()).withStyle(TextFormatting.RED));


                if (quality != null) {
                    event.getToolTip().add(new TranslationTextComponent(quality.getUnlocalizedName()));
                }
            } else {
                event.getToolTip().add(new TranslationTextComponent("cybercraft.tooltip.shift_prompt").withStyle(TextFormatting.DARK_GRAY));
            }
        } else if (stack.getItem() instanceof IDeconstructable) {
            if (event.getToolTip().size() > 1) {
                event.getToolTip().add(1, new TranslationTextComponent("cybercraft.tooltip.can_deconstruct").withStyle(TextFormatting.DARK_GRAY));
            } else {
                event.getToolTip().add(new TranslationTextComponent("cybercraft.tooltip.can_deconstruct").withStyle(TextFormatting.DARK_GRAY));
            }
        }
    }
}
