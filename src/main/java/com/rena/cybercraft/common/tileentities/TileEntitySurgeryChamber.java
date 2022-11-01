package com.rena.cybercraft.common.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntitySurgeryChamber extends TileEntity {

    public boolean lastOpen;
    public float openTicks;

    public TileEntitySurgeryChamber(TileEntityType<?> type) {
        super(type);
    }

}
