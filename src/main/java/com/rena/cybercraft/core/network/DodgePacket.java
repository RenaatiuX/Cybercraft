package com.rena.cybercraft.core.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class DodgePacket {
    private final int id;
    public DodgePacket(int id){
        this.id = id;
    }

    public DodgePacket(PacketBuffer buf){
        this(buf.readInt());
    }

    public static final void write(DodgePacket packet, PacketBuffer buf){
        buf.writeInt(packet.id);
    }

    public static final void handle(DodgePacket packet, Supplier<NetworkEvent.Context> ctx){

    }

}
