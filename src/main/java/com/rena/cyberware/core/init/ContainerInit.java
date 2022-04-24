package com.rena.cyberware.core.init;

import com.rena.cyberware.Cybercraft;
import com.rena.cyberware.common.container.ScannerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Cybercraft.MOD_ID);

    public static final RegistryObject<ContainerType<ScannerContainer>> SCANNER_CONTAINER = CONTAINERS.register("scanner", () -> IForgeContainerType.create(ScannerContainer::new));
}
