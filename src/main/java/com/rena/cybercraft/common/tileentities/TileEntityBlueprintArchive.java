package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.item.IBlueprint;
import com.rena.cybercraft.common.container.BlueprintArchiveContainer;
import com.rena.cybercraft.common.container.ComponentBoxContainer;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBlueprintArchive extends LockableLootTileEntity {

    private static final int CONTAINER_SIZE = 18;

    NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    public TileEntityBlueprintArchive() {
        super(TileEntityTypeInit.BLUEPRINTER_ARCHIVE_TE.get());
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
        return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".blueprint_archive");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory inv) {
        return new BlueprintArchiveContainer(id, inv, this);
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
