package com.rena.cybercraft.core.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class SurgeryRemovePacket{

    private BlockPos pos;
    private int dimensionId;
    private int slotNumber;
    private boolean isNull;

    public SurgeryRemovePacket(BlockPos pos, int dimensionId, int slotNumber, boolean isNull)
    {
        this.pos = pos;
        this.dimensionId = dimensionId;
        this.slotNumber = slotNumber;
        this.isNull = isNull;
    }

    public static void write(SurgeryRemovePacket packet, PacketBuffer buf) {
        buf.writeInt(packet.pos.getX());
        buf.writeInt(packet.pos.getY());
        buf.writeInt(packet.pos.getZ());
        buf.writeInt(packet.dimensionId);
        buf.writeInt(packet.slotNumber);
        buf.writeBoolean(packet.isNull);
    }

    public static SurgeryRemovePacket read(SurgeryRemovePacket packet, PacketBuffer buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        packet.pos = new BlockPos(x, y, z);
        packet.dimensionId = buf.readInt();
        packet.slotNumber = buf.readInt();
        packet.isNull = buf.readBoolean();
        return new SurgeryRemovePacket(packet.pos, packet.dimensionId , packet.slotNumber, packet.isNull);
    }

}
