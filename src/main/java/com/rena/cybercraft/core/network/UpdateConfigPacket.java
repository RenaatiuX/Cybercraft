package com.rena.cybercraft.core.network;

import com.rena.cybercraft.common.config.CybercraftConfig;
import net.minecraft.network.PacketBuffer;

public class UpdateConfigPacket {

    public UpdateConfigPacket() {

    }

    public static void write(UpdateConfigPacket packet, PacketBuffer buf) {
        buf.writeInt(CybercraftConfig.C_ESSENCE.essence.get());
        buf.writeInt(CybercraftConfig.C_ESSENCE.criticalEssence.get());
        buf.writeBoolean(CybercraftConfig.C_OTHER.surgeryCrafting.get());
        buf.writeDouble(CybercraftConfig.C_MACHINES.engineeringChance.get());
        buf.writeDouble(CybercraftConfig.C_MACHINES.scannerChance.get());
        buf.writeDouble(CybercraftConfig.C_MACHINES.scannerChanceAddl.get());
        buf.writeDouble(CybercraftConfig.C_MACHINES.scannerTime.get());
    }

    public static UpdateConfigPacket read(PacketBuffer buf) {


        return new UpdateConfigPacket();
    }

}
