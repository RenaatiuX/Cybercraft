package com.rena.cybercraft.core.network;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.hud.CybercraftHudDataEvent;
import com.rena.cybercraft.api.hud.HudNBTData;
import com.rena.cybercraft.api.hud.IHudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class CybercraftSyncPacket {

    private final CompoundNBT data;
    private final int entityId;

    public CybercraftSyncPacket(CompoundNBT data, int entityId) {
        this.data = data;
        this.entityId = entityId;
    }

    public static void write(CybercraftSyncPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.entityId);
        buf.writeNbt(packet.data);
    }

    public static CybercraftSyncPacket read(PacketBuffer buf) {
        int entityId = buf.readInt();
        CompoundNBT data = buf.readNbt();
        return new CybercraftSyncPacket(data, entityId);
    }

    public static final void handle(CybercraftSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity targetEntity = Minecraft.getInstance().player.level.getEntity(packet.entityId);
            ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(targetEntity);
            if (cybercraftUserData != null) {
                cybercraftUserData.deserializeNBT(packet.data);

                if (targetEntity == Minecraft.getInstance().player) {
                    CompoundNBT tagCompound = cybercraftUserData.getHudData();

                    CybercraftHudDataEvent hudEvent = new CybercraftHudDataEvent();
                    MinecraftForge.EVENT_BUS.post(hudEvent);
                    List<IHudElement> elements = hudEvent.getElements();

                    for (IHudElement element : elements) {
                        if (tagCompound.contains(element.getUniqueName())) {
                            element.load(new HudNBTData(tagCompound.getCompound(element.getUniqueName())));
                        }
                    }
                }
            }
        });
    }
}
