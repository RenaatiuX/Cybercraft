package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class EngineeringTableBlock extends Block {
    public EngineeringTableBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).harvestLevel(1).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).strength(10f, 6f));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityEngineeringTable();
    }
}
