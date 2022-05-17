package com.rena.cybercraft.common.item.block;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ComponentBoxContainer;
import com.rena.cybercraft.common.container.ItemComponentBoxContainer;
import com.rena.cybercraft.common.tileentities.TileEntityComponentBox;
import com.rena.cybercraft.common.util.WorldUtil;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemComponentBox extends CybercraftItemBlock{

    public ItemComponentBox(Block b) {
        super(b, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB).stacksTo(1));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (!context.getLevel().isClientSide() && context.getPlayer().isCrouching()){
            openGui(context.getPlayer(), context.getLevel(), context.getItemInHand());
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide() && player.isShiftKeyDown()){
            openGui(player, world, player.getItemInHand(hand));
            return ActionResult.success(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    private void openGui(PlayerEntity player, World world, ItemStack holdSTack) {
        if (!world.isClientSide()) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".component_box");
                }

                @Nullable
                @Override
                public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
                    return new ItemComponentBoxContainer(id, inv, holdSTack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalStateException::new));
                }
            }, buf -> buf.writeVarInt(TileEntityComponentBox.CONTAINER_SIZE));
        }
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
                return LazyOptional.of(() -> handler).cast();
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltip) {
        text.add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.component_box")).withStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent(I18n.get("cybercraft.tooltip.component_box2")).withStyle(TextFormatting.GRAY));
    }
}
