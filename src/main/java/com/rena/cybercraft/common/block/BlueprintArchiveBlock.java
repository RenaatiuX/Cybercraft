package com.rena.cybercraft.common.block;

import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlueprintArchiveBlock extends ContainerBlock {

    protected BlueprintArchiveBlock(Properties p_i48446_1_) {
        super(p_i48446_1_);
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return null;
    }
}
