package com.rena.cybercraft.common.item.block;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ComponentBoxContainer;
import com.rena.cybercraft.common.container.ItemComponentBoxContainer;
import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemComponentBox extends BlockItem{

    public ItemComponentBox(Block b) {
        super(b, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (!context.getLevel().isClientSide() && context.getPlayer().isShiftKeyDown()){
            NetworkHooks.openGui((ServerPlayerEntity) context.getPlayer(), new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".component_box");
                }

                @Nullable
                @Override
                public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
                    return new ItemComponentBoxContainer(id, inv, context.getItemInHand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new));
                }
            }, buf -> buf.writeVarInt(TileEntityComponentBox.CONTAINER_SIZE));
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapabilityProvider(stack, TileEntityComponentBox.CONTAINER_SIZE);
    }

    private class CapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
        private final ItemStack stack;
        private final ItemStackHandler handler;

        private CapabilityProvider(ItemStack stack, int inventorySize) {
            this.stack = stack;
            handler = new ItemStackHandler(inventorySize);
        }


        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                return (LazyOptional<T>) LazyOptional.of(() -> handler);
            return null;
        }

        @Override
        public CompoundNBT serializeNBT() {
            return handler.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            handler.deserializeNBT(nbt);
        }
    }
}
