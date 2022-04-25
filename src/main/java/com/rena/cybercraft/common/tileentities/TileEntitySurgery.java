package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.api.item.ICybercraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntitySurgery extends LockableTileEntity implements ITickableTileEntity
{

    public ItemStackHandler slotsPlayer = new ItemStackHandler(120);
    public ItemStackHandler slots = new ItemStackHandler(120);
    public boolean[] discardSlots = new boolean[120];
    public boolean[] isEssentialMissing = new boolean[ICybercraft.EnumSlot.values().length * 2];
    public int essence = 0;
    public int maxEssence = 0;
    public int wrongSlot = -1;
    public int ticksWrong = 0;
    public int lastEntity = -1;
    public int cooldownTicks = 0;
    public boolean missingPower = false;

    protected TileEntitySurgery(TileEntityType<?> p_i48285_1_) {
        super(p_i48285_1_);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return null;
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return null;
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    @Override
    public void tick() {

    }
}
