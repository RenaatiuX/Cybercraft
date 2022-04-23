package com.rena.cyberware.core.network;

import com.rena.cyberware.Cybercraft;
import com.rena.cyberware.api.hud.UpdateHudColorPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CCNetwork {

    public static final SimpleChannel PACKET_HANDLER = registerChannel("packet_handler", "0.1.0");

    protected static SimpleChannel registerChannel(String id, String version){
        return NetworkRegistry.newSimpleChannel(Cybercraft.modeLoc(id), () -> version, version::equals, version::equals);
    }

    public static final void init(){
        PACKET_HANDLER.registerMessage(0, UpdateHudColorPacket.class, UpdateHudColorPacket::write, UpdateHudColorPacket::read, UpdateHudColorPacket::handle);
        PACKET_HANDLER.registerMessage(1, CyberwareSyncPacket.class, CyberwareSyncPacket::write, CyberwareSyncPacket::read, CyberwareSyncPacket::handle);
    }

    public static void sendTo(Object message, ServerPlayerEntity player) {

        PACKET_HANDLER.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }
}
