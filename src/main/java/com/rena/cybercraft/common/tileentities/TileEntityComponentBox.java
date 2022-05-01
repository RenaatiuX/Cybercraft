package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ComponentBoxContainer;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityComponentBox extends LockableLootTileEntity{

    public static final int CONTAINER_SIZE = 18;

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
    protected Container createMenu(int id, PlayerInventory inv) {
        return new ComponentBoxContainer(id, inv, this);
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt = saveItems(nbt);
        return super.save(nbt);
    }

    public CompoundNBT saveItems(CompoundNBT nbt){
        if (!this.tryLoadLootTable(nbt))
            nbt = ItemStackHelper.saveAllItems(nbt, this.items);
        return nbt;
    }

    /**
     * serializes the items like forge does
     * @return
     */
    public CompoundNBT serializeNBT()
    {
        ListNBT nbtTagList = new ListNBT();
        for (int i = 0; i < items.size(); i++)
        {
            if (!items.get(i).isEmpty())
            {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt("Slot", i);
                items.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", items.size());
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        readItems(nbt);
    }

    public void readItems(CompoundNBT nbt) {
        if(!this.tryLoadLootTable(nbt))
            ItemStackHelper.loadAllItems(nbt, this.items);
    }
}
