package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.tileentities.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

    public static final DeferredRegister<TileEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Cybercraft.MOD_ID);

    public static final RegistryObject<TileEntityType<TileEntityScanner>> SCANNER_TE = TES.register("scanner", () -> TileEntityType.Builder.of(TileEntityScanner::new, BlockInit.SCANNER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityCharger>> CHARGER_TE = TES.register("charger", () -> TileEntityType.Builder.of(TileEntityCharger::new, BlockInit.CHARGER_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityBlueprintArchive>> BLUEPRINTER_ARCHIVE_TE = TES.register("blueprint_archive", () -> TileEntityType.Builder.of(TileEntityBlueprintArchive::new, BlockInit.BLUEPRINT_ARCHIVE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityEngineeringTable>> ENGINEERING_TABLE = TES.register("engineering", () -> TileEntityType.Builder.of(TileEntityEngineeringTable::new, BlockInit.ENGINEERING_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityBeacon>> BEACON_TE = TES.register("beacon", () -> TileEntityType.Builder.of(TileEntityBeacon::new, BlockInit.RADIO.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityComponentBox>> COMPONENT_BOX_TE = TES.register("component_box", () -> TileEntityType.Builder.of(TileEntityComponentBox::new, BlockInit.COMPONENT_BOX.getPrimary()).build(null));
    public static final RegistryObject<TileEntityType<TileEntityBeaconPost>> RADIO_TOWER = TES.register("radio_tower",
            () -> TileEntityType.Builder.of(TileEntityBeaconPost::new, BlockInit.RADIO_POST.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntitySurgery>> SURGERY = TES.register("surgery",
            () -> TileEntityType.Builder.of(TileEntitySurgery::new, BlockInit.SURGERY.get()).build(null));
    public static final RegistryObject<TileEntityType<TileEntitySurgeryChamber>> SURGERY_CHAMBER = TES.register("surgery_chamber",
            () -> TileEntityType.Builder.of(TileEntitySurgeryChamber::new, BlockInit.SURGERY_CHAMBER_BLOCK.get()).build(null));
}
