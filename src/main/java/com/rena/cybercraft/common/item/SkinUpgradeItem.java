package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.EffectInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class SkinUpgradeItem extends CybercraftItem{

    public static final int META_SOLARSKIN = 0;
    public static final int META_SUBDERMAL_SPIKES = 1;
    public static final int META_SYNTHETIC_SKIN = 2;
    public static final int META_IMMUNOSUPPRESSANT = 3;

    public SkinUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.ticksExisted % 20 != 0) return;

        float lightFactor = getLightFactor(entityLivingBase);
        if (lightFactor <= 0.0F) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackSolarskin = cyberwareUserData.getCybercraft(getCachedStack(META_SOLARSKIN));
        if (!itemStackSolarskin.isEmpty())
        {
            int power = Math.max(0, Math.round(getPowerProduction(itemStackSolarskin) * lightFactor));
            cyberwareUserData.addPower(power, itemStackSolarskin);
        }
    }

    private float getLightFactor(LivingEntity entityLivingBase)
    {
        World world = entityLivingBase.level;
        // world must have a sun
        if (!entityLivingBase.level.provider.hasSkyLight()) return 0.0F;
        // current position can see the sun
        BlockPos pos = new BlockPos(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.height, entityLivingBase.posZ);
        if (!entityLivingBase.level.canBlockSeeSky(pos)) return 0.0F;

        // sun isn't shaded
        int lightSky = world.getLightFor(EnumSkyBlock.SKY, pos);
        // note: world.getSkylightSubtracted() is server side only
        if (lightSky < 15) return 0.0F;

        // it's day time (see Vanilla daylight sensor)
        float celestialAngleRadians = world.getCelestialAngleRadians(1.0F);
        float offsetRadians = celestialAngleRadians < (float) Math.PI ? 0.0F : ((float) Math.PI * 2.0F);
        float celestialAngleRadians2 = celestialAngleRadians + (offsetRadians - celestialAngleRadians) * 0.2F;
        return MathHelper.cos(celestialAngleRadians2);
    }

    private Set<UUID> setIsImmunosuppressantPowered = new HashSet<>();
    private static Map<UUID, Collection<Effect>> mapPotions = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void handleMissingEssentials(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackImmunosuppressant = cyberwareUserData.getCybercraft(getCachedStack(META_IMMUNOSUPPRESSANT));
        if (!itemStackImmunosuppressant.isEmpty())
        {
            boolean isPowered = entityLivingBase.ticksExisted % 20 == 0
                    ? cyberwareUserData.usePower(itemStackImmunosuppressant, getPowerConsumption(itemStackImmunosuppressant))
                    : setIsImmunosuppressantPowered.contains(entityLivingBase.getUUID());

            if ( !isPowered
                    && entityLivingBase instanceof PlayerEntity
                    && entityLivingBase.ticksExisted % 100 == 0
                    && !entityLivingBase.isPotionActive(EffectInit.NEUROPOZYNE.get()) )
            {
                entityLivingBase.attackEntityFrom(EssentialsMissingHandler.lowessence, 2F);
            }

            if (mapPotions.containsKey(entityLivingBase.getUUID()))
            {
                Collection<Effect> potionsLastActive = mapPotions.get(entityLivingBase.getUUID());
                Collection<Effect> currentEffects = entityLivingBase.getActivePotionEffects();
                for (PotionEffect potionEffectCurrent : currentEffects)
                {
                    if ( potionEffectCurrent.getPotion() == MobEffects.POISON
                            || potionEffectCurrent.getPotion() == MobEffects.HUNGER )
                    {
                        boolean found = false;
                        for (PotionEffect potionEffectLast : potionsLastActive)
                        {
                            if ( potionEffectLast.getPotion() == potionEffectCurrent.getPotion()
                                    && potionEffectLast.getAmplifier() == potionEffectCurrent.getAmplifier() )
                            {
                                found = true;
                                break;
                            }
                        }

                        if (!found)
                        {
                            entityLivingBase.addEffect(new EffectInstance(potionEffectCurrent.getPotion(),
                                    (int) (potionEffectCurrent.getDuration() * 1.8F),
                                    potionEffectCurrent.getAmplifier(),
                                    potionEffectCurrent.getIsAmbient(),
                                    potionEffectCurrent.doesShowParticles() ));
                        }
                    }
                }
            }

            if (isPowered)
            {
                setIsImmunosuppressantPowered.add(entityLivingBase.getUUID());
            }
            else
            {
                setIsImmunosuppressantPowered.remove(entityLivingBase.getUUID());
            }

            mapPotions.put(entityLivingBase.getUUID(), entityLivingBase.getActivePotionEffects());
        }
        else if (entityLivingBase.ticksExisted % 20 == 0)
        {
            setIsImmunosuppressantPowered.remove(entityLivingBase.getUUID());
            mapPotions.remove(entityLivingBase.getUUID());
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getDamageValue() == META_IMMUNOSUPPRESSANT ? LibConstants.IMMUNO_CONSUMPTION : 0;
    }

    @SubscribeEvent
    public void handleHurt(LivingHurtEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_SUBDERMAL_SPIKES)))
        {
            if ( event.getSource() instanceof EntityDamageSource
                    && !(event.getSource() instanceof EntityDamageSourceIndirect) )
            {
                ArmorClass armorClass = ArmorClass.get(entityLivingBase);
                if (armorClass == ArmorClass.HEAVY) return;

                Random random = entityLivingBase.getRNG();
                Entity attacker = event.getSource().getTrueSource();
                if (EnchantmentThorns.shouldHit(3, random))
                {
                    if (attacker != null)
                    {
                        attacker.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBase), (float) EnchantmentThorns.getDamage(2, random));
                    }
                }
            }
        }
    }

    @Override
    public int getPowerProduction(ItemStack stack)
    {
        return stack.getDamageValue() == META_SOLARSKIN ? LibConstants.SOLAR_PRODUCTION : 0;
    }
}
