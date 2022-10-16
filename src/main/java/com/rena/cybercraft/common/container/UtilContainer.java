package com.rena.cybercraft.common.container;

import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.lwjgl.system.CallbackI;

import java.util.Objects;
import java.util.function.Predicate;

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

    protected int addHorizontalSlots(IInventory handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots, ISlotProvider provider) {
        for (int i = 0; i < amount; i++) {
            addSlot(provider.createSlot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }
    protected int addHorizontalSlots(IItemHandler handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots, IItemHandlerSlotProvider provider) {
        for (int i = 0; i < amount; i++) {
            addSlot(provider.createSlot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected int addHorizontalSlots(IItemHandler handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots) {
     return addHorizontalSlots(handler, Index, x, y, amount, distanceBetweenSlots, (inv, index, lambdaX, lambdaY) -> new SlotItemHandler(inv, index, lambdaX, lambdaY));
    }

    protected int addSlotField(IInventory handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected int addSlotField(IInventory handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance, ISlotProvider provider) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance, provider);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected int addSlotField(IItemHandler handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance, IItemHandlerSlotProvider provider) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance, provider);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected int addSlotField(IItemHandler handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance) {
       return addSlotField(handler, StartIndex, x, y, horizontalAmount, horizontalDistance, verticalAmount, VerticalDistance, (inv, index, lX, lY) -> new SlotItemHandler(inv, index, lX, lY));
    }

    protected void addPlayerInventory(int x, int y) {
        // the Rest
        addSlotField(playerInventory, 9, x, y, 9, 18, 3, 18);
        y += 58;
        // Hotbar
        addHorizontalSlots(playerInventory, 0, x, y, 9, 18);
    }

    public static interface ISlotProvider{
        Slot createSlot(IInventory inv,int index, int x, int y);
    }

    public interface IItemHandlerSlotProvider{
        Slot createSlot(IItemHandler inv, int index, int x, int y);
    }

    protected static class FilterSlot extends Slot{

        private final Predicate<ItemStack> filter;
        public FilterSlot(IInventory inv, int index, int x, int y, Predicate<ItemStack> filter) {
            super(inv, index, x, y);
            this.filter = filter;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return filter.test(stack);
        }
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

    /**
     * remeber to write the block Pos of the TileEntity in {@code NetworkHooks#OpenGui}
     * @param inventory
     * @param buffer
     * @param <X>
     * @return
     */
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    protected static <X extends TileEntity> X getClientTileEntity(final PlayerInventory inventory,
                                                                         final PacketBuffer buffer) {
        Objects.requireNonNull(inventory, "the inventory must not be null");
        Objects.requireNonNull(buffer, "the buffer must not be null");
        final TileEntity entity = inventory.player.level.getBlockEntity(buffer.readBlockPos());
        return (X) entity;
    }

}

