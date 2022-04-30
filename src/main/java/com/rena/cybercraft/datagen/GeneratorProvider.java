package com.rena.cybercraft.datagen;

import com.rena.cybercraft.Cybercraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Cybercraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneratorProvider {

    @SubscribeEvent
    public static void generate(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeClient()){
            gatherClientData(gen, helper);
        }
        if(event.includeServer()){
            gatherServerData(gen, helper);
        }
        try {
            gen.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gatherClientData(DataGenerator gen, ExistingFileHelper helper){

    }

    private static void  gatherServerData(DataGenerator gen, ExistingFileHelper helper){
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, helper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, helper));
        gen.addProvider(new ModLootTableProvider(gen));
    }
}
