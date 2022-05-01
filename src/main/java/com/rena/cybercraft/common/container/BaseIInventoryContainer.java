package com.rena.cybercraft.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class BaseIInventoryContainer extends UtilContainer{
    protected final IItemHandler inventory;

    public BaseIInventoryContainer(ContainerType<?> type, int id, PlayerInventory inv, IItemHandler inventory) {
        super(type, id, inv);
        this.inventory = inventory;
        init();
    }

    /**
     * dont forget to write the inventorySize to the buffer
     */
    public BaseIInventoryContainer(ContainerType<?> type, int id, PlayerInventory inv, PacketBuffer buffer){
        this(type, id, inv, new ItemStackHandler(buffer.readVarInt()));
    }

    protected abstract void init();

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }
}
