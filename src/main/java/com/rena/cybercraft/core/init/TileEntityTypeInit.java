package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.tileentities.TileEntityBlueprintArchive;
import com.rena.cybercraft.common.tileentities.TileEntityCharger;
import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import com.rena.cybercraft.common.tileentities.TileEntityScanner;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static final DeferredRegister<TileEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Cybercraft.MOD_ID);

    public static final RegistryObject<TileEntityType<TileEntityScanner>> SCANNER_TE = TES.register("scanner", () -> TileEntityType.Builder.of(TileEntityScanner::new, BlockInit.SCANNER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityCharger>> CHARGER_TE = TES.register("charger", () -> TileEntityType.Builder.of(TileEntityCharger::new, BlockInit.CHARGER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityComponentBox>> COMPONENT_BOX_TE = TES.register("component_box", () -> TileEntityType.Builder.of(TileEntityComponentBox::new, BlockInit.COMPONENT_BOX.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityBlueprintArchive>> BLUEPRINTER_ARCHIVE_TE = TES.register("blueprinter_archive", () -> TileEntityType.Builder.of(TileEntityBlueprintArchive::new, BlockInit.BLUEPRINTER_ARCHIVE.get()).build(null));


}
