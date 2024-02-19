package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.common.tileentities.TileEntitySurgeryChamber;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SurgeryChamberBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public SurgeryChamberBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(5f, 10f));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    private static final VoxelShape top = Block.box(0F, 15F / 16F, 0F, 1F, 1F, 1F);
    private static final VoxelShape south = Block.box(0F, 0F, 0F, 1F, 1F, 1F / 16F);
    private static final VoxelShape north = Block.box(0F, 0F, 15F / 16F, 1F, 1F, 1F);
    private static final VoxelShape east = Block.box(0F, 0F, 0F, 1F / 16F, 1F, 1F);
    private static final VoxelShape west = Block.box(15F / 16F, 0F, 0F, 1F, 1F, 1F);
    private static final VoxelShape bottom = Block.box(0F, 0F, 0F, 1F, 1F / 16F, 1F);

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        Direction face = p_220071_1_.getValue(FACING);
        boolean open = p_220071_1_.getValue(OPEN);
        VoxelShape shape = VoxelShapes.empty();
        if (p_220071_1_.getValue(HALF) == DoubleBlockHalf.UPPER) {
            shape = VoxelShapes.or(shape, top);
            if (!open || face != Direction.SOUTH) {
                shape = VoxelShapes.or(shape, south);
            }
            if (!open || face != Direction.NORTH) {
                shape = VoxelShapes.or(shape, north);
            }
            if (!open || face != Direction.EAST) {
                shape = VoxelShapes.or(shape, east);
            }
            if (!open || face != Direction.WEST) {
                shape = VoxelShapes.or(shape, west);
            }
        } else {
            shape = VoxelShapes.or(shape, bottom);
            if (!open || face != Direction.SOUTH) {
                shape = VoxelShapes.or(shape, south);
            }
            if (!open || face != Direction.NORTH) {
                shape = VoxelShapes.or(shape, north);
            }
            if (!open || face != Direction.EAST) {
                shape = VoxelShapes.or(shape, east);
            }
            if (!open || face != Direction.WEST) {
                shape = VoxelShapes.or(shape, west);
            }
        }
        return shape;

    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        boolean top = blockState.getValue(HALF) == DoubleBlockHalf.UPPER;
        if (canOpen(top ? pos : pos.above(), world)) {
            toggleDoor(top, blockState, pos, world);
            notifySurgeon(top ? pos : pos.above(), world);
        }

        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    public void toggleDoor(boolean top, BlockState blockState, BlockPos pos, World worldIn) {
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

    private boolean canOpen(BlockPos pos, World worldIn) {
        TileEntity above = worldIn.getBlockEntity(pos.above());

        if (above instanceof TileEntitySurgery) {
            return ((TileEntitySurgery) above).canOpen();
        }
        return true;
    }


    private void notifySurgeon(BlockPos pos, World worldIn) {
        TileEntity above = worldIn.getBlockEntity(pos.above());

        if (above instanceof TileEntitySurgery) {
            ((TileEntitySurgery) above).notifyChange();
        }
    }

    @Override
    public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        p_180633_1_.setBlock(p_180633_2_.above(), p_180633_3_.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
        if (p_220069_1_.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockPos pos = p_220069_3_.below();
            BlockState blockState = p_220069_2_.getBlockState(pos);
            if (blockState.getBlock() != this) {
                p_220069_2_.isEmptyBlock(pos);
            } else if (p_220069_4_ != this) {
                blockState.neighborChanged(p_220069_2_, p_220069_3_, p_220069_4_, p_220069_5_, p_220069_6_);
            }
        } else {
            BlockPos blockPos = p_220069_3_.above();
            BlockState state = p_220069_2_.getBlockState(blockPos);
            if (state.getBlock() != this) {
                p_220069_2_.isEmptyBlock(blockPos);
                if (!p_220069_2_.isClientSide) {
                    dropResources(state, p_220069_2_, blockPos);
                }
            }
        }
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

    @Override
    public void playerWillDestroy(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
        BlockPos blockpos = p_176208_2_.below();
        BlockPos blockpos1 = p_176208_2_.above();
        if (p_176208_4_.isCreative() && p_176208_3_.getValue(HALF) == DoubleBlockHalf.UPPER && p_176208_1_.getBlockState(blockpos).getBlock() == this) {
            p_176208_1_.removeBlock(blockpos, false);
        }
        if (p_176208_3_.getValue(HALF) == DoubleBlockHalf.LOWER && p_176208_1_.getBlockState(blockpos1).getBlock() == this) {
            if (p_176208_4_.isCreative()) {
                p_176208_1_.removeBlock(p_176208_2_, false);
            }
            p_176208_1_.removeBlock(blockpos1, false);
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntitySurgeryChamber();
    }

    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> p_190948_3_, ITooltipFlag p_190948_4_) {
        p_190948_3_.add(new TranslationTextComponent("cybercraft.tooltip.surgery_chamber.0"));
        p_190948_3_.add(new TranslationTextComponent("cybercraft.tooltip.surgery_chamber.1"));
    }
}
