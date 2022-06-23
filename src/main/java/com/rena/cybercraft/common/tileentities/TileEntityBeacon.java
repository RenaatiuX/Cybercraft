package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.common.block.BeaconLargeBlock;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import com.rena.cybercraft.common.item.BrainUpgradeItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.BlockInit;
import com.rena.cybercraft.core.init.EntityTypeInit;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class TileEntityBeacon extends TileEntity implements ITickableTileEntity {

    private boolean working = false;
    protected int count = 0, tier = -1;

    public TileEntityBeacon() {
        super(TileEntityTypeInit.BEACON_TE.get());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.working = nbt.getBoolean("working");
        this.tier = nbt.getInt("tier");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putBoolean("working", this.working);
        nbt.putInt("tier", this.tier);
        return super.save(nbt);
    }

    @Override
    public void tick() {
        this.working = !level.hasNeighborSignal(this.getBlockPos());
        if(!level.isClientSide()){
            if (working && this.tier > -1){
                work();
            }
        }else if (working && this.tier > -1){
            spawnParticles();
        }

    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.SpecialSpawn event){
        if(this.working && event.getEntityLiving().getType() == EntityType.ZOMBIE && checkRange(event.getEntityLiving())){
            double chance = Math.min(CybercraftConfig.C_MACHINES.zombieSpawnChance.get() + this.tier * CybercraftConfig.C_MACHINES.zombieSpawnChancePerLevel.get(), 1);
            Random rand = new Random();
            if (rand.nextDouble() < chance){
                CyberZombieEntity cyberZombie = EntityTypeInit.CYBER_ZOMBIE.get().create(this.level);
                if (rand.nextDouble() < CybercraftConfig.C_MACHINES.brutesChance.get()){
                    cyberZombie.setBrute();
                }
                LivingEntity shouldSpawn = event.getEntityLiving();
                cyberZombie.setPos(shouldSpawn.getX(), shouldSpawn.getY(), shouldSpawn.getZ());
                System.out.println(shouldSpawn.getX() + "|" + shouldSpawn.getY() + "|" + shouldSpawn.getZ());
                this.level.addFreshEntity(cyberZombie);
                event.setCanceled(true);
            }
        }
    }

    protected boolean checkRange(LivingEntity entity){
        double xDiff = Math.abs(((double)this.getBlockPos().getX()) - entity.getX());
        double yDiff = Math.abs(((double)this.getBlockPos().getY()) - entity.getY());
        double zDiff = Math.abs(((double)this.getBlockPos().getZ()) - entity.getZ());
        double range = CybercraftConfig.C_MACHINES.radioBaseRange.get() + this.tier * CybercraftConfig.C_MACHINES.radioRangePerTier.get();
        return xDiff <= range && yDiff <= range && zDiff <= range;
    }

    protected void work(){

    }
    @OnlyIn(Dist.CLIENT)
    protected void spawnParticles(){
        BlockState state = this.getBlockState();
        boolean ns = state.getValue(BeaconLargeBlock.FACING) == Direction.NORTH
                || state.getValue(BeaconLargeBlock.FACING) == Direction.SOUTH;
        boolean backwards = state.getValue(BeaconLargeBlock.FACING) == Direction.SOUTH
                || state.getValue(BeaconLargeBlock.FACING) == Direction.EAST;
        float dist = .2F;
        float speedMod = .08F;
        int degrees = 45;
        for (int index = 0; index < 5; index++) {
            float sin = (float) Math.sin(Math.toRadians(degrees));
            float cos = (float) Math.cos(Math.toRadians(degrees));
            float xOffset = dist * sin;
            float yOffset = .2F + dist * cos;
            float xSpeed = speedMod * sin;
            float ySpeed = speedMod * cos;
            float backOffsetX = (backwards ^ ns ? .3F : -.3F);
            float backOffsetZ = (backwards ? -.4F : .4F);

            level.addParticle(ParticleTypes.SMOKE,
                    worldPosition.getX() + .5F + (ns ? xOffset + backOffsetX : backOffsetZ),
                    worldPosition.getY() + .5F + yOffset,
                    worldPosition.getZ() + .5F + (ns ? backOffsetZ : xOffset + backOffsetX),
                    ns ? xSpeed : 0,
                    ySpeed,
                    ns ? 0 : xSpeed);

            level.addParticle(ParticleTypes.SMOKE,
                    worldPosition.getX() + .5F + (ns ? -xOffset + backOffsetX : backOffsetZ),
                    worldPosition.getY() + .5F + yOffset,
                    worldPosition.getZ() + .5F + (ns ? backOffsetZ : -xOffset + backOffsetX),
                    ns ? -xSpeed : 0,
                    ySpeed,
                    ns ? 0 : -xSpeed);

            degrees += 18;
        }
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
                    boolean ns = state.getValue(BeaconLargeBlock.FACING) == Direction.NORTH
                            || state.getValue(BeaconLargeBlock.FACING) == Direction.SOUTH;
                    boolean backwards = state.getValue(BeaconLargeBlock.FACING) == Direction.SOUTH
                            || state.getValue(BeaconLargeBlock.FACING) == Direction.EAST;
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
                                worldPosition.getX() + .5F + (ns ? xOffset + backOffsetX : backOffsetZ),
                                worldPosition.getY() + .5F + yOffset,
                                worldPosition.getZ() + .5F + (ns ? backOffsetZ : xOffset + backOffsetX),
                                ns ? xSpeed : 0,
                                ySpeed,
                                ns ? 0 : xSpeed);

                        level.addParticle(ParticleTypes.SMOKE,
                                worldPosition.getX() + .5F + (ns ? -xOffset + backOffsetX : backOffsetZ),
                                worldPosition.getY() + .5F + yOffset,
                                worldPosition.getZ() + .5F + (ns ? backOffsetZ : -xOffset + backOffsetX),
                                ns ? -xSpeed : 0,
                                ySpeed,
                                ns ? 0 : -xSpeed);

                        degrees += 18;
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
            mapBeaconPosition.put(getBlockPos(), LibConstants.BEACON_RANGE);
        }
    }

    @Override
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

        Item itemStackRadioRaw = ItemInit.BRAIN_UPGRADES_RADIO.get();
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

    public void setTier(int tier) {
        this.tier = tier;
    }

    public boolean isWorking() {
        return working;
    }
}
