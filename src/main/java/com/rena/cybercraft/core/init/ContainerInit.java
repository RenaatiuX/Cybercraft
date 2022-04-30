package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.BlueprintArchiveContainer;
import com.rena.cybercraft.common.container.ComponentBoxContainer;
import com.rena.cybercraft.common.container.ScannerContainer;
import net.minecraft.inventory.container.BlastFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Cybercraft.MOD_ID);

    public static final RegistryObject<ContainerType<ScannerContainer>> SCANNER_CONTAINER = CONTAINERS.register("scanner", () -> IForgeContainerType.create(ScannerContainer::new));
    public static final RegistryObject<ContainerType<ComponentBoxContainer>> COMPONENT_BOX_CONTAINER = CONTAINERS.register("component_box", () -> IForgeContainerType.create(ComponentBoxContainer::new));
    public static final RegistryObject<ContainerType<BlueprintArchiveContainer>> BLUEPRINT_ARCHIVE_CONTAINER = CONTAINERS.register("blueprinter_archive", () -> IForgeContainerType.create(BlueprintArchiveContainer::new));

}
