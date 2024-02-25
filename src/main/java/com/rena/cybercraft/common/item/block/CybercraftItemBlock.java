package com.rena.cybercraft.common.item.block;

import com.rena.cybercraft.api.item.ICybercraftTabItem;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CybercraftItemBlock extends BlockItem implements ICybercraftTabItem {

    private String[] tt;

    public CybercraftItemBlock(Block block, Properties properties) {
        super(block, properties);
    }

    public CybercraftItemBlock(Block block, Properties properties, String... tooltip) {
        super(block, properties);
        this.tt = tooltip;
    }

    @Override
    public EnumCategory getCategory(ItemStack stack) {
        return EnumCategory.BLOCKS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltip) {
        if (this.tt != null) {
            for (String str : tt) {
                text.add(new TranslationTextComponent(I18n.get(str)).withStyle(TextFormatting.GRAY));
            }
        }
    }
}
