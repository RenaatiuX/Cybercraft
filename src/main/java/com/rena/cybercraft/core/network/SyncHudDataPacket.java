package com.rena.cybercraft.core.network;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class SyncHudDataPacket implements Runnable{

    private CompoundNBT tagCompound;
    private PlayerEntity entityPlayer;
    public SyncHudDataPacket(CompoundNBT tagCompound)
    {
        this.tagCompound = tagCompound;
    }

    public static void write(SyncHudDataPacket packet, PacketBuffer buf) {
        buf.writeNbt(packet.tagCompound);
    }

    public static SyncHudDataPacket read(PacketBuffer buf) {
        CompoundNBT tagCompound = buf.readNbt();
        return new SyncHudDataPacket(tagCompound);
    }

    @Override
    public void run() {
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData != null)
        {
            cyberwareUserData.setHudData(tagCompound);
        }
    }
}
