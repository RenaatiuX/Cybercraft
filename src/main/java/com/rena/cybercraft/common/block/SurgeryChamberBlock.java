package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class SurgeryChamberBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public SurgeryChamberBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(5f, 10f));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    private static final VoxelShape top    = Block.box(       0F, 15F / 16F,        0F,       1F,       1F,       1F);
    private static final VoxelShape south  = Block.box(       0F,        0F,        0F,       1F,       1F, 1F / 16F);
    private static final VoxelShape north  = Block.box(       0F,        0F, 15F / 16F,       1F,       1F,       1F);
    private static final VoxelShape east   = Block.box(       0F,        0F,        0F, 1F / 16F,       1F,       1F);
    private static final VoxelShape west   = Block.box(15F / 16F,        0F,        0F,       1F,       1F,       1F);
    private static final VoxelShape bottom = Block.box(       0F,        0F,        0F,       1F, 1F / 16F,       1F);


    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        boolean top = blockState.getValue(HALF) == DoubleBlockHalf.UPPER;
        if (canOpen(top ? pos : pos.above(), world))
        {
            toggleDoor(top, blockState, pos, world);

            notifySurgeon(top ? pos : pos.above(), world);
        }

        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    public void toggleDoor(boolean top, BlockState blockState, BlockPos pos, World worldIn)
    {
        BlockState blockStateNew = blockState.cycle(OPEN);
        worldIn.setBlock(pos, blockStateNew, 2);

        BlockPos otherPos = pos.above();
        if (top) {
            otherPos = pos.below();
        }
        BlockState otherState = worldIn.getBlockState(otherPos);

        if (otherState.getBlock() == this) {
            otherState = otherState.cycle(OPEN);
            worldIn.setBlock(otherPos, otherState, 2);
        }
    }

    private boolean canOpen(BlockPos pos, World worldIn)
    {
        TileEntity above = worldIn.getBlockEntity(pos.above());

        if (above instanceof TileEntitySurgery)
        {
            return ((TileEntitySurgery) above).canOpen();
        }
        return true;
    }


    private void notifySurgeon(BlockPos pos, World worldIn)
    {
        TileEntity above = worldIn.getBlockEntity(pos.above());

        if (above instanceof TileEntitySurgery)
        {
            ((TileEntitySurgery) above).notifyChange();
        }
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {

        super.neighborChanged(p_220069_1_, p_220069_2_, p_220069_3_, p_220069_4_, p_220069_5_, p_220069_6_);
    }

    @Override
    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = worldReader.getBlockState(blockpos);

        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldReader, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING, OPEN, HALF);
    }
}
