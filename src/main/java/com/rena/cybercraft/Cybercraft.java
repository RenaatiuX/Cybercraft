package com.rena.cybercraft;

import com.rena.cybercraft.client.screens.BlueprintArchiveScreen;
import com.rena.cybercraft.client.screens.ComponentBoxScreen;
import com.rena.cybercraft.client.screens.ScannerScreen;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.util.CybercraftTab;
import com.rena.cybercraft.core.init.*;
import com.rena.cybercraft.core.network.CCNetwork;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
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

    public static final ResourceLocation modLoc(String path){
        return new ResourceLocation(MOD_ID, path);
    }

    //Our instance, referenced in the below sub-class
    public static Cybercraft instance;
    //The strings for our name and modid + logger
    public static final String NAME = "Cybercraft";
    public static final String MOD_ID = "cybercraft";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup CYBERCRAFTAB = new CybercraftTab("cybercraft");
    //This sub-class below is the start where we'll add registry and stuff later on
    public Cybercraft() {
        instance = this;

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CybercraftConfig.init(new ForgeConfigSpec.Builder()));
        modEventBus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes);

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        BlockInit.CUSTOM.register(modEventBus);
        EffectInit.EFFECT.register(modEventBus);

        TileEntityTypeInit.TES.register(modEventBus);
        ContainerInit.CONTAINERS.register(modEventBus);

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
            ScreenManager.register(ContainerInit.SCANNER_CONTAINER.get(), ScannerScreen::new);
            ScreenManager.register(ContainerInit.COMPONENT_BOX_CONTAINER.get(), ComponentBoxScreen::new);
            ScreenManager.register(ContainerInit.BLUEPRINT_ARCHIVE_CONTAINER.get(), BlueprintArchiveScreen::new);
            ScreenManager.register(ContainerInit.ITEM_COMPONENT_BOX_CONTAINER.get(), ComponentBoxScreen::new);

            RenderTypeLookup.setRenderLayer(BlockInit.SCANNER_BLOCK.get(), RenderType.translucent());

        });
    }

}
