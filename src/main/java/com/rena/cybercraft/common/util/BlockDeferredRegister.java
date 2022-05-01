package com.rena.cybercraft.common.util;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockDeferredRegister extends DoubleDeferredRegister<Block, Item>{

	public BlockDeferredRegister(String modid) {
		super(modid, ForgeRegistries.BLOCKS, ForgeRegistries.ITEMS);
	}

	
	public <BLOCK extends Block> DoubleRegistryObject<BLOCK, BlockItem> register(String name, Supplier<BLOCK> blockSupplier, Supplier<Item.Properties> properties){
		return register(name, blockSupplier, block -> new BlockItem(block, properties.get()));
	}
	
	 public <BLOCK extends Block, ITEM extends BlockItem> DoubleRegistryObject<BLOCK, ITEM> register(String name, Supplier<? extends BLOCK> blockSupplier,
	          Function<BLOCK, ITEM> itemCreator) {
		 return register(name, blockSupplier, itemCreator, DoubleRegistryObject::new);
	 }

}
