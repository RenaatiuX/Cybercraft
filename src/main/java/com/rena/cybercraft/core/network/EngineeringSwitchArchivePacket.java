package com.rena.cybercraft.core.network;

import com.rena.cybercraft.common.container.EngineeringTableContainer;
import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class EngineeringSwitchArchivePacket {

    private BlockPos pos;
    private int dimensionId;
    private int entityId;
    private boolean direction;
    private boolean isComponent;

    /*public EngineeringSwitchArchivePacket(BlockPos pos, PlayerEntity entityPlayer, boolean direction, boolean isComponent)
    {
        this.dimensionId = entityPlayer.level.dimensionType.getDimension();
        this.entityId = entityPlayer.getId();
        this.pos = pos;
        this.direction = direction;
        this.isComponent = isComponent;
    }

    public static void write(EngineeringSwitchArchivePacket packet, PacketBuffer buf) {
        buf.writeBoolean(packet.direction);
        buf.writeBoolean(packet.isComponent);
        buf.writeInt(packet.entityId);
        buf.writeInt(packet.pos.getX());
        buf.writeInt(packet.pos.getY());
        buf.writeInt(packet.pos.getZ());
        buf.writeInt(packet.dimensionId);
    }

    public static EngineeringSwitchArchivePacket read(EngineeringSwitchArchivePacket packet, PacketBuffer buf) {
        boolean direction = buf.readBoolean();
        boolean isComponent = buf.readBoolean();
        int entityId = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        packet.pos = new BlockPos(x, y, z);
        packet.dimensionId = buf.readInt();

        return new EngineeringSwitchArchivePacket(packet.pos, entityId, direction, isComponent);
    }

    public static final void handle(EngineeringSwitchArchivePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = DimensionManager.getWorld(dimensionId);
            Entity entity = world.getEntity(packet.entityId);
            if (entity instanceof PlayerEntity)
            {
                PlayerEntity entityPlayer = (PlayerEntity) entity;

                if (packet.isComponent)
                {
                    if (entityPlayer.containerMenu instanceof EngineeringTableContainer)
                    {
                        if (packet.direction)
                        {
                            ((EngineeringTableContainer) entityPlayer.containerMenu).nextComponentBox();
                        }
                        else
                        {
                            ((EngineeringTableContainer) entityPlayer.containerMenu).prevComponentBox();
                        }
                        TileEntityEngineeringTable te = (TileEntityEngineeringTable) world.getBlockEntity(packet.pos);
                    }
                }
                else
                {
                    if (entityPlayer.containerMenu instanceof EngineeringTableContainer)
                    {
                        if (packet.direction)
                        {
                            ((EngineeringTableContainer) entityPlayer.containerMenu).nextArchive();
                        }
                        else
                        {
                            ((EngineeringTableContainer) entityPlayer.containerMenu).prevArchive();
                        }
                        TileEntityEngineeringTable te = (TileEntityEngineeringTable) world.getBlockEntity(packet.pos);
                        te.lastPlayerArchive.put(entityPlayer.getStringUUID(), ((EngineeringTableContainer) entityPlayer.containerMenu).archive.getPos());
                    }
                }
            }
        }
    }*/

}
