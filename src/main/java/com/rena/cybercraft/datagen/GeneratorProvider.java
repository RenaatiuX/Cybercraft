package com.rena.cybercraft.datagen;

import com.rena.cybercraft.Cybercraft;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Cybercraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneratorProvider {

    @SubscribeEvent
    public static final void generate(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeClient()){
            gatherCLientData(gen, helper);
        }else if(event.includeServer()){
            gatherServerData(gen, helper);
        }
    }

    private static void gatherCLientData(DataGenerator gen, ExistingFileHelper helper){

    }

    private static void  gatherServerData(DataGenerator gen, ExistingFileHelper helper){
        gen.addProvider(new ModRecipeProvider(gen));
    }
}
