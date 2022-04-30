package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import com.rena.cybercraft.common.util.WorldUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ComponentBoxBlock extends ShapedBlock {
    private static final VoxelShape SHAPE = Block.box(1f, 0f, 4f, 15f, 10f, 12f);

    public ComponentBoxBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(10f, 20f).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().harvestLevel(1).dynamicShape().noOcclusion(), SHAPE);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityComponentBox();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()){
            TileEntityComponentBox te = WorldUtil.getTileEntity(TileEntityComponentBox.class, world, pos);
            if (te != null){
                NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean bool) {
        if (!world.isClientSide()){
            if (state.is(newState.getBlock())){
                InventoryHelper.dropContents(world, pos, WorldUtil.getTileEntity(TileEntityComponentBox.class, world, pos));
            }
        }
        super.onRemove(state, world, pos, newState, bool);
    }
}
