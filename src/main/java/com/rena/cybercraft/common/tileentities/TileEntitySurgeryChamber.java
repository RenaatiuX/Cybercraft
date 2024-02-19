package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntitySurgeryChamber extends TileEntity {

    public boolean lastOpen;
    public float openTicks;

    public TileEntitySurgeryChamber() {
        super(TileEntityTypeInit.SURGERY_CHAMBER.get());
    }

    @Override
    public TileEntityType<?> getType() {
        return TileEntityTypeInit.SURGERY_CHAMBER.get();
    }
}
