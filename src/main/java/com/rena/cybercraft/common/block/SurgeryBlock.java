package com.rena.cybercraft.common.block;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.tileentities.TileEntityScanner;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.common.util.WorldUtil;
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

public class SurgeryBlock extends Block {

    public SurgeryBlock() {
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
        if(!world.isClientSide){
            TileEntitySurgery te = WorldUtil.getTileEntity(TileEntitySurgery.class, world, pos);
            if(te != null && player instanceof ServerPlayerEntity){

                //Ensure the Base Tolerance Attribute has been updated for any Config Changes
                player.getAttribute(CybercraftAPI.TOLERANCE_ATTR).setBaseValue(CybercraftConfig.C_ESSENCE.essence.get());

                ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(player);
               // te.updatePlayerSlots(player, cybercraftUserData);
                NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.SUCCESS;
    }
}
