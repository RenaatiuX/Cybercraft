package com.rena.cybercraft.core.network;

import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import com.rena.cybercraft.common.util.WorldUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class EngineeringDestroyPacket {

    private BlockPos pos;

    public EngineeringDestroyPacket(BlockPos pos)
    {
        this.pos = pos;
    }

    public static void write(EngineeringDestroyPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.pos.getX());
        buf.writeInt(packet.pos.getY());
        buf.writeInt(packet.pos.getZ());
    }

    public static EngineeringDestroyPacket read(PacketBuffer buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        return new EngineeringDestroyPacket(new BlockPos(x, y, z));
    }

    public static final void handle(EngineeringDestroyPacket packet, Supplier<NetworkEvent.Context> ctx){
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            TileEntityEngineeringTable te = WorldUtil.getTileEntity(TileEntityEngineeringTable.class, context.getSender().level, packet.pos);
            if (te != null){
                te.salvage();
                context.setPacketHandled(true);
            }
        });
    }
}
