package com.rena.cybercraft.common.container;

import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;

public class EngineeringTableContainer extends BaseTeContainer<TileEntityEngineeringTable> {
    public EngineeringTableContainer(int id, PlayerInventory inv, TileEntityEngineeringTable tileEntity) {
        super(ContainerInit.ENGINEERING_TABLE_CONTAINER.get(), id, inv, tileEntity);
    }

    public EngineeringTableContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.ENGINEERING_TABLE_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    public void init() {
        addPlayerInventory(8, 84);
        addSlot(new Slot(this.tileEntity, 0, 15, 20));
        addSlot(new FilterSlot(this.tileEntity, 1, 15, 53, stack -> stack.getItem() == Items.PAPER));
        addSlotField(this.tileEntity, 2, 71, 17, 2, 18, 3, 18,
                (inv, index, x, y) -> new FilterSlot(inv, index, x, y, stack -> Tags.Items.COMPONENTS.contains(stack.getItem())));
        addSlot(new FilterSlot(this.tileEntity, 8, 115, 53, stack -> stack.getItem() instanceof BlueprintItem));
        addSlot(new LockedSlot(this.tileEntity, 9, 145, 21));
    }
}
