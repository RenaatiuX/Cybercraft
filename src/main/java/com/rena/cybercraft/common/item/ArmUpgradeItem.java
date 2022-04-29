package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmUpgradeItem extends CybercraftItem{

    public static final int META_BOW = 0;

    public ArmUpgradeItem(Properties properties, EnumSlot slot, Quality q) {
        super(properties, slot, q);
    }


    @SubscribeEvent
    public void useBow(LivingEntityUseItemEvent.Tick event)
    {
        ItemStack item = event.getItem();
        if ( !item.isEmpty()
                && item.getItem() instanceof BowItem)
        {
            LivingEntity livingEntity = event.getEntityLiving();
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(livingEntity);
            if (cyberwareUserData == null) return;

            if (cyberwareUserData.isCybercraftInstalled(ItemInit.ARM_UPGRADES_BOW.get()))
            {
                event.setDuration(event.getDuration() - 1);
            }
        }
    }
}
