package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.block.ChargerBlock;
import com.rena.cybercraft.common.block.ScannerBlock;
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

    public static final RegistryObject<ScannerBlock> SCANNER_BLOCK = BLOCKS.register("scanner", ScannerBlock::new);
    public static final RegistryObject<ChargerBlock> CHARGER_BLOCK = BLOCKS.register("charger", ChargerBlock::new);

    @SubscribeEvent
    public static final void registerBlockItems(RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();
        BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            registry.register(new BlockItem(block, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)).setRegistryName(block.getRegistryName()));
        });
    }
}
