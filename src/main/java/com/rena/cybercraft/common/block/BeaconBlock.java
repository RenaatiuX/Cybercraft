package com.rena.cybercraft.common.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BeaconBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final AxisAlignedBB bound = new AxisAlignedBB(1F / 16F, 0F, 1F / 16F, 15F / 16F, 4F / 16F, 15F / 16F);


    protected BeaconBlock(Properties properties) {
        super(properties);
    }


    public static AxisAlignedBB getBound() {
        return bound;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }
}
