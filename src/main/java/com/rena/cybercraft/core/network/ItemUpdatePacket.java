package com.rena.cybercraft.core.network;

import com.rena.cybercraft.common.util.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemUpdatePacket {
    private final NonNullList<ItemStack> items;
    private final BlockPos pos;

    public ItemUpdatePacket(NonNullList<ItemStack> items, BlockPos pos) {
        this.items = items;
        this.pos = pos;
    }

    public static final ItemUpdatePacket read(PacketBuffer buffer) {
        int size = buffer.readInt();
        CompoundNBT nbt = buffer.readNbt();
        NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, items);
        BlockPos pos = buffer.readBlockPos();
        return new ItemUpdatePacket(items, pos);
    }
    public static final void write(ItemUpdatePacket packet, PacketBuffer buffer){
        buffer.writeInt(packet.items.size());
        buffer.writeNbt(ItemStackHelper.saveAllItems(new CompoundNBT(), packet.items));
        buffer.writeBlockPos(packet.pos);
    }

    public static final void handle(ItemUpdatePacket packet, Supplier<NetworkEvent.Context> ctx){
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld world = mc.level;
                LockableLootTileEntity te = WorldUtil.getTileEntity(LockableLootTileEntity.class, world, packet.pos);
                if (te != null){
                   for (int i = 0;i<packet.items.size();i++){
                       te.setItem(i, packet.items.get(i));
                   }
                   context.setPacketHandled(true);
                }
            });
        });
    }


}
