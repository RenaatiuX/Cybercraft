package com.rena.cyberware.api.hud;

import com.rena.cyberware.api.CybercraftAPI;
import com.rena.cyberware.api.ICybercraftUserData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateHudColorPacket{

    private int color;

    public UpdateHudColorPacket(int color)
    {
        this.color = color;
    }

    public static void write(UpdateHudColorPacket packet, PacketBuffer buf)
    {
        buf.writeInt(packet.color);
    }

    public static UpdateHudColorPacket read(PacketBuffer buf)
    {
        return new UpdateHudColorPacket(buf.readInt());
    }

    public static void handle(UpdateHudColorPacket packet, Supplier<NetworkEvent.Context> ctx){
        ServerPlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(player);
            if (cyberwareUserData != null)
            {
                cyberwareUserData.setHudColor(packet.color);
            }
        });
    }

}
