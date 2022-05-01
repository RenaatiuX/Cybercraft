package com.rena.cybercraft.common.container;

import com.rena.cybercraft.common.tileentities.TileEntityBlueprintArchive;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class BlueprintArchiveContainer extends BaseTeContainer<TileEntityBlueprintArchive>{
    public BlueprintArchiveContainer(int id, PlayerInventory inv, TileEntityBlueprintArchive tileEntity) {
        super(ContainerInit.BLUEPRINT_ARCHIVE_CONTAINER.get(), id, inv, tileEntity);
    }

    public BlueprintArchiveContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.BLUEPRINT_ARCHIVE_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    public void init() {
        addSlotField(tileEntity, 0, 8, 18, 9, 18, 2, 18, ComponentsSlot::new);
        addPlayerInventory(8, 68);
    }

    private static final class ComponentsSlot extends Slot {

        public ComponentsSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return Tags.Items.BLUPRINT_ITEMS.contains(stack.getItem());
        }
    }
}
