package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class TileEntityBeaconLarge extends TileEntityBeacon implements ITickableTileEntity {

    private boolean wasWorking = false;
    private int count = 0;

    private static int TIER = 3;

    public TileEntityBeaconLarge(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    /*@Override
    public void tick() {
        BlockState master = level.getBlockState(worldPosition.offset(0, -10, 0));

        boolean powered = level.hasNeighborSignal(worldPosition.offset(1, -10, 0))
                || level.hasNeighborSignal(worldPosition.offset(-1, -10, 0))
                || level.hasNeighborSignal(worldPosition.offset(0, -10, 1))
                || level.hasNeighborSignal(worldPosition.offset(0, -10, -1));
        boolean working = !powered && master.getBlock() == BlockInit.RADIO_POST.get() && master.getValue(BlockBeaconPost.TRANSFORMED) == 2;

        if (!wasWorking && working)
        {
            this.enable();
        }

        if (wasWorking && !working)
        {
            disable();
        }

        wasWorking = working;

        if (level.isClientSide && working)
        {
            count = (count + 1) % 20;
            if (count == 0)
            {
                BlockState state = level.getBlockState(worldPosition);
                if (state.getBlock() == BlockInit.RADIO_LARGE.get())
                {
                    boolean ns = state.getValue(BlockBeaconLarge.FACING) == Direction.EAST || state.getValue(BlockBeaconLarge.FACING) == Direction.WEST;
                    float dist = .5F;
                    float speedMod = .2F;
                    int degrees = 45;
                    for (int index = 0; index < 18; index++)
                    {
                        float sin = (float) Math.sin(Math.toRadians(degrees));
                        float cos = (float) Math.cos(Math.toRadians(degrees));
                        float xOffset = dist * sin;
                        float yOffset = .2F + dist * cos;
                        float xSpeed = speedMod * sin;
                        float ySpeed = speedMod * cos;
                        level.spawnParticle(ParticleTypes.SMOKE,
                                pos.getX() + .5F + (ns ? xOffset : 0),
                                pos.getY() + .5F + yOffset,
                                pos.getZ() + .5F + (ns ? 0 : xOffset),
                                ns ? xSpeed : 0,
                                ySpeed,
                                ns ? 0 : xSpeed,
                                255, 255, 255 );

                        level.spawnParticle(ParticleTypes.SMOKE,
                                pos.getX() + .5F - (ns ? xOffset : 0),
                                pos.getY() + .5F + yOffset,
                                pos.getZ() + .5F - (ns ? 0 : xOffset),
                                ns ? -xSpeed : 0,
                                ySpeed,
                                ns ? 0 : -xSpeed,
                                255, 255, 255 );

                        degrees += 5;
                    }
                }
            }
        }
    }

    private void disable()
    {
        Map<BlockPos, Integer> mapBeaconPosition = getBeaconPositionsForTierAndDimension(TIER, level);
        mapBeaconPosition.remove(getBlockPos());
    }

    private void enable()
    {
        Map<BlockPos, Integer> mapBeaconPosition = getBeaconPositionsForTierAndDimension(TIER, level);
        if (!mapBeaconPosition.containsKey(getBlockPos()))
        {
            mapBeaconPosition.put(getBlockPos(), LibConstants.LARGE_BEACON_RANGE);
        }
    }

    @Override
    public boolean isRemoved() {

        disable();
        return super.isRemoved();
    }*/
}
