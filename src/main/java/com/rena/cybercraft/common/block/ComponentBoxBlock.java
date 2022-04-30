package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ComponentBoxBlock extends Block {
    public ComponentBoxBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(10f, 20f).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().harvestLevel(1));
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
}
