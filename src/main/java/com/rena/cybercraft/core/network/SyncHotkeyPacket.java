package com.rena.cybercraft.core.network;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.HotkeyHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class SyncHotkeyPacket implements Runnable{

    private int selectedPart;
    private int key;
    private PlayerEntity entityPlayer;


    public SyncHotkeyPacket(int selectedPart, int key)
    {
        this.selectedPart = selectedPart;
        this.key = key;
    }

    public static void write(SyncHotkeyPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.selectedPart);
        buf.writeInt(packet.key);
    }

    public static SyncHotkeyPacket read(PacketBuffer buf) {
        int selectedPart = buf.readInt();
        int key = buf.readInt();
        return new SyncHotkeyPacket(key, selectedPart);
    }

    @Override
    public void run() {
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData != null)
        {
            if (key == Integer.MAX_VALUE)
            {
                HotkeyHelper.removeHotkey(cyberwareUserData, cyberwareUserData.getActiveItems().get(selectedPart));
            }
            else
            {
                HotkeyHelper.removeHotkey(cyberwareUserData, key);
                HotkeyHelper.assignHotkey(cyberwareUserData, cyberwareUserData.getActiveItems().get(selectedPart), key);
            }
        }
    }
}
