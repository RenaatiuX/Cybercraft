package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class LowerOrgansUpgradeItem extends CybercraftItem implements IMenuItem {

    public static final int META_LIVER_FILTER = 0;
    public static final int META_METABOLIC_GENERATOR = 1;
    public static final int META_BATTERY = 2;
    public static final int META_ADRENALINE_PUMP = 3;

    public LowerOrgansUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static Map<UUID, Collection<Effect>> mapPotions = new HashMap<>();

    @SubscribeEvent
    public void handleEatFoodTick(LivingEntityUseItemEvent.Tick event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!(entityLivingBase instanceof PlayerEntity)) return;
        PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
        ItemStack stack = event.getItem();

        if ( !stack.isEmpty()
                && ( stack.getItem().getItemUseAction(stack) == EnumAction.EAT
                || stack.getItem().getItemUseAction(stack) == EnumAction.DRINK ) )
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && cyberwareUserData.isCybercraftInstalled(getCachedStack(META_LIVER_FILTER)))
            {
                mapPotions.put(entityPlayer.getUUID(), new ArrayList<>(entityPlayer.getActivePotionEffects()));
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
                && ( stack.getItem().getItemUseAction(stack) == EnumAction.EAT
                || stack.getItem().getItemUseAction(stack) == EnumAction.DRINK ) )
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && cyberwareUserData.isCybercraftInstalled(getCachedStack(META_LIVER_FILTER)))
            {
                Collection<Effect> potionEffectsRemoved = new ArrayList<>(entityPlayer.getActivePotionEffects());
                for (Effect potionEffect : potionEffectsRemoved)
                {
                    if (potionEffect.getEffect().isBadEffect())
                    {
                        entityPlayer.removeEffect(potionEffect.getEffect());
                    }
                }

                Collection<Effect> potionEffectsToAdd = mapPotions.get(entityPlayer.getUUID());
                if (potionEffectsToAdd != null)
                {
                    for (Effect potionEffectToAdd : potionEffectsToAdd)
                    {
                        for (Effect potionEffectRemoved : potionEffectsRemoved)
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
        if (entityLivingBase.ticksExisted % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackMetabolicGenerator = cyberwareUserData.getCybercraft(getCachedStack(META_METABOLIC_GENERATOR));
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

        ItemStack itemStackAdrenalinePump = cyberwareUserData.getCybercraft(getCachedStack(META_ADRENALINE_PUMP));
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
        return wareStack.getDamageValue() == META_METABOLIC_GENERATOR ? LibConstants.METABOLIC_PRODUCTION :
                wareStack.getDamageValue() == META_BATTERY ? LibConstants.BATTERY_CAPACITY * wareStack.getCount() : 0;
    }

    @Override
    public int installedStackSize(ItemStack stack)
    {
        return stack.getDamageValue() == META_BATTERY ? 4 : 1;
    }

    @Override
    public int getPowerProduction(ItemStack stack)
    {
        return stack.getDamageValue() == META_METABOLIC_GENERATOR ? LibConstants.METABOLIC_PRODUCTION : 0;
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getDamageValue() == META_ADRENALINE_PUMP ? LibConstants.ADRENALINE_CONSUMPTION : 0;
    }

    @Override
    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        if (stack.getDamageValue() == META_BATTERY)
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
        return stack.getDamageValue() == META_METABOLIC_GENERATOR;
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
