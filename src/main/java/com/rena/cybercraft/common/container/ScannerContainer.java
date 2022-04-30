package com.rena.cybercraft.common.container;

import com.rena.cybercraft.common.container.util.BetterRefrenceHolder;
import com.rena.cybercraft.common.tileentities.TileEntityScanner;
import com.rena.cybercraft.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

public class ScannerContainer extends BaseTeContainer<TileEntityScanner> {
    public ScannerContainer(int id, PlayerInventory inv, TileEntityScanner tileEntity) {
        super(ContainerInit.SCANNER_CONTAINER.get(), id, inv, tileEntity);
    }

    public ScannerContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.SCANNER_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    public void init() {
        addPlayerInventory(8, 84);
        addSlot(new Slot(tileEntity, 0, 15, 53));
        addSlot(new Slot(tileEntity, 1, 35, 53));
        addSlot(new LockedSlot(tileEntity, 2, 140, 56));

        track();
    }

    public int getCounterPercentage(){
        return tileEntity.getCounterPercentage();
    }

    private void track(){
        addDataSlot(new BetterRefrenceHolder() {
            @Override
            public int get() {
                return tileEntity.getCounterPercentage();
            }

            @Override
            public void set(int value) {
                tileEntity.setCounterPercentage(value);
            }
        });
    }
}
