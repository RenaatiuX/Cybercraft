package com.rena.cybercraft;

import com.rena.cybercraft.client.renderer.tileentity.PostBeaconTileEntityRenderer;
import com.rena.cybercraft.client.renderer.tileentity.TileEntityEngineeringRender;
import com.rena.cybercraft.client.screens.*;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import com.rena.cybercraft.common.events.CybercraftDataHandler;
import com.rena.cybercraft.common.item.CybercraftSpawnEgg;
import com.rena.cybercraft.common.util.CybercraftTab;
import com.rena.cybercraft.core.init.*;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.common.events.loot.NeuropozyneAdditionModifier;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

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
        modEventBus.addListener(CybercraftDataHandler::entityAttributeModification);

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        BlockInit.CUSTOM.register(modEventBus);
        EffectInit.EFFECT.register(modEventBus);
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);

        TileEntityTypeInit.TES.register(modEventBus);
        ContainerInit.CONTAINERS.register(modEventBus);
        AttributeInit.ATTRIBUTES.register(modEventBus);

        ModLoadingContext context = ModLoadingContext.get();
        //Our listener for setup, it will pick up on anything put into setup and notify Forge of it
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::ClientSetup);
        modEventBus.addListener(this::registerEntityAttributes);



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
            ScreenManager.register(ContainerInit.ENGINEERING_TABLE_CONTAINER.get(), EngineeringTableScreen::new);
            ScreenManager.register(ContainerInit.SURGERY_CONTAINER.get(), SurgeryScreen::new);

            RenderTypeLookup.setRenderLayer(BlockInit.SCANNER_BLOCK.get(), RenderType.translucent());

            ClientRegistry.bindTileEntityRenderer(TileEntityTypeInit.ENGINEERING_TABLE.get(), TileEntityEngineeringRender::new);
            ClientRegistry.bindTileEntityRenderer(TileEntityTypeInit.RADIO_TOWER.get(), PostBeaconTileEntityRenderer::new);

        });
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeInit.CYBER_ZOMBIE.get(), CyberZombieEntity.createAttributes().build());
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistriesCybercraft {

        @SubscribeEvent
        public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
            CybercraftSpawnEgg.initSpawnEggs();
        }
        @SubscribeEvent
        public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){

            event.getRegistry().registerAll(
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_abandoned_mineshaft")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_simple_dungeon")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_stronghold_crossing")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_stronghold_corridor")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_stronghold_library")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_desert_pyramid")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_jungle_temple")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_buried_treasure")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_shipwreck_treasure")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_shipwreck_map")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_shipwreck_supply")),
                    new NeuropozyneAdditionModifier.Serializer().setRegistryName
                            (new ResourceLocation(Cybercraft.MOD_ID, "neuropozyne_in_ruined_portal"))
            );

        }

    }

}
