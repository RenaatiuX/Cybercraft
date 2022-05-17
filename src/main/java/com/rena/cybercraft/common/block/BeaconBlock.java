package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityBeacon;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BeaconBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final VoxelShape BOUND = Block.box(1F / 16F, 0F, 1F / 16F, 15F / 16F, 4F / 16F, 15F / 16F);


    public BeaconBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(5).strength(10F, 10F).requiresCorrectToolForDrops().noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return BOUND;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityBeacon();
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

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirrorIn) {
        return blockState.rotate(mirrorIn.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING);
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos pos, Block block, BlockPos blockPos, boolean p_220069_6_) {
        super.neighborChanged(blockState, world, pos, block, blockPos, p_220069_6_);

    }
}
