package com.rena.cybercraft.datagen;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator p_i232552_1_, BlockTagsProvider p_i232552_2_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_i232552_1_, p_i232552_2_, Cybercraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for (Item i : ForgeRegistries.ITEMS){
            if (CybercraftAPI.isCybercraft(new ItemStack(i)))
                tag(Tags.Items.BLUPRINT_ITEMS).add(i);
        }
    }
}
