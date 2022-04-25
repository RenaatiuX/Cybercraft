package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.common.util.NNLUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LegUpgradeItem extends CybercraftItem{

    private static final int META_JUMP_BOOST = 0;
    private static final int META_FALL_DAMAGE = 1;

    public LegUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public NonNullList<NonNullList<ItemStack>> required(ItemStack stack) {
        return NNLUtil.fromArray(new ItemStack[][] {
                new ItemStack[] { CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_LEFT_CYBER_LEG),
                        CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_RIGHT_CYBER_LEG) }});
    }

    @SubscribeEvent
    public void playerJumps(LivingEvent.LivingJumpEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackJumpBoost = cyberwareUserData.getCybercraft(getCachedStack(META_JUMP_BOOST));
        if (!itemStackJumpBoost.isEmpty())
        {
            int numLegs = 0;
            if (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_LEFT_CYBER_LEG)))
            {
                numLegs++;
            }
            if (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_RIGHT_CYBER_LEG)))
            {
                numLegs++;
            }
            if (cyberwareUserData.usePower(itemStackJumpBoost, getPowerConsumption(itemStackJumpBoost)))
            {
                if (entityLivingBase.isShiftKeyDown())
                {
                    Vec3d vector = entityLivingBase.getLook(0.5F);
                    double total = Math.abs(vector.z + vector.x);
                    double jump = 0;
                    if (jump >= 1)
                    {
                        jump = (jump + 2D) / 4D;
                    }

                    double y = vector.y < total ? total : vector.y;

                    entityLivingBase.motionY += (numLegs * ((jump + 1) * y)) / 3F;
                    entityLivingBase.motionZ += (jump + 1) * vector.z * numLegs;
                    entityLivingBase.motionX += (jump + 1) * vector.x * numLegs;
                }
                else
                {
                    entityLivingBase.motionY += numLegs * (0.2750000059604645D / 2D);
                }
            }
        }
    }

    @SubscribeEvent
    public void onFallDamage(LivingAttackEvent event)
    {
        if ( event.getSource() != DamageSource.FALL
                || event.getAmount() > 6F )
        {
            return;
        }

        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_FALL_DAMAGE)))
        {
            event.setCanceled(true);
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getDamageValue() == META_JUMP_BOOST ? LibConstants.JUMPBOOST_CONSUMPTION : 0;
    }
}
