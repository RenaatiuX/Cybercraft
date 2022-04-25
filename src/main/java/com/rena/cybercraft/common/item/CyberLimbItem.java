package com.rena.cybercraft.common.item;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class CyberLimbItem extends CybercraftItem implements ICybercraft.ISidedLimb {

    public static final int META_LEFT_CYBER_ARM         = 0;
    public static final int META_RIGHT_CYBER_ARM        = 1;
    public static final int META_LEFT_CYBER_LEG         = 2;
    public static final int META_RIGHT_CYBER_LEG        = 3;

    private Set<Integer> didFall = new HashSet<>();

    public CyberLimbItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isEssential(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack comparison) {
        ICybercraft ware = CybercraftAPI.getCybercraft(comparison);

        if (ware instanceof ISidedLimb)
        {
            return ware.isEssential(comparison) && ((ISidedLimb) ware).getSide(comparison) == this.getSide(stack);
        }
        return false;
    }

    @Override
    public EnumSide getSide(ItemStack stack) {
        return CybercraftAPI.getMetaData(stack) % 2 == 0 ? EnumSide.LEFT : EnumSide.RIGHT;
    }

    public static boolean isPowered(ItemStack stack)
    {
        CompoundNBT data = CybercraftAPI.getCybercraftNBT(stack);
        if (!data.contains("active"))
        {
            data.putBoolean("active", true);
        }
        return data.getBoolean("active");
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return LibConstants.LIMB_CONSUMPTION;
    }

    @SubscribeEvent
    public void handleFallDamage(LivingAttackEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if ( entityLivingBase.level.isClientSide() && event.getSource() == DamageSource.FALL )
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
            if (cyberwareUserData == null) return;
            if ( cyberwareUserData.isCybercraftInstalled(getCachedStack(META_LEFT_CYBER_LEG))
                    || cyberwareUserData.isCybercraftInstalled(getCachedStack(META_RIGHT_CYBER_LEG)) )
            {
                didFall.add(entityLivingBase.getId());
            }
        }
    }

    @SubscribeEvent
    public void handleSound(PlaySoundAtEntityEvent event)
    {
        Entity entity = event.getEntity();
        if ( entity instanceof PlayerEntity && event.getSound() == SoundEvents.PLAYER_HURT && entity.level.isClientSide )
        {
            if (didFall.contains(entity.getId()))
            {
                int numLegs = 0;

                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entity);
                if (cyberwareUserData == null) return;

                if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_LEFT_CYBER_LEG)))
                {
                    numLegs++;
                }

                if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_RIGHT_CYBER_LEG)))
                {
                    numLegs++;
                }

                if (numLegs > 0)
                {
                    event.setSound(SoundEvents.IRON_GOLEM_HURT);
                    event.setPitch(event.getPitch() + 1F);
                    didFall.remove(entity.getId());
                }
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void power(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();
        for (int damage = 0; damage < 4; damage++)
        {
            ItemStack itemStackInstalled = cyberwareUserData.getCybercraft(getCachedStack(damage));
            if (!itemStackInstalled.isEmpty())
            {
                boolean isPowered = cyberwareUserData.usePower(itemStackInstalled, getPowerConsumption(itemStackInstalled));

                CybercraftAPI.getCybercraftNBT(itemStackInstalled).putBoolean("active", isPowered);
            }
        }
    }
}
