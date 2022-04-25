package com.rena.cybercraft.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.logging.Level;

public class ParticlePacket {

    private final int effectId;
    private final float x;
    private final float y;
    private final float z;

    /**
     * send to Client, only use in this direction
     */
    public ParticlePacket(int effectId, float x, float y, float z)
    {
        this.effectId = effectId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static final void write(ParticlePacket packet, PacketBuffer buf){
        buf.writeInt(packet.effectId);
        buf.writeFloat(packet.x);
        buf.writeFloat(packet.y);
        buf.writeFloat(packet.z);
    }

    public static final ParticlePacket read(PacketBuffer buf){
        int effectId = buf.readInt();
        float x = buf.readFloat();
        float y = buf.readFloat();
        float z = buf.readFloat();
        return new ParticlePacket(effectId, x,y,z);
    }

    public static final void handle(ParticlePacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> () -> clientHandle(packet, ctx));
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static final Object clientHandle(ParticlePacket packet, Supplier<NetworkEvent.Context> ctx){
        ClientWorld world = Minecraft.getInstance().level;

        if (world != null)
        {
            switch (packet.effectId)
            {
                case 0:
                    for (int index = 0; index < 5; index++)
                    {
                        world.addParticle(ParticleTypes.HEART,
                                packet.x + world.random.nextFloat() - 0.5F,
                                packet.y + world.random.nextFloat() - 0.5F,
                                packet.z + world.random.nextFloat() - 0.5F,
                                2.0F * (world.random.nextFloat() - 0.5F),
                                0.5F,
                                2.0F * (world.random.nextFloat() - 0.5F) );
                    }
                    break;

                case 1:
                    for (int index = 0; index < 5; index++)
                    {
                        world.addParticle(ParticleTypes.ANGRY_VILLAGER,
                                packet.x + world.random.nextFloat() - 0.5F,
                                packet.y + world.random.nextFloat() - 0.5F,
                                packet.z + world.random.nextFloat() - 0.5F,
                                2.0F * (world.random.nextFloat() - 0.5F),
                                .5F,
                                2.0F * (world.random.nextFloat() - 0.5F) );
                    }
                    break;
            }
        }
        return null;
    }

}
