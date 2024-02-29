package com.rena.cybercraft.common.container;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SurgeryContainer extends UtilContainer {

    private final TileEntitySurgery surgery;

    public SurgeryContainer(int id, PlayerInventory playerinv, TileEntitySurgery surgery) {
        super(ContainerInit.SURGERY_CONTAINER.get(), id, playerinv);
        this.surgery = surgery;
        init();
    }

    /**
     * just client side
     *
     * @param id
     * @param playerInventory
     * @param buffer          dont forget to write the block pos of the tileentity on the buffer in NetworkHooks
     */
    public SurgeryContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, getClientTileEntity(playerInventory, buffer));
    }

    public void init() {
        addPlayerInventory(8, 140);


        int indexContainerSlot = 0;
        for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
            for (int indexCyberwareSlot = 0; indexCyberwareSlot < 8; indexCyberwareSlot++) {
                addSlot(new SlotSurgery(surgery.slots, surgery.slotsPlayer, indexContainerSlot, 9 + 20 * indexCyberwareSlot, 109, slot));
                indexContainerSlot++;
            }
            for (int indexCyberwareSlot = 0; indexCyberwareSlot < LibConstants.WARE_PER_SLOT - 8; indexCyberwareSlot++) {
                addSlot(new SlotSurgery(surgery.slots, surgery.slotsPlayer, indexContainerSlot, Integer.MIN_VALUE, Integer.MIN_VALUE, slot));
                indexContainerSlot++;
            }
        }
    }

    public TileEntitySurgery getSurgery() {
        return surgery;
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return surgery.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (!(slot instanceof SlotSurgery)) {
                if (index >= 3 && index < 30) {
                    if (!moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerEntity, itemstack1);
        }

        return itemstack;
    }

    public class SlotSurgery extends SlotItemHandler {
        public final int savedXPosition;
        public final int savedYPosition;
        public final ICybercraft.EnumSlot slot;
        private final int indexSurgery;
        private IItemHandler playerItems;
        private boolean visible = false;

        public SlotSurgery(IItemHandler itemHandler, IItemHandler playerItems, int indexSurgery, int xPosition, int yPosition, ICybercraft.EnumSlot slot) {
            super(itemHandler, indexSurgery, xPosition, yPosition);
            savedXPosition = xPosition;
            savedYPosition = yPosition;
            this.slot = slot;
            this.indexSurgery = indexSurgery;
            this.playerItems = playerItems;
        }

        public ItemStack getPlayerStack() {
            return playerItems.getStackInSlot(indexSurgery);
        }

        public boolean slotDiscarded() {
            return surgery.discardSlots[indexSurgery];
        }

        public void setDiscarded(boolean dis) {
            surgery.discardSlots[this.getSlotIndex()] = dis;
            surgery.updateEssential(slot);
            surgery.updateEssence();
        }

        @Override
        public boolean mayPickup(PlayerEntity playerIn) {
            return surgery.canDisableItem(this.getItem(), slot, indexSurgery % LibConstants.WARE_PER_SLOT);
        }


        @Override
        public void setChanged() {
            super.setChanged();
            surgery.updateEssence();
            surgery.setChanged();
        }

        @Override
        public void set(@Nonnull ItemStack stack) {
            if (mayPlace(stack)) {
                surgery.disableDependants(getPlayerStack(), slot, indexSurgery % LibConstants.WARE_PER_SLOT);
                super.set(stack);
            }
            surgery.setChanged();
            surgery.updateEssential(slot);
            surgery.updateEssence();
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public boolean isActive() {
            return this.visible;
        }

        /*@Override
        public ItemStack onTake(PlayerEntity player, ItemStack stack) {
            ItemStack result = super.onTake(player, stack);
            surgery.setChanged();
            surgery.updateEssential(slot);
            surgery.updateEssence();
            return result;
        }*/

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            ItemStack playerStack = getPlayerStack();
            if (!getPlayerStack().isEmpty()
                    && !surgery.canDisableItem(playerStack, slot, indexSurgery % LibConstants.WARE_PER_SLOT)) {
                return false;
            }
            if (!(!stack.isEmpty() && CybercraftAPI.isCybercraft(stack) && CybercraftAPI.getCybercraft(stack).getSlot(stack) == slot)) {
                return false;
            }

            if (CybercraftAPI.areCybercraftStacksEqual(stack, playerStack)) {
                int stackSize = CybercraftAPI.getCybercraft(stack).installedStackSize(stack);
                if (playerStack.getCount() == stackSize) return false;
            }


            return !doesItemConflict(stack) && areRequirementsFulfilled(stack);
        }

        public boolean doesItemConflict(@Nonnull ItemStack stack) {
            return surgery.doesItemConflict(stack, slot, indexSurgery % LibConstants.WARE_PER_SLOT);
        }

        public boolean areRequirementsFulfilled(@Nonnull ItemStack stack) {
            return surgery.areRequirementsFulfilled(stack, slot, indexSurgery % LibConstants.WARE_PER_SLOT);
        }


        @Override
        public int getMaxStackSize(@Nonnull ItemStack stack) {
            if (stack.isEmpty() || !CybercraftAPI.isCybercraft(stack)) {
                return 1;
            }
            ItemStack playerStack = getPlayerStack();
            int stackSize = CybercraftAPI.getCybercraft(stack).installedStackSize(stack);
            if (CybercraftAPI.areCybercraftStacksEqual(playerStack, stack)) {
                return stackSize - playerStack.getCount();
            }
            return stackSize;
        }
    }
}
