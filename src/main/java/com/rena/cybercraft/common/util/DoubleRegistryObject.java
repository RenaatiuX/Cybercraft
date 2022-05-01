package com.rena.cybercraft.common.util;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class DoubleRegistryObject<PRIM extends IForgeRegistryEntry<? super PRIM>, SECOND extends IForgeRegistryEntry<? super SECOND>> {
	
	private final RegistryObject<PRIM> primary;
	private final RegistryObject<SECOND> secondary;
	
	public DoubleRegistryObject(RegistryObject<PRIM> primary, RegistryObject<SECOND> secondary) {
		super();
		this.primary = primary;
		this.secondary = secondary;
	}
	
	public PRIM getPrimary(){
		return primary.get();
	}
	
	public SECOND getSecondary() {
		return secondary.get();
	}

}
