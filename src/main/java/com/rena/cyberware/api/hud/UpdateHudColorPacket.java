package com.rena.cyberware.api.hud;

import com.rena.cyberware.api.CybercraftAPI;
import com.rena.cyberware.api.ICybercraftUserData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class UpdateHudColorPacket{

    public UpdateHudColorPacket() {}

    private int color;

    public UpdateHudColorPacket(int color)
    {
        this.color = color;
    }

    /*@Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(color);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        color = buf.readInt();
    }

    public static class UpdateHudColorPacketHandler implements IMessageHandler<UpdateHudColorPacket, ByteBufUtils>
    {
        @Override
        public ByteBufUtils onMessage(UpdateHudColorPacket message, MessageContext ctx)
        {
            ServerPlayerEntity player = ctx.getServerHandler().player;
            DimensionManager.getWorld(player.level.provider.getDimension()).addScheduledTask(new DoSync(message.color, player));

            return null;
        }
    }*/

    private static class DoSync implements Runnable
    {
        private int color;
        private PlayerEntity playerEntity;

        public DoSync(int color, PlayerEntity playerEntity)
        {
            this.color = color;
            this.playerEntity = playerEntity;
        }

        @Override
        public void run()
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(playerEntity);
            if (cyberwareUserData != null)
            {
                cyberwareUserData.setHudColor(color);
            }
        }
    }

}
