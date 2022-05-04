package com.rena.cybercraft.core.network;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class TriggerActiveAbilityPacket /*implements Runnable*/{

    private ItemStack stack;

    private PlayerEntity entityPlayer;

    public TriggerActiveAbilityPacket(ItemStack stack)
    {
        this.stack = stack;
    }

    /*public static void write(TriggerActiveAbilityPacket packet, PacketBuffer buf) {
        buf.writeItemStack(packet.stack, false);

    }

    public static TriggerActiveAbilityPacket read(TriggerActiveAbilityPacket packet, PacketBuffer buf) {
        packet.stack = buf.readItem();
        return new TriggerActiveAbilityPacket(packet.stack);
    }

    @Override
    public void run() {
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData != null)
        {
            CybercraftAPI.useActiveItem(entityPlayer, cyberwareUserData.getCybercraft(stack));
        }
    }*/
}
