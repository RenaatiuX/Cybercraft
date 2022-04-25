package com.rena.cybercraft.common.item;

import com.rena.cybercraft.core.init.EffectInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class NeuropozyneItem extends Item {

    public NeuropozyneItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.abilities.instabuild)
        {
            stack.shrink(1);
        }

        player.addEffect(new EffectInstance(EffectInit.NEUROPOZYNE.get(), 24000, 0, false, false));

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        String neuropozyne = I18n.get("tooltip.cybercraft.neuropozyne");

        tooltip.add(new TranslationTextComponent(neuropozyne).withStyle(TextFormatting.BLUE));
    }
}
