package com.rena.cybercraft.core.network;

import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ScannerSmashPacket {

    private int x;
    private int y;
    private int z;

    public ScannerSmashPacket(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void write(ScannerSmashPacket packet, PacketBuffer buf) {
        buf.writeInt(packet.x);
        buf.writeInt(packet.y);
        buf.writeInt(packet.z);
    }

    public static ScannerSmashPacket read(PacketBuffer buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        return new ScannerSmashPacket(x, y, z);
    }

    public static final void handle(ScannerSmashPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = Minecraft.getInstance().level;

            if (world != null)
            {
                TileEntity te = world.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
                if (te != null && te instanceof TileEntityEngineeringTable)
                {
                    TileEntityEngineeringTable eng = (TileEntityEngineeringTable) te;
                    eng.smashSounds();
                }
            }
        });
    }

}
