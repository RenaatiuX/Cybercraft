package com.rena.cybercraft.common.container;

import com.google.common.collect.Lists;
import com.rena.cybercraft.common.item.BlueprintItem;
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
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;

import java.util.LinkedList;

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
        addSlot(new ResultSLot(this.tileEntity, 9, 145, 21));
    }

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack lvt_3_1_ = ItemStack.EMPTY;
        Slot lvt_4_1_ = (Slot) this.slots.get(p_82846_2_);
        if (lvt_4_1_ != null && lvt_4_1_.hasItem()) {
            ItemStack lvt_5_1_ = lvt_4_1_.getItem();
            lvt_3_1_ = lvt_5_1_.copy();
            if (p_82846_2_ == 0) {
                IWorldPosCallable.NULL.execute((p_217067_2_, p_217067_3_) -> {
                    lvt_5_1_.getItem().onCraftedBy(lvt_5_1_, p_217067_2_, p_82846_1_);
                });
                if (!this.moveItemStackTo(lvt_5_1_, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                lvt_4_1_.onQuickCraft(lvt_5_1_, lvt_3_1_);
            } else if (p_82846_2_ >= 10 && p_82846_2_ < 46) {
                if (!this.moveItemStackTo(lvt_5_1_, 1, 10, false)) {
                    if (p_82846_2_ < 37) {
                        if (!this.moveItemStackTo(lvt_5_1_, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(lvt_5_1_, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(lvt_5_1_, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (lvt_5_1_.isEmpty()) {
                lvt_4_1_.set(ItemStack.EMPTY);
            } else {
                lvt_4_1_.setChanged();
            }

            if (lvt_5_1_.getCount() == lvt_3_1_.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack lvt_6_1_ = lvt_4_1_.onTake(p_82846_1_, lvt_5_1_);
            if (p_82846_2_ == 0) {
                p_82846_1_.drop(lvt_6_1_, false);
            }
        }

        return lvt_3_1_;
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
        }

        @Override
        public ItemStack onTake(PlayerEntity player, ItemStack stack) {
            onQuickCraft(stack, 1);
            return super.onTake(player, stack);
        }
    }
}
