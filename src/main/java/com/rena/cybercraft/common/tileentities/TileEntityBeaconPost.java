package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.common.block.BeaconPostBlock;
import com.rena.cybercraft.common.tileentities.util.IRadioTower;
import com.rena.cybercraft.common.util.WorldUtil;
import com.rena.cybercraft.core.init.BlockInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TileEntityBeaconPost extends TileEntity implements IRadioTower {

    private BlockPos master = null;
    private boolean isMaster = false;
    private boolean destructing = false;
    private int tier = 1;
    private Set<BlockPos> slaves = new HashSet<>();

    public TileEntityBeaconPost() {
        super(TileEntityTypeInit.RADIO_TOWER.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public double getViewDistance() {
        return 16384.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isMaster){
            AxisAlignedBB aabb = new AxisAlignedBB(this.getBlockPos().below(10));
            aabb = aabb.inflate(2, 10, 2);
            return aabb;
        }
        return super.getRenderBoundingBox();
    }

    public void setMasterLoc(BlockPos start) {
        this.master = start;
        this.isMaster = start.compareTo(this.getBlockPos()) == 0;
        if (this.isMaster) {
            this.slaves.add(start);
        } else {
            TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, level, this.master);
            te.addSlave(this.worldPosition);
        }
        level.sendBlockUpdated(worldPosition, level.getBlockState(getBlockPos()), level.getBlockState(getBlockPos()), 2);
        this.setChanged();
    }

    private void setMaster(BlockPos start){
        this.master = start;
        this.isMaster = start.compareTo(this.getBlockPos()) == 0;
        if (this.isMaster) {
            this.slaves.add(start);
        }
        this.setChanged();
    }



    public void updateMaster(BlockPos newMaster) {
        if (this.isMaster) {
            TileEntityBeaconPost master = WorldUtil.getTileEntity(TileEntityBeaconPost.class, this.level, newMaster);
            if (master != null) {
                master.setMasterLoc(newMaster);
                for (BlockPos pos : this.slaves) {
                    TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, this.level, pos);
                    te.setMasterLoc(newMaster);
                }
            }
        } else {
            TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, this.level, this.master);
            te.updateMaster(newMaster);
        }
    }

    public boolean isTransformed() {
        return this.getBlockState().getValue(BeaconPostBlock.TRANSFORMED) != 0;
    }

    public void destruct() {
        BlockPos masterPos;
        if (this.isMaster)
            masterPos = this.worldPosition;
        else
            masterPos = this.master;
        if (!destructing) {
            destructing = true;
            for (int y = 0; y <= -9; y--) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (y > -3 && (x != 0 || z != 0)) {
                            continue;
                        }

                        BlockPos newPos = masterPos.offset(x, y, z);

                        BlockState state = level.getBlockState(newPos);
                        Block block = state.getBlock();
                        if (block == BlockInit.RADIO_POST.get() && state.getValue(BeaconPostBlock.TRANSFORMED) > 0) {
                            level.setBlock(newPos, state.setValue(BeaconPostBlock.TRANSFORMED, 0), Constants.BlockFlags.BLOCK_UPDATE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        load(nbt);
    }

    protected void load(CompoundNBT nbt) {
        if (nbt.contains("xx"))
            setMaster(loadBlockPos(nbt));
        if (this.isMaster) {
            if (nbt.contains("slaves")) {
                ListNBT list = nbt.getList("slaves", 0);
                for (int i = 0; i < list.size(); i++) {
                    CompoundNBT poses = list.getCompound(i);
                    this.slaves.add(loadBlockPos(poses));
                }
            }
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(null, pkt.getTag());
    }

    @Nullable
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
        if (this.master != null)
            tagCompound = saveBlockPos(this.master, tagCompound);
        if (this.isMaster) {
            ListNBT list = new ListNBT();
            for (BlockPos pos : this.slaves) {
                CompoundNBT nbt = new CompoundNBT();
                nbt = saveBlockPos(pos, nbt);
                list.add(nbt);
            }
            if (list.size() > 0)
                tagCompound.put("slaves", list);
        }
        return tagCompound;
    }

    private static CompoundNBT saveBlockPos(BlockPos pos, CompoundNBT nbt) {
        nbt.putInt("xx", pos.getX());
        nbt.putInt("yy", pos.getY());
        nbt.putInt("zz", pos.getZ());
        return nbt;
    }

    private static BlockPos loadBlockPos(CompoundNBT nbt) {
        int x = nbt.getInt("xx");
        int y = nbt.getInt("yy");
        int z = nbt.getInt("zz");
        return new BlockPos(x, y, z);
    }

    public int getTier() {
        return tier;
    }

    public boolean isDestructing() {
        return destructing;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public BlockPos getMaster() {
        if (!isMaster)
            return master;
        return this.getBlockPos();
    }

    @Override
    public int getTier(BlockPos pos) {
        if (this.isMaster)
            return this.tier;
        return 0;
    }

    public void addSlave(BlockPos pos) {
        if (this.isMaster) {
            this.slaves.add(pos);
        } else {
            TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, this.level, this.master);
            te.addSlave(pos);

        }
    }

    public void removeSlave(BlockPos pos) {
        if (this.isMaster)
            this.slaves.remove(pos);
        else {
            TileEntityBeaconPost te = WorldUtil.getTileEntity(TileEntityBeaconPost.class, this.level, this.master);
            te.removeSlave(pos);

        }
    }

    public Set<BlockPos> getSlaves() {
        return slaves;
    }
}
