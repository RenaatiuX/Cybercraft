package com.rena.cybercraft.common.item;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.ParticlePacket;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeartUpgradeItem extends CybercraftItem {

    public static final int META_INTERNAL_DEFIBRILLATOR = 0;
    public static final int META_PLATELET_DISPATCHER = 1;
    public static final int META_STEM_CELL_SYNTHESIZER = 2;
    public static final int META_CARDIOVASCULAR_COUPLER = 3;

    private static Map<UUID, Integer> timesPlatelets = new HashMap<>();

    public HeartUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack comparison) {
        return comparison.getItem() == ItemInit.CYBER_HEART.get() && (stack.getDamageValue() == META_INTERNAL_DEFIBRILLATOR || stack.getDamageValue() == META_CARDIOVASCULAR_COUPLER);
    }

    @SubscribeEvent
    public void handleDeath(LivingDeathEvent event) {
        if (event.isCanceled()) return;
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackInternalDefibrillator = cyberwareUserData.getCybercraft(getCachedStack(META_INTERNAL_DEFIBRILLATOR));
        if (!itemStackInternalDefibrillator.isEmpty()) {
            if ((!CybercraftAPI.getCybercraftNBT(itemStackInternalDefibrillator).contains("used"))
                    && cyberwareUserData.usePower(itemStackInternalDefibrillator, getPowerConsumption(itemStackInternalDefibrillator), false)) {
                if (entityLivingBase instanceof PlayerEntity) {
                    NonNullList<ItemStack> items = cyberwareUserData.getInstalledCybercraft(EnumSlot.HEART);
                    NonNullList<ItemStack> itemsNew = NonNullList.create();
                    itemsNew.addAll(items);
                    for (int index = 0; index < items.size(); index++) {
                        ItemStack item = items.get(index);
                        if (!item.isEmpty()
                                && item.getItem() == this
                                && CybercraftAPI.getMetaData(item) == META_INTERNAL_DEFIBRILLATOR) {
                            itemsNew.set(index, ItemStack.EMPTY);
                            break;
                        }
                    }
                    cyberwareUserData.setInstalledCybercraft(entityLivingBase, EnumSlot.HEART, itemsNew);
                    cyberwareUserData.updateCapacity();
                    if (!entityLivingBase.level.isClientSide) {
                        CybercraftAPI.updateData(entityLivingBase);
                    }
                } else {
                    itemStackInternalDefibrillator = cyberwareUserData.getCybercraft(itemStackInternalDefibrillator);
                    CompoundNBT tagCompoundCyberware = CybercraftAPI.getCybercraftNBT(itemStackInternalDefibrillator);
                    tagCompoundCyberware.putBoolean("used", true);

                    CybercraftAPI.updateData(entityLivingBase);
                }
                entityLivingBase.setHealth(entityLivingBase.getMaxHealth() / 3F);
                CCNetwork.sendToPlayerInTRange(new PacketDistributor.TargetPoint(entityLivingBase.getX(), entityLivingBase.getY(), entityLivingBase.getZ(), 20, entityLivingBase.level.dimension()),
                        new ParticlePacket(1, (float) entityLivingBase.getX(), (float) entityLivingBase.getY() + entityLivingBase.getEyeHeight() / 2F, (float) entityLivingBase.getZ()));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void handleLivingUpdate(CybercraftUpdateEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackStemCellSynthesizer = cyberwareUserData.getCybercraft(getCachedStack(META_STEM_CELL_SYNTHESIZER));
        if (entityLivingBase.tickCount % 20 == 0) {
            if (!itemStackStemCellSynthesizer.isEmpty()) {
                isStemWorking.put(entityLivingBase.getUUID(), cyberwareUserData.usePower(itemStackStemCellSynthesizer, getPowerConsumption(itemStackStemCellSynthesizer)));
            }
        }

        ItemStack itemStackPlateletDispatcher = cyberwareUserData.getCybercraft(getCachedStack(META_PLATELET_DISPATCHER));
        if (entityLivingBase.tickCount % 20 == 0
                && !itemStackPlateletDispatcher.isEmpty()) {
            isPlateletWorking.put(entityLivingBase.getUUID(), cyberwareUserData.usePower(itemStackPlateletDispatcher, getPowerConsumption(itemStackPlateletDispatcher)));
        }

        if (isPlateletWorking(entityLivingBase)
                && !itemStackPlateletDispatcher.isEmpty()) {
            if (entityLivingBase.getHealth() >= entityLivingBase.getMaxHealth() * .8F
                    && entityLivingBase.getHealth() != entityLivingBase.getMaxHealth()) {
                int t = getPlateletTime(entityLivingBase);
                if (t >= 40) {
                    timesPlatelets.put(entityLivingBase.getUUID(), entityLivingBase.tickCount);
                    entityLivingBase.heal(1);
                }
            } else {
                timesPlatelets.put(entityLivingBase.getUUID(), entityLivingBase.tickCount);
            }
        } else {
            timesPlatelets.remove(entityLivingBase.getUUID());
        }

        if (!itemStackStemCellSynthesizer.isEmpty()) {
            if (isStemWorking(entityLivingBase)) {
                int t = getMedkitTime(entityLivingBase);
                if (t >= 100
                        && damageMedkit.get(entityLivingBase.getUUID()) > 0F) {
                    CCNetwork.sendToPlayerInTRange(new PacketDistributor.TargetPoint(entityLivingBase.getX(), entityLivingBase.getY(), entityLivingBase.getZ(), 20, entityLivingBase.level.dimension()),
                            new ParticlePacket(0, (float) entityLivingBase.getX(), (float) entityLivingBase.getY() + entityLivingBase.getEyeHeight() / 2F, (float) entityLivingBase.getZ()));

                    entityLivingBase.heal(damageMedkit.get(entityLivingBase.getUUID()));
                    timesMedkit.put(entityLivingBase.getUUID(), 0);
                    damageMedkit.put(entityLivingBase.getUUID(), 0F);
                }
            }
        }
		else
		{
			if (timesMedkit.containsKey(entityLivingBase.getUUID()))
			{
				timesMedkit.remove(entityLivingBase);
				damageMedkit.remove(entityLivingBase);
			}
		}
    }

    private static Map<UUID, Boolean> isPlateletWorking = new HashMap<>();

    private boolean isPlateletWorking(LivingEntity entityLivingBase) {
        if (!isPlateletWorking.containsKey(entityLivingBase.getUUID())) {
            isPlateletWorking.put(entityLivingBase.getUUID(), false);
            return false;
        }

        return isPlateletWorking.get(entityLivingBase.getUUID());
    }

    private static Map<UUID, Boolean> isStemWorking = new HashMap<>();

    private boolean isStemWorking(LivingEntity entityLivingBase) {
        if (!isStemWorking.containsKey(entityLivingBase.getUUID())) {
            isStemWorking.put(entityLivingBase.getUUID(), false);
            return false;
        }

        return isStemWorking.get(entityLivingBase.getUUID());
    }


    private static Map<UUID, Integer> timesMedkit = new HashMap<>();
    private static Map<UUID, Float> damageMedkit = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void handleHurt(LivingHurtEvent event) {
        if (event.isCanceled()) return;
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackStemCellSynthesizer = cyberwareUserData.getCybercraft(getCachedStack(META_STEM_CELL_SYNTHESIZER));
        if (!itemStackStemCellSynthesizer.isEmpty()) {
            float damageAmount = event.getAmount();
            DamageSource damageSrc = event.getSource();

            damageAmount = applyArmorCalculations(entityLivingBase, damageSrc, damageAmount);
            damageAmount = applyPotionDamageCalculations(entityLivingBase, damageSrc, damageAmount);
            damageAmount = Math.max(damageAmount - entityLivingBase.getAbsorptionAmount(), 0.0F);

            damageMedkit.put(entityLivingBase.getUUID(), damageAmount);
            timesMedkit.put(entityLivingBase.getUUID(), entityLivingBase.tickCount);
        }
    }

    // Stolen from EntityLivingBase
    protected float applyArmorCalculations(LivingEntity entityLivingBase, DamageSource source, float damage) {
        if (!source.isBypassArmor()) {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entityLivingBase.getAbsorptionAmount(), (float) entityLivingBase.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        return damage;
    }

    // Stolen from EntityLivingBase
    protected float applyPotionDamageCalculations(LivingEntity entityLivingBase, DamageSource source, float damage) {
        if (source.isBypassInvul()) {
            return damage;
        } else {
            if (entityLivingBase.hasEffect(Effects.DAMAGE_RESISTANCE) && source != DamageSource.OUT_OF_WORLD) {
                int i = (entityLivingBase.getEffect(Effects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5;
                int j = 25 - i;
                float f = damage * (float) j;
                damage = f / 25.0F;
            }

            if (damage <= 0.0F) {
                return 0.0F;
            } else {
                int k = EnchantmentHelper.getDamageProtection(entityLivingBase.getArmorSlots(), source);

                if (k > 0) {
                    damage = CombatRules.getDamageAfterMagicAbsorb(damage, (float) k);
                }

                return damage;
            }
        }
    }

    private int getPlateletTime(LivingEntity entityLivingBase) {
        if (entityLivingBase != null) {
            if (!timesPlatelets.containsKey(entityLivingBase.getUUID())) {
                timesPlatelets.put(entityLivingBase.getUUID(), entityLivingBase.tickCount);
                return 0;
            }
            return entityLivingBase.tickCount - timesPlatelets.get(entityLivingBase.getUUID());
        }
        return 0;
    }

    private int getMedkitTime(LivingEntity entityLivingBase) {
        if (entityLivingBase != null) {
            if (!timesMedkit.containsKey(entityLivingBase.getUUID())) {
                timesMedkit.put(entityLivingBase.getUUID(), entityLivingBase.tickCount);
                damageMedkit.put(entityLivingBase.getUUID(), 0F);
                return 0;
            }
            return entityLivingBase.tickCount - timesMedkit.get(entityLivingBase.getUUID());
        }
        return 0;
    }

    @SubscribeEvent
    public void power(CybercraftUpdateEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackCardiovascularCoupler = cyberwareUserData.getCybercraft(getCachedStack(META_CARDIOVASCULAR_COUPLER));
        if (!itemStackCardiovascularCoupler.isEmpty()) {
            cyberwareUserData.addPower(getPowerProduction(itemStackCardiovascularCoupler), itemStackCardiovascularCoupler);
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return stack.getDamageValue() == META_INTERNAL_DEFIBRILLATOR ? LibConstants.DEFIBRILLATOR_CONSUMPTION
                : stack.getDamageValue() == META_PLATELET_DISPATCHER ? LibConstants.PLATELET_CONSUMPTION
                : stack.getDamageValue() == META_STEM_CELL_SYNTHESIZER ? LibConstants.STEMCELL_CONSUMPTION
                : 0;
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return stack.getDamageValue() == META_INTERNAL_DEFIBRILLATOR ? LibConstants.DEFIBRILLATOR_CONSUMPTION : 0;
    }

    @Override
    public boolean hasCustomPowerMessage(ItemStack stack) {
        return stack.getDamageValue() == META_INTERNAL_DEFIBRILLATOR;
    }

    @Override
    public int getPowerProduction(ItemStack stack) {
        return stack.getDamageValue() == META_CARDIOVASCULAR_COUPLER ? LibConstants.COUPLER_PRODUCTION + 1 : 0;
    }

}
