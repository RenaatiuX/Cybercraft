package com.rena.cyberware;

import com.rena.cyberware.common.config.CybercraftConfig;
import com.rena.cyberware.core.init.ItemInit;
import com.rena.cyberware.core.network.CCNetwork;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Cybercraft.MOD_ID)
public class Cybercraft {

    public static final ResourceLocation modeLoc(String path){
        return new ResourceLocation(MOD_ID, path);
    }

    //Our instance, referenced in the below sub-class
    public static Cybercraft instance;
    //The strings for our name and modid + logger
    public static final String NAME = "Cybercraft";
    public static final String MOD_ID = "cybercraft";
    public static final Logger LOGGER = LogManager.getLogger();

    //This sub-class below is the start where we'll add registry and stuff later on
    public Cybercraft() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CybercraftConfig.init(new ForgeConfigSpec.Builder()));

        ItemInit.ITEMS.register(modEventBus);

        ModLoadingContext context = ModLoadingContext.get();
        //Our listener for setup, it will pick up on anything put into setup and notify Forge of it
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::ClientSetup);



    }

    private void setup(final FMLCommonSetupEvent event) {
        //This is for thread-safe operations later on such as world-gen
        event.enqueueWork(() -> {
            CCNetwork.init();
        });
    }

    private void ClientSetup(final FMLClientSetupEvent event){

        event.enqueueWork(() -> {

        });
    }

}
