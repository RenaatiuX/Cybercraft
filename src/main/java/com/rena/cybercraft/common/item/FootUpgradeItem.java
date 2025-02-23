package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FootUpgradeItem extends CybercraftItem implements IMenuItem {

    public FootUpgradeItem(Properties properties, EnumSlot slot, Quality q) {
        super(properties, slot, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public NonNullList<Item> requiredInstalledItems() {
        if (this.getItem() != ItemInit.FOOT_UPGRADES_AQUA.get()) return NonNullList.create();

        return NonNullList.of(
                        ItemInit.CYBER_LIMB_LEG_LEFT.get(),
                ItemInit.CYBER_LIMB_LEG_RIGHT.get());
    }

    @SubscribeEvent
    public void handleHorseMove(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase instanceof HorseEntity) {
            HorseEntity entityHorse = (HorseEntity) entityLivingBase;
            for (Entity entityPassenger : entityHorse.getPassengers()) {
                if (entityPassenger instanceof LivingEntity) {
                    ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPassenger);
                    if ( cyberwareUserData != null && cyberwareUserData.isCybercraftInstalled(ItemInit.FOOT_UPGRADES_SPURS.get()) ) {
                        entityHorse.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1, 5, true, false));
                        break;
                    }
                }
            }
        }
    }

    private Map<UUID, Boolean> mapIsAquaPowered = new HashMap<>();
    private Map<UUID, Integer> mapCountdownWheelsPowered = new HashMap<>();
    private Map<UUID, Float> mapStepHeight = new HashMap<>();

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        if ( !entityLivingBase.isOnGround()
                && entityLivingBase.isInWater() )
        {
            ItemStack itemStackAqua = cyberwareUserData.getCybercraft(ItemInit.FOOT_UPGRADES_AQUA.get());
            if (!itemStackAqua.isEmpty())
            {
                int numLegs = 0;
                if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_LEFT.get()))
                {
                    numLegs++;
                }
                if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_RIGHT.get()))
                {
                    numLegs++;
                }
                boolean wasPowered = mapIsAquaPowered.computeIfAbsent(entityLivingBase.getUUID(), k -> Boolean.TRUE);

                boolean isPowered = entityLivingBase.tickCount % 20 == 0
                        ? cyberwareUserData.usePower(itemStackAqua, getPowerConsumption(itemStackAqua))
                        : wasPowered;
                if (isPowered)
                {
                    if (entityLivingBase.zza > 0)
                    {
                        entityLivingBase.moveRelative(0F, new Vector3d(0F, numLegs * 0.4F, 0.075F));
                    }
                }

                mapIsAquaPowered.put(entityLivingBase.getUUID(), isPowered);
            }
        }
        else if (entityLivingBase.tickCount % 20 == 0)
        {
            mapIsAquaPowered.remove(entityLivingBase.getUUID());
        }

        ItemStack itemStackWheels = cyberwareUserData.getCybercraft(ItemInit.FOOT_UPGRADES_WHEELS.get());
        if (!itemStackWheels.isEmpty())
        {
            boolean wasPowered = getCountdownWheelsPowered(entityLivingBase) > 0;

            boolean isPowered = EnableDisableHelper.isEnabled(itemStackWheels)
                    && ( entityLivingBase.tickCount % 20 == 0
                    ? cyberwareUserData.usePower(itemStackWheels, getPowerConsumption(itemStackWheels))
                    : wasPowered );
            if (isPowered)
            {
                if (!mapStepHeight.containsKey(entityLivingBase.getUUID()))
                {
                    mapStepHeight.put(entityLivingBase.getUUID(), Math.max(entityLivingBase.maxUpStep, .6F));
                }
                entityLivingBase.maxUpStep = 1F;

                mapCountdownWheelsPowered.put(entityLivingBase.getUUID(), 10);
            }
            else if (mapStepHeight.containsKey(entityLivingBase.getUUID()) && wasPowered)
            {
                entityLivingBase.maxUpStep = mapStepHeight.get(entityLivingBase.getUUID());

                mapCountdownWheelsPowered.put(entityLivingBase.getUUID(), getCountdownWheelsPowered(entityLivingBase) - 1);
            }
            else
            {
                mapCountdownWheelsPowered.put(entityLivingBase.getUUID(), 0);
            }
        }
        else if (mapStepHeight.containsKey(entityLivingBase.getUUID()))
        {
            entityLivingBase.maxUpStep = mapStepHeight.get(entityLivingBase.getUUID());

            int countdownWheelsPowered = getCountdownWheelsPowered(entityLivingBase) - 1;
            if (countdownWheelsPowered == 0)
            {
                mapStepHeight.remove(entityLivingBase.getUUID());
            }

            mapCountdownWheelsPowered.put(entityLivingBase.getUUID(), countdownWheelsPowered);
        }
    }

    private int getCountdownWheelsPowered(LivingEntity entityLivingBase)
    {
        return mapCountdownWheelsPowered.computeIfAbsent(entityLivingBase.getUUID(), k -> 10);
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return stack.getItem() == ItemInit.FOOT_UPGRADES_AQUA.get() ? LibConstants.AQUA_CONSUMPTION :
                stack.getItem() == ItemInit.FOOT_UPGRADES_WHEELS.get() ? LibConstants.WHEEL_CONSUMPTION : 0;
    }

    @Override
    public boolean hasMenu(ItemStack stack) {
        return stack.getItem() == ItemInit.FOOT_UPGRADES_WHEELS.get();
    }

    @Override
    public void use(Entity entity, ItemStack stack) {
        EnableDisableHelper.toggle(stack);
    }

    @Override
    public String getUnlocalizedLabel(ItemStack stack) {
        return EnableDisableHelper.getUnlocalizedLabel(stack);
    }

    private static final float[] f = new float[] { 1.0F, 0.0F, 0.0F };

    @Override
    public float[] getColor(ItemStack stack) {
        return EnableDisableHelper.isEnabled(stack) ? f : null;
    }
}
