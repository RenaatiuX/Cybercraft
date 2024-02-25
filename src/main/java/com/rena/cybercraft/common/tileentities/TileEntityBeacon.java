package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.common.block.BeaconLargeBlock;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import com.rena.cybercraft.core.init.EntityTypeInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

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

    public void setTier(int tier) {
        this.tier = tier;
    }

    public boolean isWorking() {
        return working;
    }
}
