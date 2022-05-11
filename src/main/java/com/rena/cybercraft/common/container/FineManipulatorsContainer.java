package com.rena.cybercraft.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class FineManipulatorsContainer extends PlayerContainer {

    public FineManipulatorsContainer(PlayerInventory playerInventory, boolean localWorld, PlayerEntity player) {
        super(playerInventory, localWorld, player);

        slots.clear();

        //craftSlots = new CraftingInventory(this, 3, 3);

        addSlot(new CraftingResultSlot(playerInventory.player, craftSlots, resultSlots, 0, 124, 35));

        for (int indexRow = 0; indexRow < 3; indexRow++)
        {
            for (int indexColumn = 0; indexColumn < 3; indexColumn++)
            {
                addSlot(new Slot(craftSlots, indexColumn + indexRow * 3, 30 + indexColumn * 18, 17 + indexRow * 18));
            }
        }

        for (int indexRow = 0; indexRow < 3; indexRow++)
        {
            for (int indexColumn = 0; indexColumn < 9; indexColumn++)
            {
                addSlot(new Slot(playerInventory, indexColumn + (indexRow + 1) * 9, 8 + indexColumn * 18, 84 + indexRow * 18));
            }
        }

        for (int indexColumn = 0; indexColumn < 9; indexColumn++)
        {
            addSlot(new Slot(playerInventory, indexColumn, 8 + indexColumn * 18, 142));
        }
    }

    @Override
    public void removed(PlayerEntity player) {
        super.removed(player);

        for (int indexSlot = 0; indexSlot < 9; indexSlot++)
        {
            ItemStack itemstack = craftSlots.removeItemNoUpdate(indexSlot);

            if (!itemstack.isEmpty())
            {
                player.drop(itemstack, false);
            }
        }

        resultSlots.setItem(0, ItemStack.EMPTY);
    }
}
