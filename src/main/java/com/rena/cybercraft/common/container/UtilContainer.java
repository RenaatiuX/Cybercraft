package com.rena.cybercraft.common.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

public abstract class UtilContainer extends Container {

    protected PlayerInventory playerInventory;

    public UtilContainer(ContainerType<?> type, int id, PlayerInventory inv) {
        super(type, id);
        this.playerInventory = inv;
    }

    protected int addHorizontalSlots(IInventory handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected int addSlotField(IInventory handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected void addPlayerInventory(int x, int y) {
        // the Rest
        addSlotField(playerInventory, 9, x, y, 9, 18, 3, 18);
        y += 58;
        // Hotbar
        addHorizontalSlots(playerInventory, 0, x, y, 9, 18);
    }

    protected static class LockedSlot extends Slot {

        public LockedSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

    }

    /**
     * remember to write the Entity Id to the buffer before using the method
     */
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    protected static <X extends Entity> X getClientEntity(final PlayerInventory inventory,
                                                          final PacketBuffer buffer) {
        Objects.requireNonNull(inventory, "the inventory must not be null");
        Objects.requireNonNull(buffer, "the buffer must not be null");
        final Entity entity = inventory.player.level.getEntity(buffer.readVarInt());
        return (X) entity;
    }

}

