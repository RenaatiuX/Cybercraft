package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.api.item.IBlueprint;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBlueprintArchive extends TileEntity {
    public TileEntityBlueprintArchive(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    /*public class ItemStackHandlerBlueprint extends ItemStackHandler
    {
        public ItemStackHandlerBlueprint(int i)
        {
            super(i);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if (!isItemValidForSlot(slot, stack)) return stack;

            return super.insertItem(slot, stack, simulate);
        }

        public boolean isItemValidForSlot(int slot, ItemStack stack)
        {
            if (!stack.isEmpty() && stack.getItem() instanceof IBlueprint) return true;
            int[] ids = OreDictionary.getOreIDs(stack);
            int paperId = OreDictionary.getOreID("paper");
            for (int id : ids)
            {
                if (id == paperId)
                {
                    return true;
                }
            }

            return stack.isEmpty();
        }

    }

    public ItemStackHandler slots = new ItemStackHandlerBlueprint(18);
    public String customName = null;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.getCapability(cap, side);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) slots;
        }
        return super.getCapability(cap);
    }

    @Override
    public void load(BlockState blockState, CompoundNBT tagCompound) {
        super.load(blockState, tagCompound);

        slots.deserializeNBT(tagCompound.getCompound("inv"));

        if (tagCompound.contains("CustomName"))
        {
            customName = tagCompound.getString("CustomName");
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT tagCompound) {
        tagCompound = super.save(tagCompound);

        tagCompound.put("inv", this.slots.serializeNBT());

        if (this.hasCustomName())
        {
            tagCompound.putString("CustomName", customName);
        }

        return tagCompound;
    }

    //I don't know what this shit is
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound data = pkt.getNbtCompound();
        this.readFromNBT(data);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT data = new CompoundNBT();
        this.save(data);
        return new SUpdateTileEntityPacket(worldPosition, 0, data);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    public boolean isUsableByPlayer(PlayerEntity entityPlayer)
    {
        return this.level.getBlockEntity(worldPosition) == this
                && entityPlayer.distanceToSqr(worldPosition.getX() + 0.5D, worldPosition.getY() + 0.5D, worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public String getName()
    {
        return this.hasCustomName() ? customName : "cybercraft.container.blueprint_archive";
    }

    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomInventoryName(String name)
    {
        this.customName = name;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return (oldState.getBlock() != newState.getBlock());
    }*/

}
