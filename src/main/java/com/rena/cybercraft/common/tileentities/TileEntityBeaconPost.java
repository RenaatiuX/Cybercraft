package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBeaconPost extends TileEntity {
    public TileEntityBeaconPost(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

   /* public static class TileEntityBeaconPostMaster extends TileEntityBeaconPost
    {
        public TileEntityBeaconPostMaster(TileEntityType<?> p_i48289_1_) {
            super(p_i48289_1_);
        }

        @OnlyIn(Dist.CLIENT)
        @Nonnull
        @Override
        public AxisAlignedBB getRenderBoundingBox()
        {
            return new AxisAlignedBB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 10, worldPosition.getZ() + 2);
        }

        @Override
        public void setMasterLoc(BlockPos start)
        {
            throw new IllegalStateException("NO");
        }
    }*/

    public BlockPos master = null;
    public boolean destructing = false;


    @OnlyIn(Dist.CLIENT)
    @Override
    public double getViewDistance()
    {
        return 16384.0D;
    }

    /*public void setMasterLoc(BlockPos start)
    {
        this.master = start;
        level.notifyBlockUpdate(worldPosition, level.getBlockState(getBlockPos()), level.getBlockState(getBlockPos()), 2);
        this.markDirty();
    }*/

    @Override
    public boolean isRemoved() {

        return super.isRemoved();
    }

    /*public void destruct()
    {
        if (!destructing)
        {
            destructing = true;
            for (int y = 0; y <= 9; y++)
            {
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        if (y > 3 && (x != 0 || z != 0))
                        {
                            continue;
                        }

                        BlockPos newPos = worldPosition.offset(x, y, z);

                        BlockState state = level.getBlockState(newPos);
                        Block block = state.getBlock();
                        if (block == BlockInit.RADIO_POST.get() && state.getValue(BlockBeaconPost.TRANSFORMED) > 0)
                        {
                            level.getBlockEntity(newPos);
                            level.setBlockEntity(newPos, state.withProperty(BlockBeaconPost.TRANSFORMED, 0), 2);

                        }

                    }
                }
            }
        }
    }*/

    /*@Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        super.load(p_230337_1_, p_230337_2_);
        if (!(this instanceof TileEntityBeaconPostMaster))
        {
            int x = p_230337_2_.getInt("xx");
            int y = p_230337_2_.getInt("yy");
            int z = p_230337_2_.getInt("zz");
            this.master = new BlockPos(x, y, z);
        }
    }

    //I don't know what this shit is
    /*@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound data = pkt.getNbtCompound();
        this.readFromNBT(data);
    }*/

    /*@Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT data = new CompoundNBT();
        this.save(data);
        return new SUpdateTileEntityPacket(worldPosition, 0, data);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Override
    public CompoundNBT save(CompoundNBT tagCompound) {
        tagCompound = super.save(tagCompound);

        if (!(this instanceof TileEntityBeaconPostMaster))
        {
            tagCompound.putInt("xx", master.getX());
            tagCompound.putInt("yy", master.getY());
            tagCompound.putInt("zz", master.getZ());
        }

        return tagCompound;
    }*/
}
