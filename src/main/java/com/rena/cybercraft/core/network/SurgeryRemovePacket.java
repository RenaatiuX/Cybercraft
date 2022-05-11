package com.rena.cybercraft.core.network;

import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SurgeryRemovePacket implements Runnable{

    private BlockPos pos;
    private int dimensionId;
    private int slotNumber;
    private boolean isNull;

    public SurgeryRemovePacket(BlockPos pos, int dimensionId, int slotNumber, boolean isNull)
    {
        this.pos = pos;
        this.dimensionId = dimensionId;
        this.slotNumber = slotNumber;
        this.isNull = isNull;
    }

    public static void write(SurgeryRemovePacket packet, PacketBuffer buf) {
        buf.writeInt(packet.pos.getX());
        buf.writeInt(packet.pos.getY());
        buf.writeInt(packet.pos.getZ());
        buf.writeInt(packet.dimensionId);
        buf.writeInt(packet.slotNumber);
        buf.writeBoolean(packet.isNull);
    }

    public static SurgeryRemovePacket read(SurgeryRemovePacket packet, PacketBuffer buf) {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        packet.pos = new BlockPos(x, y, z);
        packet.dimensionId = buf.readInt();
        packet.slotNumber = buf.readInt();
        packet.isNull = buf.readBoolean();
        return new SurgeryRemovePacket(packet.pos, packet.dimensionId , packet.slotNumber, packet.isNull);
    }

    @Override
    public void run() {

    }

    /*@Override
    public void run() {
        World world = DimensionManager.getWorld(dimensionId);
        TileEntity te = world.getBlockEntity(pos);
        if (te instanceof TileEntitySurgery)
        {
            TileEntitySurgery surgery = (TileEntitySurgery) te;

            surgery.discardSlots[slotNumber] = isNull;

            if (isNull)
            {
                surgery.disableDependants(surgery.slotsPlayer.getStackInSlot(slotNumber),
                        ICybercraft.EnumSlot.values()[slotNumber / 10], slotNumber % LibConstants.WARE_PER_SLOT);
            }
            else
            {
                surgery.enableDependsOn(surgery.slotsPlayer.getStackInSlot(slotNumber),
                        ICybercraft.EnumSlot.values()[slotNumber / 10], slotNumber % LibConstants.WARE_PER_SLOT);
            }
            surgery.updateEssential(ICybercraft.EnumSlot.values()[slotNumber / LibConstants.WARE_PER_SLOT]);
            surgery.updateEssence();
        }
    }*/
}
