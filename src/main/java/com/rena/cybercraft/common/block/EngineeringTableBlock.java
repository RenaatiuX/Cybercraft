package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import com.rena.cybercraft.common.util.WorldUtil;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class EngineeringTableBlock extends RotatableBlock {
    public static final EnumProperty<Half> TOP = BlockStateProperties.HALF;

    public EngineeringTableBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).harvestLevel(1).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f, 6f).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(TOP, Half.BOTTOM));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.getValue(TOP) == Half.BOTTOM;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        return  world.isEmptyBlock(pos.above());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (!world.isClientSide()){
            if (state.getValue(TOP) == Half.TOP){
                if (world.isEmptyBlock(pos.below()))
                    return Blocks.AIR.defaultBlockState();
            }else{
                if (world.isEmptyBlock(pos.above()))
                    return Blocks.AIR.defaultBlockState();
            }
        }
        return state;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean bool) {
        if (!world.isClientSide){
            if (!state.is(newState.getBlock())){
                if (state.getValue(TOP) == Half.BOTTOM){
                    TileEntityEngineeringTable te = WorldUtil.getTileEntity(TileEntityEngineeringTable.class, world, pos);
                    InventoryHelper.dropContents(world, pos, te);
                    world.destroyBlock(pos.above(), true);
                }else if (state.getValue(TOP) == Half.TOP){
                    world.destroyBlock(pos.below(), true);
                }
            }
        }
        super.onRemove(state, world, pos, newState, bool);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (!world.isClientSide()) {
            BlockState upperPart = BlockInit.ENGINEERING_TABLE.get().defaultBlockState();
            upperPart = upperPart.setValue(HORIZONTAL_FACING, state.getValue(HORIZONTAL_FACING)).setValue(TOP, Half.TOP);
            world.setBlockAndUpdate(pos.above(), upperPart);
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityEngineeringTable();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide){
            BlockPos tePos = pos;
            if (state.getValue(TOP) == Half.TOP)
                tePos = tePos.below();
            TileEntityEngineeringTable te = WorldUtil.getTileEntity(TileEntityEngineeringTable.class, world, tePos);
            if (te != null){
                NetworkHooks.openGui((ServerPlayerEntity) player, te, tePos);
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TOP);

    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }
}
