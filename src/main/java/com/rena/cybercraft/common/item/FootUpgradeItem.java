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
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FootUpgradeItem extends CybercraftItem implements IMenuItem {

    public static final int META_SPURS = 0;
    public static final int META_AQUA = 1;
    public static final int META_WHEELS = 2;

    public FootUpgradeItem(Properties properties, EnumSlot slots, String... subnames) {
        super(properties, slots, subnames);
    }

    @Override
    public NonNullList<NonNullList<ItemStack>> required(ItemStack stack) {
        if (stack.getDamageValue() != META_AQUA) return NonNullList.create();

        return NNLUtil.fromArray(new ItemStack[][] {
                new ItemStack[] {
                        ItemInit.CYBER_LIMBS.get().getCachedStack(CyberLimbItem.META_LEFT_CYBER_LEG),
                        ItemInit.CYBER_LIMBS.get().getCachedStack(CyberLimbItem.META_RIGHT_CYBER_LEG) }});
    }

    @SubscribeEvent
    public void handleHorseMove(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase instanceof HorseEntity)
        {
            ItemStack itemStackSpurs = getCachedStack(META_SPURS);
            HorseEntity entityHorse = (HorseEntity) entityLivingBase;
            for (Entity entityPassenger : entityHorse.getPassengers())
            {
                if (entityPassenger instanceof LivingEntity)
                {
                    ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPassenger);
                    if ( cyberwareUserData != null
                            && cyberwareUserData.isCybercraftInstalled(itemStackSpurs) )
                    {
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

    /*@SubscribeEvent(priority= EventPriority.NORMAL)
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        if ( !entityLivingBase.isOnGround()
                && entityLivingBase.isInWater() )
        {
            ItemStack itemStackAqua = cyberwareUserData.getCybercraft(getCachedStack(META_AQUA));
            if (!itemStackAqua.isEmpty())
            {
                int numLegs = 0;
                if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMBS.get().getCachedStack(CyberLimbItem.META_LEFT_CYBER_LEG)))
                {
                    numLegs++;
                }
                if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMBS.get().getCachedStack(CyberLimbItem.META_RIGHT_CYBER_LEG)))
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
                        entityLivingBase.moveRelative(0F, 0F, numLegs * 0.4F, 0.075F);
                    }
                }

                mapIsAquaPowered.put(entityLivingBase.getUUID(), isPowered);
            }
        }
        else if (entityLivingBase.tickCount % 20 == 0)
        {
            mapIsAquaPowered.remove(entityLivingBase.getUUID());
        }

        ItemStack itemStackWheels = cyberwareUserData.getCybercraft(getCachedStack(META_WHEELS));
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
    }*/

    private int getCountdownWheelsPowered(LivingEntity entityLivingBase)
    {
        return mapCountdownWheelsPowered.computeIfAbsent(entityLivingBase.getUUID(), k -> 10);
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return stack.getDamageValue() == META_AQUA ? LibConstants.AQUA_CONSUMPTION :
                stack.getDamageValue() == META_WHEELS ? LibConstants.WHEEL_CONSUMPTION : 0;
    }

    @Override
    public boolean hasMenu(ItemStack stack) {
        return stack.getDamageValue() == META_WHEELS;
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
