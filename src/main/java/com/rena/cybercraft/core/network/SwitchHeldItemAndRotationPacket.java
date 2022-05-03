package com.rena.cybercraft.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchHeldItemAndRotationPacket {

    private int slot;
    private int entityId;
    private int attackerId;

    public SwitchHeldItemAndRotationPacket(int slot, int entityId, int attackerId) {
        this.slot = slot;
        this.entityId = entityId;
        this.attackerId = attackerId;
    }

    public static void write(SwitchHeldItemAndRotationPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.slot);
        buf.writeInt(packet.entityId);
        buf.writeInt(packet.attackerId);
    }

    public static SwitchHeldItemAndRotationPacket read(PacketBuffer buf) {
        int slot = buf.readInt();
        int entityId = buf.readInt();
        int attackerId = buf.readInt();
        return new SwitchHeldItemAndRotationPacket(slot, entityId, attackerId);
    }

    public static final void handle(SwitchHeldItemAndRotationPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity targetEntity = Minecraft.getInstance().level.getEntity(packet.entityId);

            if (targetEntity != null) {
                ((PlayerEntity) targetEntity).inventory.selected = packet.slot;

                if (packet.attackerId != -1) {
                    ((PlayerEntity) targetEntity).closeContainer();
                    Entity facingEntity = Minecraft.getInstance().level.getEntity(packet.attackerId);

                    if (facingEntity != null) {
                        faceEntity(targetEntity, facingEntity);
                    }
                }
            }

        });
    }

    public static void faceEntity(Entity player, Entity entity)
    {
        double d0 = entity.getX() - player.getX();
        double d2 = entity.getZ() - player.getZ();
        double d1;

        if (entity instanceof LivingEntity)
        {
            LivingEntity entitylivingbase = (LivingEntity) entity;
            d1 = entitylivingbase.getY() + entitylivingbase.getEyeHeight()
                    - (player.getY() + player.getEyeHeight());
        }
        else
        {
            d1 = (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0D
                    - (player.getY()+ player.getEyeHeight());
        }

        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        player.xRot = (float) (-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        player.yRot = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
    }
}