package com.rena.cybercraft.common.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class DummyTileEntity extends TileEntity {
    private final Function<BlockPos, BlockPos> masterPos;

    public DummyTileEntity(TileEntityType<?> type, Function<BlockPos, BlockPos> getMasterPos) {
        super(type);
        this.masterPos = getMasterPos;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        TileEntity masterTe = this.level.getBlockEntity(masterPos.apply(this.getBlockPos()));
        if (masterTe != null)
            return masterTe.getCapability(cap, side);
        return super.getCapability(cap, side);
    }
}
