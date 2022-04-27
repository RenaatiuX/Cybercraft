package com.rena.cybercraft.events;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.IDeconstructable;
import com.rena.cybercraft.core.init.BlockInit;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class MiscHandler {

    public static final MiscHandler INSTANCE = new MiscHandler();

   /* @SubscribeEvent(priority = EventPriority.LOWEST)
    @OnlyIn(Dist.CLIENT)
    public void handleCybercraftTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (CybercraftAPI.isCybercraft(stack))
        {
            ICybercraft ware = CybercraftAPI.getCybercraft(stack);
            ICybercraft.Quality quality = ware.getQuality(stack);


            GameSettings settings = Minecraft.getInstance().options;
            if (settings.isDown(settings.keyBindSneak))
            {
                List<String> info = ware.getInfo(stack);
                if (info != null)
                {
                    event.getToolTip().addAll(info);
                }

                NonNullList<NonNullList<ItemStack>> requirements = ware.required(stack);
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
                            toAdd += I18n.get(requirements.get(indexRequirement).get(indexSubRequirement).getTranslationKey() + ".name");

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
    }

    @SubscribeEvent
    public void handleNeuropozynePopulation(LootTableLoadEvent event)
    {
        if ( event.getName() == LootTables.SIMPLE_DUNGEON
                || event.getName() == LootTables.ABANDONED_MINESHAFT
                || event.getName() == LootTables.STRONGHOLD_CROSSING
                || event.getName() == LootTables.STRONGHOLD_CORRIDOR
                || event.getName() == LootTables.STRONGHOLD_LIBRARY
                || event.getName() == LootTables.DESERT_PYRAMID
                || event.getName() == LootTables.JUNGLE_TEMPLE
                || event.getName() == LootTables.BURIED_TREASURE
                || event.getName() == LootTables.SHIPWRECK_TREASURE
                || event.getName() == LootTables.RUINED_PORTAL)
        {
            LootTable table = event.getTable();
            LootPool main = event.getTable().getPool("main");
            if (main != null)
            {
                LootCondition[] lc = new LootCondition[0];
                LootFunction[] lf = new LootFunction[] { new SetCount(lc, new RandomValueRange(16F, 64F)) };
                main.addRandomItems(new LootEntryItem(ItemInit.NEUROPOZYNE.get(), 15, 0, lf, lc, "cybercraft:neuropozyne"));
            }
        }

        if (event.getName() == LootTables.NETHER_BRIDGE
                || event.getName() == LootTables.BASTION_OTHER
                || event.getName() == LootTables.BASTION_BRIDGE
                || event.getName() == LootTables.BASTION_HOGLIN_STABLE
                || event.getName() == LootTables.BASTION_TREASURE)
        {
            LootTable table = event.getTable();
            LootPool main = event.getTable().getPool("main");
            if (main != null)
            {
                LootCondition[] lc = new LootCondition[0];
                LootFunction[] lf = new LootFunction[0];
                main.addRandomItems(new LootEntryItem(Item.byBlock(BlockInit.CHARGER_BLOCK.get()), 15, 0, lf, lc, "cybercraft:surgery_apparatus")); //it must be changed by the surgery block
            }
        }
    }*/

}
