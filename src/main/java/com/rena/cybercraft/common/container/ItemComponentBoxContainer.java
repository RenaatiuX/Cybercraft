package com.rena.cybercraft.common.container;

import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemComponentBoxContainer extends BaseIInventoryContainer{
    public ItemComponentBoxContainer(int id, PlayerInventory inv, IItemHandler inventory) {
        super(ContainerInit.ITEM_COMPONENT_BOX_CONTAINER.get(), id, inv, inventory);
    }

    public ItemComponentBoxContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.ITEM_COMPONENT_BOX_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    protected void init() {
        addSlotField(inventory, 0, 8, 18, 9, 18, 2, 18, (inv, index, x, y) -> new ComponentsSlot(inv, index, x, y));
        addPlayerInventory(8, 68);
    }

    private static final class ComponentsSlot extends SlotItemHandler {

        public ComponentsSlot(IItemHandler p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return Tags.Items.COMPONENTS.contains(stack.getItem());
        }
    }
}
