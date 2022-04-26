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

    public ArmUpgradeItem(Properties properties, EnumSlot slots, String... subnames) {
        super(properties, slots, subnames);
    }

    @Override
    public NonNullList<NonNullList<ItemStack>> required(ItemStack stack) {
        NonNullList<NonNullList<ItemStack>> l1 = NonNullList.create();
        NonNullList<ItemStack> l2 = NonNullList.create();
        l2.add(ItemInit.CYBER_LIMBS.get().getCachedStack(CyberLimbItem.META_LEFT_CYBER_ARM));
        l2.add(ItemInit.CYBER_LIMBS.get().getCachedStack(CyberLimbItem.META_RIGHT_CYBER_ARM));
        l1.add(l2);
        return l1;
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

            if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_BOW)))
            {
                event.setDuration(event.getDuration() - 1);
            }
        }
    }
}
