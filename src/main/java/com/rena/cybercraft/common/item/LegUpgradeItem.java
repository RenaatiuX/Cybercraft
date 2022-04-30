package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LegUpgradeItem extends CybercraftItem {

    private static final int META_JUMP_BOOST = 0;
    private static final int META_FALL_DAMAGE = 1;

    public LegUpgradeItem(Properties properties, EnumSlot slot, Quality q) {
        super(properties, slot, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public NonNullList<Item> requiredInstalledItems() {
        return NonNullList.of(ItemInit.CYBER_LIMB_LEG_RIGHT.get(), ItemInit.CYBER_LIMB_LEG_LEFT.get());
    }

    @SubscribeEvent
    public void playerJumps(LivingEvent.LivingJumpEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackJumpBoost = cyberwareUserData.getCybercraft(ItemInit.LEG_JUMP_BOOST.get());
        if (!itemStackJumpBoost.isEmpty()) {
            int numLegs = 0;
            if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_RIGHT.get())) {
                numLegs++;
            }
            if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_LEG_LEFT.get())) {
                numLegs++;
            }
            if (cyberwareUserData.usePower(itemStackJumpBoost, getPowerConsumption(itemStackJumpBoost))) {
                if (entityLivingBase.isShiftKeyDown()) {
                    Vector3d vector = entityLivingBase.getViewVector(0.5F);
                    double total = Math.abs(vector.z + vector.x);
                    double jump = 0;
                    if (jump >= 1) {
                        jump = (jump + 2D) / 4D;
                    }

                    double y = vector.y < total ? total : vector.y;
                    Vector3d motion = entityLivingBase.getDeltaMovement();
                    motion = motion.add((jump + 1) * vector.x * numLegs, numLegs * ((jump + 1) * y) / 3F, (jump + 1) * vector.z * numLegs);
                    entityLivingBase.setDeltaMovement(motion);
                } else {
                    Vector3d motion = entityLivingBase.getDeltaMovement().add(0f, numLegs * (0.2750000059604645D / 2D), 0f);
                    entityLivingBase.setDeltaMovement(motion);
                }
            }
        }
    }

    @SubscribeEvent
    public void onFallDamage(LivingAttackEvent event) {
        if (event.getSource() != DamageSource.FALL
                || event.getAmount() > 6F) {
            return;
        }

        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        if (cyberwareUserData.isCybercraftInstalled(ItemInit.LEG_FALL_DAMAGE.get())) {
            event.setCanceled(true);
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return stack.getItem() == ItemInit.LEG_JUMP_BOOST.get() ? LibConstants.JUMPBOOST_CONSUMPTION : 0;
    }
}
