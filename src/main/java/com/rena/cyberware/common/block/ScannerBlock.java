package com.rena.cyberware.common.block;

import com.rena.cyberware.common.tileentities.TileEntityScanner;
import com.rena.cyberware.common.util.WorldUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ScannerBlock extends Block {
    public ScannerBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).strength(7f, 3f).requiresCorrectToolForDrops());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityScanner();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()){
            TileEntityScanner te = WorldUtil.getTileEntity(TileEntityScanner.class, world, pos);
            if (te != null && player instanceof ServerPlayerEntity){
                NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
                return ActionResultType.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }
}
