package com.rena.cybercraft.common.container;

import com.google.common.collect.Lists;
import com.rena.cybercraft.api.CybercraftSurgeryEvent;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.item.block.ItemComponentBox;
import com.rena.cybercraft.common.tileentities.MergeInventory;
import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class EngineeringTableContainer extends UtilContainer {

    protected final TileEntityEngineeringTable tileEntity;
    protected final MergeInventory inv;
    protected final IWorldPosCallable canInteractWithCallable;
    private boolean hasComponentInventory = false, playerInvComponentBox = false, teComponentBox = false;

    public EngineeringTableContainer(int id, PlayerInventory inv, TileEntityEngineeringTable tileEntity) {
        super(ContainerInit.ENGINEERING_TABLE_CONTAINER.get(), id, inv);
        this.tileEntity = tileEntity;
        this.inv = tileEntity.getMergeInventory();
        this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
        init();
    }

    public EngineeringTableContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        this(id, inv, getClientTileEntity(inv, buffer));
    }

    public void init() {
        addPlayerInventory(8, 84);
        addSlot(new Slot(this.tileEntity, 0, 15, 20));
        addSlot(new FilterSlot(this.tileEntity, 1, 15, 53, stack -> stack.getItem() == Items.PAPER));
        addSlotField(this.tileEntity, 2, 71, 17, 2, 18, 3, 18,
                (inv, index, x, y) -> new FilterSlot(inv, index, x, y, stack -> Tags.Items.COMPONENTS.contains(stack.getItem())));
        addSlot(new FilterSlot(this.tileEntity, 8, 115, 53, stack -> stack.getItem() instanceof BlueprintItem));
        addSlot(new ResultSLot(this.tileEntity, 9, 145, 21));
        if (tileEntity.hasComponentInventory()) {
            makeComponentInventory(tileEntity.getComponentBoxInv(), inv -> this.teComponentBox);
            hasComponentInventory = true;
            teComponentBox = true;
        } else if (findComponentBoxInvInPlayer() != null) {
            makeComponentInventory(findComponentBoxInvInPlayer(), inv -> this.playerInvComponentBox);
            hasComponentInventory = true;
            playerInvComponentBox = true;
        }
        addDataSlots(getData());
    }

    @Override
    public void broadcastChanges() {
        if (tileEntity.hasComponentInventory() != teComponentBox){
            if (tileEntity.hasComponentInventory()){
                makeComponentInventory(tileEntity.getComponentBoxInv(), inv -> this.teComponentBox);
                hasComponentInventory = true;
                teComponentBox = true;
            }else{
                teComponentBox = false;
                hasComponentInventory = false;
                if(findComponentBoxInvInPlayer() != null){
                    makeComponentInventory(findComponentBoxInvInPlayer(), inv -> this.playerInvComponentBox);
                    hasComponentInventory = true;
                    playerInvComponentBox = true;
                }
            }

        }else if (!teComponentBox && (findComponentBoxInvInPlayer() != null) != playerInvComponentBox) {
            if (findComponentBoxInvInPlayer() != null) {
                makeComponentInventory(findComponentBoxInvInPlayer(), inv -> this.playerInvComponentBox);
                hasComponentInventory = true;
                playerInvComponentBox = true;
            } else {
                playerInvComponentBox = false;
                hasComponentInventory = false;
            }

        }
        super.broadcastChanges();
    }

    private void makeComponentInventory(IItemHandler handler, Predicate<IItemHandler> isEnabled) {
        addSlotField(handler, 0, 181, 22, 3, 18, 6, 18,
                (inv, index, x, y) -> new ComponentEnabledSlot(inv, index, x, y, isEnabled));
    }

    private IItemHandler findComponentBoxInvInPlayer() {
        for (int i = 0; i < playerInventory.getContainerSize(); i++) {
            if (playerInventory.getItem(i).getItem() instanceof ItemComponentBox && playerInventory.getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                return playerInventory.getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
            }
        }
        return null;
    }

    public boolean isHasComponentInventory() {
        return hasComponentInventory;
    }

    public ItemStack quickMoveStack(PlayerEntity player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotIndex == 0) {
                this.canInteractWithCallable.execute((p_217067_2_, p_217067_3_) -> {
                    itemstack1.getItem().onCraftedBy(itemstack1, p_217067_2_, player);
                });
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (slotIndex >= 10 && slotIndex < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (slotIndex < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(player, itemstack1);
            if (slotIndex == 0) {
                player.drop(itemstack2, false);
            }
        }

        return itemstack;
    }

    public TileEntityEngineeringTable getTileEntity() {
        return tileEntity;
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    protected static <X extends TileEntity> X getClientTileEntity(final PlayerInventory inventory,
                                                                  final PacketBuffer buffer) {
        Objects.requireNonNull(inventory, "the inventory must not be null");
        Objects.requireNonNull(buffer, "the buffer must not be null");
        final TileEntity tileEntity = inventory.player.level.getBlockEntity(buffer.readBlockPos());
        return (X) tileEntity;
    }

    public static class ComponentEnabledSlot extends ComponentBoxContainer.ComponentsItemHandlerSlot{

        private final Predicate<IItemHandler> enabled;

        public ComponentEnabledSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<IItemHandler> enabled) {
            super(itemHandler, index, xPosition, yPosition);
            this.enabled = enabled;
        }

        @Override
        public boolean isActive() {
            return enabled.test(this.getItemHandler());
        }
    }

    private static final class ResultSLot extends LockedSlot {

        private final TileEntityEngineeringTable inv;

        public ResultSLot(TileEntityEngineeringTable inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
            this.inv = inventoryIn;
        }

        @Override
        protected void onQuickCraft(ItemStack stack, int count) {
            for (int k = 0; k < count; k++) {
                reduceItems();
            }
        }


        private void reduceItems() {
            NonNullList<ItemStack> comps = NNLUtil.deepCopyList(inv.getBlueprintRecipe().getComponents());
            for (int i = 0; i < inv.getContainerSize(); i++) {
                for (ItemStack comp : comps) {
                    if (!comp.isEmpty() && inv.getItem(i).getItem() == comp.getItem()) {
                        if (inv.getItem(i).getCount() >= comp.getCount()) {
                            inv.getItem(i).shrink(comp.getCount());
                        } else {
                            comp.shrink(inv.getItem(i).getCount());
                            inv.getItem(i).shrink(inv.getItem(i).getCount());
                        }
                        break;
                    }
                }
            }
        }

        @Override
        public ItemStack onTake(PlayerEntity player, ItemStack stack) {
            reduceItems();
            return super.onTake(player, stack);
        }
    }

    private final IIntArray getData() {
        return new IIntArray() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return hasComponentInventory ? 2 : 1;
                    case 1:
                        return teComponentBox ? 2 : 1;
                    case 2:
                        return playerInvComponentBox ? 2 : 1;
                }

                return 0;
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0:
                        hasComponentInventory = value == 2;
                        break;
                    case 1:
                        teComponentBox = value == 2;
                        break;
                    case 2:
                        playerInvComponentBox = value == 2;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }
}
