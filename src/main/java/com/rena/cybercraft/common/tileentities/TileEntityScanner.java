package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ScannerContainer;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityScanner extends LockableLootTileEntity implements ITickableTileEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    public TileEntityScanner() {
        super(TileEntityTypeInit.SCANNER_TE.get());
    }

    @Override
    public void tick() {

    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".scanner");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory inv) {
        return new ScannerContainer(id, inv, this);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }
}
