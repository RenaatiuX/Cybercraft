package com.rena.cybercraft.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public enum ArmorClass {

    NONE(),
    LIGHT(),
    HEAVY;

    public static boolean isWearingLightOrNone(LivingEntity entityLivingBase) {
        return get(entityLivingBase) != HEAVY;
    }

    public static ArmorClass get(@Nonnull LivingEntity entityLivingBase) {
        boolean hasNoArmor = true;

        for (ItemStack stack : entityLivingBase.getArmorSlots()) {
            if (stack.isEmpty()) continue;
            hasNoArmor = false;

            if (stack.getItem() instanceof ArmorItem) {
                if (((ArmorItem) stack.getItem()).getMaterial().getDefenseForSlot(EquipmentSlotType.CHEST) > 4) {
                    return HEAVY;
                }
            }

            /*if (stack.getItem() instanceof ISpecialArmor)
            {
                if (((ISpecialArmor) stack.getItem()).getProperties(entityLivingBase, stack, DamageSource.CACTUS, 1, 1).AbsorbRatio * 25D > 4)
                {
                    return HEAVY;
                }
            }*/
        }
        return hasNoArmor ? NONE : LIGHT;
    }

}
