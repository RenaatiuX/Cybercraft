package com.rena.cybercraft.datagen;

import com.rena.cybercraft.Cybercraft;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_i48256_1_,@Nullable ExistingFileHelper existingFileHelper) {
        super(p_i48256_1_, Cybercraft.MOD_ID, existingFileHelper);
    }
}
