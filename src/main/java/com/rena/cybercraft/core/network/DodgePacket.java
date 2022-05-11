package com.rena.cybercraft.core.network;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.hud.INotification;
import com.rena.cybercraft.api.hud.NotificationInstance;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.events.HudHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class DodgePacket {
    private final int id;
    public DodgePacket(int id){
        this.id = id;
    }

    public DodgePacket(PacketBuffer buf){
        this(buf.readInt());
    }

    public static void write(DodgePacket packet, PacketBuffer buf){
        buf.writeInt(packet.id);
    }

    public static DodgePacket read(PacketBuffer buf){
        int id = buf.readInt();
        return new DodgePacket(id);
    }

    public static void handle(DodgePacket packet, Supplier<NetworkEvent.Context> ctx){
        Entity targetEntity = Minecraft.getInstance().level.getEntity(packet.id);

        if (targetEntity != null)
        {
            for (int index = 0; index < 25; index++)
            {
                Random rand = targetEntity.level.random;
                targetEntity.level.addParticle(ParticleTypes.EFFECT, targetEntity.getX(), targetEntity.getY() + rand.nextFloat() * targetEntity.dimensions.height, targetEntity.getZ(),
                        (rand.nextFloat() - .5F) * .2F,
                        0,
                        (rand.nextFloat() - .5F) * .2F);

            }

            targetEntity.playSound(SoundEvents.FIREWORK_ROCKET_SHOOT, 1F, 1F);

            if (targetEntity == Minecraft.getInstance().player)
            {
                HudHandler.addNotification(new NotificationInstance(targetEntity.tickCount, new DodgeNotification()));
            }
        }

        //return null;
    }

    private static class DodgeNotification implements INotification
    {

        @Override
        public void render(int x, int y)
        {
            Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);

            MatrixStack matrixStack = new MatrixStack();

            matrixStack.pushPose();
            float[] color = CybercraftAPI.getHUDColor();
            RenderSystem.color3f(color[0], color[1], color[2]);
            ClientUtils.drawTexturedModalRect(x + 1, y + 1, 0, 39, 15, 14);
            matrixStack.popPose();
        }

        @Override
        public int getDuration()
        {
            return 5;
        }
    }

}
