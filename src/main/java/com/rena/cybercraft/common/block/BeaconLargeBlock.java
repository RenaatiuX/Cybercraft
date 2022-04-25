package com.rena.cybercraft.common.block;

import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BeaconLargeBlock extends ContainerBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final AxisAlignedBB ew     = new AxisAlignedBB(  5F / 16F, 0F,   3F / 16F,  11F / 16F, 1F,  13F / 16F);
    private static final AxisAlignedBB ns     = new AxisAlignedBB(  3F / 16F, 0F,   5F / 16F,  13F / 16F, 1F,  11F / 16F);
    private static final AxisAlignedBB middle = new AxisAlignedBB(6.5F / 16F, 0F, 6.5F / 16F, 9.5F / 16F, 1F, 9.5F / 16F);

    protected BeaconLargeBlock(Properties properties) {
        super(properties);
    }



    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return null;
    }
}
