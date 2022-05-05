package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.api.IEnergy;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PowerContainer implements IEnergy, INBTSerializable<CompoundNBT>{

    private long stored;
    private long capacity;
    private long inputRate;
    private long outputRate;

    public PowerContainer()
    {
        this.stored = 0;
        this.capacity = 5000;
        this.inputRate = 50;
        this.outputRate = 50;
    }

    @Override
    public CompoundNBT serializeNBT() {
        final CompoundNBT tag = new CompoundNBT();
        tag.putLong("power", stored);
        tag.putLong("capacity", capacity);
        tag.putLong("input", inputRate);
        tag.putLong("output", outputRate);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.stored = nbt.getLong("power");
        this.capacity = nbt.getLong("capacity");
        this.inputRate = nbt.getLong("input");
        this.outputRate = nbt.getLong("output");

        if (this.stored > this.getCapacity())
        {
            this.stored = this.getCapacity();
        }
    }

    @Override
    public long getCapacity()
    {
        return capacity;
    }

    @Override
    public long getStoredPower()
    {
        return stored;
    }

    @Override
    public long givePower(long power, boolean simulated)
    {
        final long acceptedEnergy = Math.min(this.getCapacity() - this.stored, Math.min(inputRate, power));

        if (!simulated)
        {
            this.stored += acceptedEnergy;
        }

        return acceptedEnergy;
    }

    @Override
    public long takePower(long power, boolean simulated)
    {
        final long removedPower = Math.min(this.stored, Math.min(outputRate, power));

        if (!simulated)
        {
            this.stored -= removedPower;
        }

        return removedPower;
    }
}
