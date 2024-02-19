package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityBeacon;
import com.rena.cybercraft.common.tileentities.util.IRadioTower;
import com.rena.cybercraft.common.util.WorldUtil;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BeaconBlock extends HorizontalBlock {

    private static final VoxelShape BOUND = Block.box(1F, 0F, 1F / 16F, 15F, 4F, 15F);

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
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
        BlockState down = reader.getBlockState(pos.below());
        return Block.isFaceFull(down.getShape(reader, pos.below()), Direction.UP);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected static int getTier(IWorld world, BlockPos pos) {
        TileEntity radio = world.getBlockEntity(pos.below());
        int tier;
        if (radio == null)
            tier = 0;
        else {
            tier = radio instanceof IRadioTower ? ((IRadioTower) radio).getTier(pos.below()) : 0;
        }
        return tier;
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity living, ItemStack stack) {
        TileEntityBeacon te = WorldUtil.getTileEntity(TileEntityBeacon.class, world, pos);
        if (te != null) {
            te.setTier(getTier(world, pos));
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (!canSurvive(state, world, pos))
            return Blocks.AIR.defaultBlockState();
        TileEntityBeacon te = WorldUtil.getTileEntity(TileEntityBeacon.class, world, pos);
        if (te != null) {
            te.setTier(getTier(world, pos));
        }
        return state;
    }
}
