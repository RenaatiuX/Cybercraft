package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.block.*;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.item.block.CybercraftItemBlock;
import com.rena.cybercraft.common.item.block.ItemComponentBox;
import com.rena.cybercraft.common.util.BlockDeferredRegister;
import com.rena.cybercraft.common.util.DoubleRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Cybercraft.MOD_ID)
public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Cybercraft.MOD_ID);
    public static final BlockDeferredRegister CUSTOM = new BlockDeferredRegister(Cybercraft.MOD_ID);

    public static final RegistryObject<ScannerBlock> SCANNER_BLOCK = BLOCKS.register("scanner",
            ScannerBlock::new);
    public static final RegistryObject<ChargerBlock> CHARGER_BLOCK = BLOCKS.register("charger",
            ChargerBlock::new);
    public static final RegistryObject<BlueprintArchiveBlock> BLUEPRINT_ARCHIVE_BLOCK = BLOCKS.register("blueprint_archive",
            BlueprintArchiveBlock::new);
    public static final RegistryObject<EngineeringTableBlock> ENGINEERING_TABLE = BLOCKS.register("engineering_table",
            EngineeringTableBlock::new);
    public static final DoubleRegistryObject<ComponentBoxBlock, ItemComponentBox> COMPONENT_BOX = CUSTOM.register("component_box",
            ComponentBoxBlock::new, ItemComponentBox::new);
    public static final RegistryObject<BeaconBlock> RADIO = BLOCKS.register("beacon", BeaconBlock::new);
    public static final RegistryObject<BeaconPostBlock> RADIO_POST = BLOCKS.register("radio_post", BeaconPostBlock::new);
    public static final RegistryObject<SurgeryBlock> SURGERY = BLOCKS.register("surgery", SurgeryBlock::new);
    public static final RegistryObject<SurgeryChamberBlock> SURGERY_CHAMBER_BLOCK = BLOCKS.register("surgery_chamber",
            SurgeryChamberBlock::new);

    @SubscribeEvent
    public static final void registerBlockItems(RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();
        BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> registry.register(new BlockItem(block, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)).setRegistryName(block.getRegistryName())));
        for (String s : CybercraftConfig.C_MOBS.startItems.get(ICybercraft.EnumSlot.EYES).get()){
            System.out.println(s);
        }

    }
}
