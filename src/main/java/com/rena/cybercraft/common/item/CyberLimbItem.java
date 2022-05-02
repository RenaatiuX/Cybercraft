package com.rena.cybercraft.common.item;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

    private Set<Integer> didFall = new HashSet<>();
    private final EnumSide side;

    public CyberLimbItem(Properties properties, EnumSlot slot, EnumSide side, Quality q) {
        super(properties, slot, q);
        this.side = side;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isEssential(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack comparison) {
        ICybercraft ware = CybercraftAPI.getCybercraft(comparison);

        if (ware instanceof ISidedLimb) {
            return ware.isEssential(comparison) && ((ISidedLimb) ware).getSide(comparison) == this.getSide(stack);
        }
        return false;
    }

    @Override
    public EnumSide getSide(ItemStack stack) {
        return side;
    }

    public static boolean isPowered(ItemStack stack) {
        CompoundNBT data = CybercraftAPI.getCybercraftNBT(stack);
        if (!data.contains("active")) {
            data.putBoolean("active", true);
        }
        return data.getBoolean("active");
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return LibConstants.LIMB_CONSUMPTION;
    }

    @SubscribeEvent
    public void handleFallDamage(LivingAttackEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.level.isClientSide() && event.getSource() == DamageSource.FALL) {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
            if (cyberwareUserData == null) return;
            if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_LEFT.get())
                    && cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_RIGHT.get())) {
                didFall.add(entityLivingBase.getId());
            }
        }
    }

    @SubscribeEvent
    public void handleSound(PlaySoundAtEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity && event.getSound() == SoundEvents.PLAYER_HURT && entity.level.isClientSide) {
            if (didFall.contains(entity.getId())) {
                int numLegs = 0;

                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entity);
                if (cyberwareUserData == null) return;

                //left leg
                if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_LEFT.get())) {
                    numLegs++;
                }
                //right Leg
                if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_RIGHT.get())) {
                    numLegs++;
                }

                if (numLegs > 0) {
                    event.setSound(SoundEvents.IRON_GOLEM_HURT);
                    event.setPitch(event.getPitch() + 1F);
                    didFall.remove(entity.getId());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void power(CybercraftUpdateEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();
        for (int damage = 0; damage < 4; damage++) {
            ItemStack itemStackInstalled = cyberwareUserData.getCybercraft(this);
            if (!itemStackInstalled.isEmpty()) {
                boolean isPowered = cyberwareUserData.usePower(itemStackInstalled, getPowerConsumption(itemStackInstalled));

                CybercraftAPI.getCybercraftNBT(itemStackInstalled).putBoolean("active", isPowered);
            }
        }
    }
}
