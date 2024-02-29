package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class LowerOrgansUpgradeItem extends CybercraftItem implements IMenuItem {

    public LowerOrgansUpgradeItem(Properties properties, EnumSlot slots, Quality q) {
        super(properties, slots, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static Map<UUID, Collection<EffectInstance>> mapPotions = new HashMap<>();

    @SubscribeEvent
    public void handleEatFoodTick(LivingEntityUseItemEvent.Tick event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!(entityLivingBase instanceof PlayerEntity)) return;
        PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
        ItemStack stack = event.getItem();

        if ( !stack.isEmpty()
                && ( stack.getItem().getUseAnimation(stack) == UseAction.EAT
                || stack.getItem().getUseAnimation(stack) == UseAction.DRINK ) )
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && cyberwareUserData.isCybercraftInstalled(ItemInit.LOWER_ORGANS_UPGRADES_LIVER.get()))
            {
                mapPotions.put(entityPlayer.getUUID(), new ArrayList<>(entityPlayer.getActiveEffects()));
            }
        }
    }

    @SubscribeEvent
    public void handleEatFoodEnd(LivingEntityUseItemEvent.Finish event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!(entityLivingBase instanceof PlayerEntity)) return;
        PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
        ItemStack stack = event.getItem();

        if ( !stack.isEmpty()
                && ( stack.getItem().getUseAnimation(stack) == UseAction.EAT
                || stack.getItem().getUseAnimation(stack) == UseAction.DRINK ) )
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && cyberwareUserData.isCybercraftInstalled(ItemInit.LOWER_ORGANS_UPGRADES_LIVER.get()))
            {
                Collection<EffectInstance> potionEffectsRemoved = new ArrayList<>(entityPlayer.getActiveEffects());
                for (EffectInstance potionEffect : potionEffectsRemoved)
                {
                    if (potionEffect.getEffect().isBeneficial())
                    {
                        entityPlayer.removeEffect(potionEffect.getEffect());
                    }
                }

                Collection<EffectInstance> potionEffectsToAdd = mapPotions.get(entityPlayer.getUUID());
                if (potionEffectsToAdd != null)
                {
                    for (EffectInstance potionEffectToAdd : potionEffectsToAdd)
                    {
                        for (EffectInstance potionEffectRemoved : potionEffectsRemoved)
                        {
                            if (potionEffectRemoved.getEffect() == potionEffectToAdd.getEffect())
                            {
                                entityPlayer.addEffect(new EffectInstance(potionEffectToAdd));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void power(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackMetabolicGenerator = cyberwareUserData.getCybercraft(ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get());
        if ( !itemStackMetabolicGenerator.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackMetabolicGenerator) )
        {
            if (!cyberwareUserData.isAtCapacity(itemStackMetabolicGenerator, getPowerProduction(itemStackMetabolicGenerator)))
            {
                if (entityLivingBase instanceof PlayerEntity)
                {
                    PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
                    if ( entityPlayer.getFoodData().getFoodLevel() > 0
                            || entityPlayer.isCreative() )
                    {
                        int toRemove = getTicksTilRemove(itemStackMetabolicGenerator);
                        if (!entityPlayer.isCreative() && toRemove <= 0)
                        {
                            entityPlayer.getFoodData().addExhaustion(6.0F);
                            toRemove = LibConstants.METABOLIC_USES;
                        }
                        else if (toRemove > 0)
                        {
                            toRemove--;
                        }
                        CybercraftAPI.getCybercraftNBT(itemStackMetabolicGenerator).putInt("toRemove", toRemove);

                        cyberwareUserData.addPower(getPowerProduction(itemStackMetabolicGenerator), itemStackMetabolicGenerator);
                    }
                }
                else
                {
                    cyberwareUserData.addPower(getPowerProduction(itemStackMetabolicGenerator) / 10, itemStackMetabolicGenerator);
                }
            }
        }

        ItemStack itemStackAdrenalinePump = cyberwareUserData.getCybercraft(ItemInit.LOWER_ORGANS_UPGRADES_ADRENALINE.get());
        if (!itemStackAdrenalinePump.isEmpty())
        {
            boolean wasBelow = wasBelow(itemStackAdrenalinePump);
            boolean isBelow = false;
            if (entityLivingBase.getMaxHealth() > 8 && entityLivingBase.getHealth() < 8)
            {
                isBelow = true;

                if ( !wasBelow
                        && cyberwareUserData.usePower(itemStackAdrenalinePump, this.getPowerConsumption(itemStackAdrenalinePump), false) )
                {
                    entityLivingBase.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 600, 0, true, false));
                    entityLivingBase.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 600, 0, true, false));
                }
            }

            CybercraftAPI.getCybercraftNBT(itemStackAdrenalinePump).putBoolean("wasBelow", isBelow);
        }
    }

    private int getTicksTilRemove(ItemStack stack)
    {
        CompoundNBT data = CybercraftAPI.getCybercraftNBT(stack);
        if (!data.contains("toRemove"))
        {
            data.putInt("toRemove", LibConstants.METABOLIC_USES);
        }
        return data.getInt("toRemove");
    }

    private boolean wasBelow(ItemStack stack)
    {
        CompoundNBT data = CybercraftAPI.getCybercraftNBT(stack);
        if (!data.contains("wasBelow"))
        {
            data.putBoolean("wasBelow", false);
        }
        return data.getBoolean("wasBelow");
    }

    @Override
    public int getCapacity(ItemStack wareStack)
    {
        return wareStack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get() ? LibConstants.METABOLIC_PRODUCTION :
                wareStack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get() ? LibConstants.BATTERY_CAPACITY * wareStack.getCount() : 0;
    }

    @Override
    public int maxInstalledStackSize(ItemStack stack)
    {
        return stack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get() ? 4 : 1;
    }

    @Override
    public int getPowerProduction(ItemStack stack)
    {
        return stack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get() ? LibConstants.METABOLIC_PRODUCTION : 0;
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_ADRENALINE.get() ? LibConstants.ADRENALINE_CONSUMPTION : 0;
    }

    @Override
    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        if (stack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get())
        {
            switch (stack.getCount())
            {
                case 1:
                    return 5;
                case 2:
                    return 7;
                case 3:
                    return 9;
                case 4:
                    return 11;
            }
        }
        return super.getUnmodifiedEssenceCost(stack);
    }

    @Override
    public boolean hasMenu(ItemStack stack)
    {
        return stack.getItem() == ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get();
    }

    @Override
    public void use(Entity entity, ItemStack stack)
    {
        EnableDisableHelper.toggle(stack);
    }

    @Override
    public String getUnlocalizedLabel(ItemStack stack)
    {
        return EnableDisableHelper.getUnlocalizedLabel(stack);
    }

    private static final float[] f = new float[] { 1.0F, 0.0F, 0.0F };

    @Override
    public float[] getColor(ItemStack stack)
    {
        return EnableDisableHelper.isEnabled(stack) ? f : null;
    }
}
