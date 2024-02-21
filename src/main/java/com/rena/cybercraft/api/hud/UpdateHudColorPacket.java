package com.rena.cybercraft.api.hud;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateHudColorPacket {

    private int color;

    public UpdateHudColorPacket(int color) {
        this.color = color;
    }

    public static void write(UpdateHudColorPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.color);
    }

    public static UpdateHudColorPacket read(PacketBuffer buf) {
        return new UpdateHudColorPacket(buf.readInt());
    }

    public static void handle(UpdateHudColorPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(player);
            if (cyberwareUserData != null) {
                cyberwareUserData.setHudColor(packet.color);
            }
        });
    }

}
