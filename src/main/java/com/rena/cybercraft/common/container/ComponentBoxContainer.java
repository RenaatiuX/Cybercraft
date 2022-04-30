package com.rena.cybercraft.common.container;

import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ComponentBoxContainer extends BaseTeContainer<TileEntityComponentBox>{
    public ComponentBoxContainer(int id, PlayerInventory inv, TileEntityComponentBox tileEntity) {
        super(ContainerInit.COMPONENT_BOX_CONTAINER.get(), id, inv, tileEntity);
    }

    public ComponentBoxContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.COMPONENT_BOX_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    public void init() {
        addSlotField(tileEntity, 0, 8, 18, 9, 18, 6, 18);
        addPlayerInventory(8, 140);
    }
}
