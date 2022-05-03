package com.rena.cybercraft.core.network;

import net.minecraft.network.PacketBuffer;

public class OpenRadialMenuPacket {

    public OpenRadialMenuPacket() {}

    public static void write(OpenRadialMenuPacket packet, PacketBuffer buf) {

    }

    public static OpenRadialMenuPacket read(PacketBuffer buf) {


        return new OpenRadialMenuPacket();
    }

}
