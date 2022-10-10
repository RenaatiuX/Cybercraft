package com.rena.cybercraft.common.container;

import com.rena.cybercraft.common.container.slot.SurgerySlot;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SurgeryContainer extends Container {

    private final TileEntitySurgery surgery;
    protected SurgeryContainer(@Nullable ContainerType<?> p_i50105_1_, int p_i50105_2_, TileEntitySurgery surgery) {
        super(p_i50105_1_, p_i50105_2_);
        this.surgery = surgery;
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return false;//surgery.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if ( slot != null
                && slot.hasItem() )
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (!(slot instanceof SurgerySlot))
            {
                if ( index >= 3
                        && index < 30 )
                {
                    if (!moveItemStackTo(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if ( index >= 30
                        && index < 39
                        && !moveItemStackTo(itemstack1, 3, 30, false) )
                {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.getCount() == 0)
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerEntity, itemstack1);
        }

        return itemstack;
    }
}
