package com.rena.cybercraft.common.container;

import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemComponentBoxContainer extends BaseIInventoryContainer{
    public ItemComponentBoxContainer(int id, PlayerInventory inv, IItemHandler inventory) {
        super(ContainerInit.ITEM_COMPONENT_BOX_CONTAINER.get(), id, inv, inventory);
    }

    public ItemComponentBoxContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.ITEM_COMPONENT_BOX_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    protected void init() {

    }
}
