package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.common.item.BrainUpgradeItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.BlockInit;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class TileEntityBeacon extends TileEntity implements ITickableTileEntity {

    private static List<Integer> tiers = new CopyOnWriteArrayList<>();
    private static Map<Integer, Map<Integer, Map<BlockPos, Integer>>> mapBeaconPositionByTierDimension = new HashMap<>();
    private boolean wasWorking = false;
    private int count = 0;

    private static int TIER = 2;

    private static Map<Integer, Map<BlockPos, Integer>> getBeaconPositionsForTier(int tier)
    {
        Map<Integer, Map<BlockPos, Integer>> mapBeaconPositionByDimension = mapBeaconPositionByTierDimension.get(tier);
        if (mapBeaconPositionByDimension == null)
        {
            mapBeaconPositionByDimension = new HashMap<>();
            mapBeaconPositionByTierDimension.put(tier, mapBeaconPositionByDimension);
            if (!tiers.contains(tier))
            {
                tiers.add(tier);
                Collections.sort(tiers);
                Collections.reverse(tiers);
            }
        }

        return mapBeaconPositionByDimension;
    }

    /*public static Map<BlockPos, Integer> getBeaconPositionsForTierAndDimension(int tier, @Nonnull World world)
    {
        Map<Integer, Map<BlockPos, Integer>> mapBeaconPositionByDimension = getBeaconPositionsForTier(tier);
        int idDimension = world.provider.getDimension();
        return mapBeaconPositionByDimension.computeIfAbsent(idDimension, k -> new HashMap<>());
    }*/

    public TileEntityBeacon(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void tick() {

    }

    /*@Override
    public void tick() {
        boolean working = !level.hasNeighborSignal(worldPosition);

        if (!wasWorking && working)
        {
            enable();
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
                if (state.getBlock() == BlockInit.RADIO.get())
                {
                    boolean ns = state.getValue(BlockBeaconLarge.FACING) == Direction.NORTH
                            || state.getValue(BlockBeaconLarge.FACING) == Direction.SOUTH;
                    boolean backwards = state.getValue(BlockBeaconLarge.FACING) == Direction.SOUTH
                            || state.getValue(BlockBeaconLarge.FACING) == Direction.EAST;
                    float dist = .2F;
                    float speedMod = .08F;
                    int degrees = 45;
                    for (int index = 0; index < 5; index++)
                    {
                        float sin = (float) Math.sin(Math.toRadians(degrees));
                        float cos = (float) Math.cos(Math.toRadians(degrees));
                        float xOffset = dist * sin;
                        float yOffset = .2F + dist * cos;
                        float xSpeed = speedMod * sin;
                        float ySpeed = speedMod * cos;
                        float backOffsetX = (backwards ^ ns ? -.3F : .3F);
                        float backOffsetZ = (backwards ? .4F : -.4F);

                        level.addParticle(ParticleTypes.SMOKE,
                                pos.getX() + .5F + (ns ? xOffset + backOffsetX : backOffsetZ),
                                pos.getY() + .5F + yOffset,
                                pos.getZ() + .5F + (ns ? backOffsetZ : xOffset + backOffsetX),
                                ns ? xSpeed : 0,
                                ySpeed,
                                ns ? 0 : xSpeed,
                                255, 255, 255 );

                        level.addParticle(ParticleTypes.SMOKE,
                                pos.getX() + .5F + (ns ? -xOffset + backOffsetX : backOffsetZ),
                                pos.getY() + .5F + yOffset,
                                pos.getZ() + .5F + (ns ? backOffsetZ : -xOffset + backOffsetX),
                                ns ? -xSpeed : 0,
                                ySpeed,
                                ns ? 0 : -xSpeed,
                                255, 255, 255 );

                        degrees += 18;
                    }
                }
            }
        }
    }*/

    /*private void disable()
    {
        Map<BlockPos, Integer> mapBeaconPosition = getBeaconPositionsForTierAndDimension(TIER, level);
        mapBeaconPosition.remove(getBlockPos());
    }

    private void enable()
    {
        Map<BlockPos, Integer> mapBeaconPosition = getBeaconPositionsForTierAndDimension(TIER, level);
        if (!mapBeaconPosition.containsKey(getBlockPos()))
        {
            mapBeaconPosition.put(getBlockPos(), LibConstants.BEACON_RANGE);
        }
    }*/

    /*@Override
    public boolean isRemoved() {
        disable();
        return super.isRemoved();
    }

    public static int isInRange(World world, double posX, double posY, double posZ)
    {
        for (int tier : tiers)
        {
            Map<BlockPos, Integer> mapBeaconPosition = getBeaconPositionsForTierAndDimension(tier, world);
            for (Map.Entry<BlockPos, Integer> entry : mapBeaconPosition.entrySet())
            {
                double squareDistance = (posX - entry.getKey().getX()) * (posX - entry.getKey().getX())
                        + (posZ - entry.getKey().getZ()) * (posZ - entry.getKey().getZ());
                if (squareDistance < entry.getValue() * entry.getValue())
                {
                    return tier;
                }
            }
        }

        List<LivingEntity> entitiesInRange = world.getEntitiesOfClass(PlayerEntity.class,
                new AxisAlignedBB(posX - LibConstants.BEACON_RANGE_INTERNAL, 0, posZ - LibConstants.BEACON_RANGE_INTERNAL,
                        posX + LibConstants.BEACON_RANGE_INTERNAL, 255, posZ + LibConstants.BEACON_RANGE_INTERNAL) );

        ItemStack itemStackRadioRaw = ItemInit.BRAIN_UPGRADES.get().getCachedStack(BrainUpgradeItem.META_RADIO);
        for (LivingEntity entityInRange : entitiesInRange)
        {
            if (BrainUpgradeItem.isRadioWorking(entityInRange))
            {
                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityInRange);
                if (cyberwareUserData != null)
                {
                    ItemStack itemStackRadio = cyberwareUserData.getCybercraft(itemStackRadioRaw);
                    if ( !itemStackRadio.isEmpty()
                            && EnableDisableHelper.isEnabled(itemStackRadio) )
                    {
                        return 1;
                    }
                }
            }
        }

        return -1;
    }*/

}
