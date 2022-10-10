package com.rena.cybercraft.common.container.slot;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft.EnumSlot;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SurgerySlot extends SlotItemHandler {

    public final int savedXPosition;
    public final int savedYPosition;
    public final EnumSlot slot;
    private final int index;
    private final IItemHandler playerItems;

    public SurgerySlot(IItemHandler itemHandler, IItemHandler playerItems, int index, int xPosition, int yPosition, EnumSlot slot) {
        super(itemHandler, index, xPosition, yPosition);
        savedXPosition = xPosition;
        savedYPosition = yPosition;
        this.slot = slot;
        this.index = index;
        this.playerItems = playerItems;
    }

    /*public ItemStack getPlayerStack()
    {
        return playerItems.getStackInSlot(slotNumber);
    }

    public boolean slotDiscarded()
    {
        return surgery.discardSlots[slotNumber];
    }

    public void setDiscarded(boolean dis)
    {
        surgery.discardSlots[slotNumber] = dis;
        surgery.updateEssential(slot);
        surgery.updateEssence();
    }

    @Override
    public boolean canTakeStack(EntityPlayer entityPlayer)
    {
        return surgery.canDisableItem(getStack(), slot, index % LibConstants.WARE_PER_SLOT);
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();

        surgery.updateEssence();
        surgery.markDirty();
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        ItemStack playerStack = getPlayerStack();
        if ( !getPlayerStack().isEmpty()
                && !surgery.canDisableItem(playerStack, slot, index % LibConstants.WARE_PER_SLOT) )
        {
            return false;
        }
        if ( !( !stack.isEmpty()
                && CyberwareAPI.isCyberware(stack)
                && CyberwareAPI.getCyberware(stack).getSlot(stack) == slot ) )
        {
            return false;
        }

        if (CyberwareAPI.areCyberwareStacksEqual(stack, playerStack))
        {
            int stackSize = CyberwareAPI.getCyberware(stack).installedStackSize(stack);
            if (playerStack.getCount() == stackSize) return false;
        }


        return !doesItemConflict(stack)
                && areRequirementsFulfilled(stack);
    }

    public boolean doesItemConflict(@Nonnull ItemStack stack)
    {
        return surgery.doesItemConflict(stack, slot, index % LibConstants.WARE_PER_SLOT);
    }

    public boolean areRequirementsFulfilled(@Nonnull ItemStack stack)
    {
        return surgery.areRequirementsFulfilled(stack, slot, index % LibConstants.WARE_PER_SLOT);
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack)
    {
        if ( stack.isEmpty()
                || !CyberwareAPI.isCyberware(stack) )
        {
            return 1;
        }
        ItemStack playerStack = getPlayerStack();
        int stackSize = CyberwareAPI.getCyberware(stack).installedStackSize(stack);
        if (CyberwareAPI.areCyberwareStacksEqual(playerStack, stack))
        {
            return stackSize - playerStack.getCount();
        }
        return stackSize;
    }*/
}
