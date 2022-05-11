package com.rena.cybercraft.core.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class GuiPacket implements Runnable{

    private int guid;
    private int x;
    private int y;
    private int z;

    public GuiPacket(int guid, int x, int y, int z)
    {
        this.guid = guid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void write(GuiPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.x);
        buf.writeInt(packet.y);
        buf.writeInt(packet.z);
        buf.writeInt(packet.guid);
    }

    public static GuiPacket read(PacketBuffer buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        int guid = buf.readInt();

        return new GuiPacket(guid, x, y, z);
    }

    @Override
    public void run() {
        /*ServerPlayerEntity serverPlayer = context.getServerHandler().player;
        serverPlayer.openGui(Cyberware.INSTANCE, guid, serverPlayer.world, x, y, z);*/
    }
}
