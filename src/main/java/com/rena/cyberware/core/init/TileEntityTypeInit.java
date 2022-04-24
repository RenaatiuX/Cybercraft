package com.rena.cyberware.core.init;

import com.rena.cyberware.Cybercraft;
import com.rena.cyberware.common.tileentities.TileEntityScanner;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static final DeferredRegister<TileEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Cybercraft.MOD_ID);

    public static final RegistryObject<TileEntityType<TileEntityScanner>> SCANNER_TE = TES.register("scanner", () -> TileEntityType.Builder.of(TileEntityScanner::new, BlockInit.SCANNER_BLOCK.get()).build(null));
}
