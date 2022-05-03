package com.rena.cybercraft.core.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;


public class EngineeringDestroyPacket {

    private BlockPos pos;
    private int dimensionId;

    public EngineeringDestroyPacket(BlockPos pos, int dimensionId)
    {
        this.pos = pos;
        this.dimensionId = dimensionId;
    }

    public static void write(EngineeringDestroyPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.pos.getX());
        buf.writeInt(packet.pos.getY());
        buf.writeInt(packet.pos.getZ());
    }

    public static EngineeringDestroyPacket read(EngineeringDestroyPacket packet, PacketBuffer buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        packet.pos = new BlockPos(x, y, z);
        packet.dimensionId = buf.readInt();
        return new EngineeringDestroyPacket(packet.pos, packet.dimensionId);
    }
}
