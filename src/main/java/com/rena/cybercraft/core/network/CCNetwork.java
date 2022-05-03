package com.rena.cybercraft.core.network;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.hud.UpdateHudColorPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class CCNetwork {

    public static final SimpleChannel PACKET_HANDLER = registerChannel("packet_handler", "0.1.0");
    private static int CHANNEL_INDEX = 0;

    protected static SimpleChannel registerChannel(String id, String version){
        return NetworkRegistry.newSimpleChannel(Cybercraft.modLoc(id), () -> version, version::equals, version::equals);
    }

    public static final void init(){
        PACKET_HANDLER.registerMessage(CHANNEL_INDEX++, UpdateHudColorPacket.class, UpdateHudColorPacket::write, UpdateHudColorPacket::read, UpdateHudColorPacket::handle);
        PACKET_HANDLER.registerMessage(CHANNEL_INDEX++, CybercraftSyncPacket.class, CybercraftSyncPacket::write, CybercraftSyncPacket::read, CybercraftSyncPacket::handle);
        PACKET_HANDLER.registerMessage(CHANNEL_INDEX++, ParticlePacket.class, ParticlePacket::write, ParticlePacket::read, ParticlePacket::handle);
        PACKET_HANDLER.registerMessage(CHANNEL_INDEX++, SwitchHeldItemAndRotationPacket.class, SwitchHeldItemAndRotationPacket::write, SwitchHeldItemAndRotationPacket::read, SwitchHeldItemAndRotationPacket::handle);
    }

    public static void sendTo(Object message, ServerPlayerEntity player) {

        PACKET_HANDLER.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToPlayerInTRange(PacketDistributor.TargetPoint point, Object packet){
        PACKET_HANDLER.send(PacketDistributor.NEAR.with(() -> point), packet);
    }
}
