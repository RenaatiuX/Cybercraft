package com.rena.cyberware.core.network;

import com.rena.cyberware.api.CybercraftAPI;
import com.rena.cyberware.api.ICybercraftUserData;
import com.rena.cyberware.api.hud.CybercraftHudDataEvent;
import com.rena.cyberware.api.hud.HudNBTData;
import com.rena.cyberware.api.hud.IHudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class CyberwareSyncPacket {

    private final CompoundNBT data;
    private final int entityId;

    public CyberwareSyncPacket(CompoundNBT data, int entityId) {
        this.data = data;
        this.entityId = entityId;
    }

    public static void write(CyberwareSyncPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.entityId);
        buf.writeNbt(packet.data);
    }

    public static CyberwareSyncPacket read(PacketBuffer buf) {
        int entityId = buf.readInt();
        CompoundNBT data = buf.readNbt();
        return new CyberwareSyncPacket(data, entityId);
    }

    public static final void handle(CyberwareSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
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
