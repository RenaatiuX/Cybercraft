package com.rena.cybercraft.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ExpCapsuleItem extends Item {

    public ExpCapsuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> list) {
        if (this.allowdedIn(group)) {
            ItemStack stack = new ItemStack(this);
            CompoundNBT tagCompound = new CompoundNBT();
            tagCompound.putInt("xp", 100);
            stack.setTag(tagCompound);
            list.add(stack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isFoil(ItemStack stack)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        int xp = 0;
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("xp"))
            {
                xp = tagCompound.getInt("xp");
            }
        }

        if (!player.abilities.instabuild)
        {
            stack.shrink(1);
        }

        player.giveExperienceLevels(xp);

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int xp = 0;
        CompoundNBT tagCompound = stack.getTag();
        if (tagCompound != null)
        {
            if (tagCompound.contains("xp"))
            {
                xp = tagCompound.getInt("xp");
            }
        }
        String before = I18n.get("tooltip.cybercraft.exp_capsule.before");
        if (before.length() > 0) before += " ";

        String after = I18n.get("tooltip.cybercraft.exp_capsule.after");
        if (after.length() > 0) after = " " + after;

        tooltip.add(new TranslationTextComponent(before + xp + after).withStyle(TextFormatting.RED));
    }
}
