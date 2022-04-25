package com.rena.cybercraft.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.network.SwitchHeldItemAndRotationPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MuscleUpgradeItem extends CybercraftItem implements IMenuItem {

    private static final int META_WIRED_REFLEXES          = 0;
    private static final int META_MUSCLE_REPLACEMENTS     = 1;

    private static final UUID idMuscleSpeedAttribute = UUID.fromString("f0ab4766-4be1-11e6-beb8-9e71128cae77");
    private static final UUID idMuscleDamageAttribute = UUID.fromString("f63d6916-4be1-11e6-beb8-9e71128cae77");
    private static final HashMultimap<String, AttributeModifier> multimapMuscleSpeedAttribute;
    private static final HashMultimap<String, AttributeModifier> multimapMuscleDamageAttribute;

    static {
        multimapMuscleSpeedAttribute = HashMultimap.create();
        multimapMuscleSpeedAttribute.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(idMuscleSpeedAttribute, "Muscle speed upgrade", 1.5F, 0));
        multimapMuscleDamageAttribute = HashMultimap.create();
        multimapMuscleDamageAttribute.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(idMuscleDamageAttribute, "Muscle damage upgrade", 3F, 0));
    }

    public MuscleUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onAdded(LivingEntity entityLivingBase, ItemStack stack)
    {
        if (stack.getDamageValue() == META_WIRED_REFLEXES)
        {
            entityLivingBase.getAttributeMap().applyAttributeModifiers(multimapMuscleSpeedAttribute);
        }
        else if (stack.getDamageValue() == META_MUSCLE_REPLACEMENTS)
        {
            entityLivingBase.getAttributeMap().applyAttributeModifiers(multimapMuscleDamageAttribute);
        }
    }

    @Override
    public void onRemoved(LivingEntity entityLivingBase, ItemStack stack)
    {
        if (stack.getDamageValue() == META_WIRED_REFLEXES)
        {
            entityLivingBase.getAttributeMap().removeAttributeModifiers(multimapMuscleSpeedAttribute);
        }
        else if (stack.getDamageValue() == META_MUSCLE_REPLACEMENTS)
        {
            entityLivingBase.getAttributeMap().removeAttributeModifiers(multimapMuscleDamageAttribute);
        }
    }

    @Override
    public int installedStackSize(ItemStack stack)
    {
        return stack.getDamageValue() == META_WIRED_REFLEXES ? 3 : 1;
    }

    @SubscribeEvent
    public void handleHurt(LivingHurtEvent event)
    {
        if (event.isCanceled()) return;
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!(entityLivingBase instanceof PlayerEntity)) return;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackWiredReflexes = cyberwareUserData.getCybercraft(getCachedStack(META_WIRED_REFLEXES));
        int rank = itemStackWiredReflexes.getCount();
        if ( rank > 1
                && EnableDisableHelper.isEnabled(itemStackWiredReflexes)
                && setIsStrengthPowered.contains(entityLivingBase.getUUID()) )
        {
            PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
            if ( event.getSource() instanceof EntityDamageSource
                    && !(event.getSource() instanceof EntityDamageSourceIndirect) )
            {
                EntityDamageSource source = (EntityDamageSource) event.getSource();
                Entity attacker = source.getTrueSource();
                int lastAttacked = ReflectionHelper.getPrivateValue(CombatTracker.class, entityPlayer.getCombatTracker(), 2);

                if (entityPlayer.ticksExisted - lastAttacked > 120)
                {
                    int indexWeapon = -1;
                    ItemStack itemMainhand = entityPlayer.getHeldItemMainhand();
                    if (!itemMainhand.isEmpty())
                    {
                        if ( entityPlayer.getItemInUseCount() > 0
                                || itemMainhand.getItem() instanceof SwordItem
                                || itemMainhand.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, itemMainhand).containsKey(SharedMonsterAttributes.ATTACK_DAMAGE.getName()) )
                        {
                            indexWeapon = entityPlayer.inventory.currentItem;
                        }
                    }

                    if (indexWeapon == -1)
                    {
                        double mostDamage = 0F;

                        for (int indexHotbar = 0; indexHotbar < 10; indexHotbar++)
                        {
                            if (indexHotbar != entityPlayer.inventory.currentItem)
                            {
                                ItemStack potentialWeapon = entityPlayer.inventory.mainInventory.get(indexHotbar);
                                if (!potentialWeapon.isEmpty())
                                {
                                    Multimap<String, AttributeModifier> modifiers = potentialWeapon.getItem().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, potentialWeapon);
                                    if (modifiers.containsKey(SharedMonsterAttributes.ATTACK_DAMAGE.getName()))
                                    {
                                        double damage = modifiers.get(SharedMonsterAttributes.ATTACK_DAMAGE.getName()).iterator().next().getAmount();

                                        if (damage > mostDamage || indexWeapon == -1)
                                        {
                                            mostDamage = damage;
                                            indexWeapon = indexHotbar;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (indexWeapon != -1)
                    {
                        entityPlayer.inventory.currentItem = indexWeapon;

                        CyberwarePacketHandler.INSTANCE.sendTo(new SwitchHeldItemAndRotationPacket(indexWeapon, entityPlayer.getEntityId(),
                                        rank > 2 && attacker != null ? attacker.getEntityId() : -1 ),
                                (ServerPlayerEntity) entityPlayer);

                        ServerWorld worldServer = (ServerWorld) entityPlayer.level;

                        for (PlayerEntity trackingPlayer : worldServer.getEntityTracker().getTrackingPlayers(entityPlayer))
                        {
                            CyberwarePacketHandler.INSTANCE.sendTo(new SwitchHeldItemAndRotationPacket(indexWeapon, entityPlayer.getEntityId(),
                                            rank > 2 && attacker != null ? attacker.getEntityId() : -1 ),
                                    (ServerPlayerEntity) trackingPlayer);
                        }
                    }
                }
            }
        }
    }

    private Set<UUID> setIsSpeedPowered = new HashSet<>();
    private Set<UUID> setIsStrengthPowered = new HashSet<>();

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackMuscleReplacement = cyberwareUserData.getCybercraft(getCachedStack(META_MUSCLE_REPLACEMENTS));
        if (!itemStackMuscleReplacement.isEmpty())
        {
            boolean wasPowered = setIsStrengthPowered.contains(entityLivingBase.getUUID());
            boolean isPowered = entityLivingBase.ticksExisted % 20 == 0
                    ? cyberwareUserData.usePower(itemStackMuscleReplacement, getPowerConsumption(itemStackMuscleReplacement))
                    : wasPowered;
            if (isPowered)
            {
                if ( !entityLivingBase.isInWater()
                        && entityLivingBase.isOnGround()
                        && entityLivingBase.moveForward > 0 )
                {
                    entityLivingBase.moveRelative(0F, 0.0F,.5F, 0.075F);
                }

                if (!wasPowered)
                {
                    onAdded(entityLivingBase, itemStackMuscleReplacement);
                    setIsStrengthPowered.add(entityLivingBase.getUUID());
                }
            }
            else if (wasPowered)
            {
                onRemoved(entityLivingBase, itemStackMuscleReplacement);
                setIsStrengthPowered.remove(entityLivingBase.getUUID());
            }
        }
        else if (entityLivingBase.ticksExisted % 20 == 0)
        {
            onRemoved(entityLivingBase, itemStackMuscleReplacement);
            setIsStrengthPowered.remove(entityLivingBase.getUUID());
        }

        if (entityLivingBase.ticksExisted % 20 == 0)
        {
            ItemStack itemStackWiredReflexes = cyberwareUserData.getCybercraft(getCachedStack(META_WIRED_REFLEXES));
            if ( !itemStackWiredReflexes.isEmpty()
                    && EnableDisableHelper.isEnabled(itemStackWiredReflexes) )
            {
                boolean wasPowered = setIsSpeedPowered.contains(entityLivingBase.getUUID());
                boolean isPowered = cyberwareUserData.usePower(itemStackWiredReflexes, getPowerConsumption(itemStackWiredReflexes));
                if ( !wasPowered
                        && isPowered )
                {
                    onAdded(entityLivingBase, itemStackWiredReflexes);
                    setIsSpeedPowered.add(entityLivingBase.getUUID());
                }
                else if ( wasPowered
                        && !isPowered )
                {
                    onRemoved(entityLivingBase, itemStackWiredReflexes);
                    setIsSpeedPowered.remove(entityLivingBase.getUUID());
                }
            }
            else
            {
                onRemoved(entityLivingBase, itemStackWiredReflexes);
                setIsSpeedPowered.remove(entityLivingBase.getUUID());
            }
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getDamageValue() == META_WIRED_REFLEXES ? LibConstants.REFLEXES_CONSUMPTION : LibConstants.REPLACEMENTS_CONSUMPTION;
    }

    @Override
    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        if (stack.getDamageValue() == META_WIRED_REFLEXES)
        {
            switch (stack.getCount())
            {
                case 1:
                    return 9;
                case 2:
                    return 10;
                case 3:
                    return 11;
            }
        }
        return super.getUnmodifiedEssenceCost(stack);
    }

    @Override
    public boolean hasMenu(ItemStack stack)
    {
        return stack.getDamageValue() == META_WIRED_REFLEXES;
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

    @Override
    public boolean isEssential(ItemStack stack)
    {
        return stack.getDamageValue() == META_MUSCLE_REPLACEMENTS;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other)
    {
        return stack.getDamageValue() == META_MUSCLE_REPLACEMENTS
                && CybercraftAPI.getCybercraft(other).isEssential(other);
    }
}
