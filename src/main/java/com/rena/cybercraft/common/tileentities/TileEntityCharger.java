package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntityCharger extends TileEntity implements ITickableTileEntity, IEnergyStorage {

    private PowerContainer container = new PowerContainer();
    private boolean last = false;

    public TileEntityCharger() {
        super(TileEntityTypeInit.CHARGER_TE.get());
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT compoundNBT) {
        super.load(p_230337_1_, compoundNBT);
        container.deserializeNBT(compoundNBT.getCompound("power"));
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        compoundNBT = super.save(compoundNBT);
        compoundNBT.put("power", container.serializeNBT());
        return compoundNBT;
    }

   /* @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT data = pkt.getTag();
        this.load(data);
    }*/

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

    @Override
    public void tick() {
        /*List<LivingEntity> entitiesInRange = level.getEntitiesOfClass(LivingEntity.class,
                new AxisAlignedBB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(),
                        worldPosition.getX() + 1F, worldPosition.getY() + 2.5F, worldPosition.getZ() + 1F) );
        for (LivingEntity entityInRange : entitiesInRange)
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityInRange);
            if (cyberwareUserData != null)
            {
                if ( !cyberwareUserData.isAtCapacity(ItemStack.EMPTY, 20)
                        && (container.getStoredPower() >= CyberwareConfig.TESLA_PER_POWER) )
                {
                    if (!level.isClientSide)
                    {
                        container.takePower(CyberwareConfig.TESLA_PER_POWER, false);
                    }
                    cyberwareUserData.addPower(20, ItemStack.EMPTY);

                    if (entityInRange.tickCount % 5 == 0)
                    {
                        level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + .5F, worldPosition.getY() + 1F, worldPosition.getZ() + .5F, 0F, .05F, 0F, 255, 150, 255 );
                        level.addParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + .5F, pos.getY() + 1F, pos.getZ() + .5F, .04F, .05F, .04F, 255, 150, 255 );
                        level.addParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + .5F, pos.getY() + 1F, pos.getZ() + .5F, -.04F, .05F, .04F, 255, 150, 255 );
                        level.addParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + .5F, pos.getY() + 1F, pos.getZ() + .5F, .04F, .05F, -.04F, 255, 150, 255 );
                        level.addParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + .5F, pos.getY() + 1F, pos.getZ() + .5F, -.04F, .05F, -.04F, 255, 150, 255 );
                    }
                }
            }
        }

        boolean hasPower = (container.getStoredPower() >= CyberwareConfig.TESLA_PER_POWER);
        if (hasPower != last && !level.isClientSide)
        {
            BlockState state = level.getBlockState(getBlockPos());
            level.sendBlockUpdated(worldPosition, state, state, 2);
            last = hasPower;
        }*/
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return (int)container.givePower(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return (int)container.takePower(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return (int)container.getStoredPower();
    }

    @Override
    public int getMaxEnergyStored() {
        return (int)container.getCapacity();
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
