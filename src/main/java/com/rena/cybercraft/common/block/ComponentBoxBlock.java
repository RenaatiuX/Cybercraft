package com.rena.cybercraft.common.block;

import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import com.rena.cybercraft.common.util.WorldUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ComponentBoxBlock extends ShapedBlock {
    private static final VoxelShape SHAPE = Block.box(1f, 0f, 4f, 15f, 10f, 12f);

    public ComponentBoxBlock() {
        super(AbstractBlock.Properties.of(Material.METAL).strength(10f, 20f).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().harvestLevel(1).noOcclusion(), SHAPE);
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
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!world.isClientSide()) {
            TileEntityComponentBox te = WorldUtil.getTileEntity(TileEntityComponentBox.class, world, pos);
            if (te != null) {
                stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                    if (handler instanceof ItemStackHandler)
                        te.loadForgeItems(((ItemStackHandler) handler).serializeNBT());
                });
            }
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()) {
            TileEntityComponentBox te = WorldUtil.getTileEntity(TileEntityComponentBox.class, world, pos);
            if (te != null) {
                NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean bool) {
        if (!world.isClientSide()) {
            if (state.is(newState.getBlock())) {
                TileEntityComponentBox te = WorldUtil.getTileEntity(TileEntityComponentBox.class, world, pos);
                CompoundNBT nbt = te.serializeNBT();
                ItemStack stack = new ItemStack(state.getBlock().asItem(), 1, nbt);
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
        super.onRemove(state, world, pos, newState, bool);
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
        return Collections.emptyList();
    }
}
