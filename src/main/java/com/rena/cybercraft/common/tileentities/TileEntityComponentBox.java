package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityComponentBox extends LockableLootTileEntity{

    private static final int CONTAINER_SIZE = 18;

    NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    public TileEntityComponentBox() {
        super(TileEntityTypeInit.COMPONENT_BOX_TE.get());
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".component_box");
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        if (!this.tryLoadLootTable(nbt))
            nbt = ItemStackHelper.saveAllItems(nbt, this.items);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        readItems(nbt);
    }

    protected void readItems(CompoundNBT nbt) {
        if(!this.tryLoadLootTable(nbt))
            ItemStackHelper.loadAllItems(nbt, this.items);
    }
}
