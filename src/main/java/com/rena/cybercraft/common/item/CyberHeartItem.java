package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.events.EssentialsMissingHandler;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CyberHeartItem extends CybercraftItem{

    public CyberHeartItem(Properties properties, EnumSlot slots) {
        super(properties, slots);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isEssential(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other)
    {
        return CybercraftAPI.getCybercraft(other).isEssential(other);
    }

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void power(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();
        ItemStack itemStackCyberheart = cyberwareUserData.getCybercraft(getCachedStack(0));

        if (!itemStackCyberheart.isEmpty())
        {
            if (!cyberwareUserData.usePower(itemStackCyberheart, getPowerConsumption(itemStackCyberheart)))
            {
                entityLivingBase.hurt(EssentialsMissingHandler.heartless, Integer.MAX_VALUE);
            }
            else if (entityLivingBase.hasEffect(Effects.WEAKNESS))
            {
                entityLivingBase.removeEffect(Effects.WEAKNESS);
            }
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return LibConstants.HEART_CONSUMPTION;
    }
}
