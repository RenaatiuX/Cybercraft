package com.rena.cybercraft.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.SwitchHeldItemAndRotationPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MuscleUpgradeItem extends CybercraftItem implements IMenuItem {

    private static final UUID idMuscleSpeedAttribute = UUID.fromString("f0ab4766-4be1-11e6-beb8-9e71128cae77");
    private static final UUID idMuscleDamageAttribute = UUID.fromString("f63d6916-4be1-11e6-beb8-9e71128cae77");
    private static final HashMultimap<Attribute, AttributeModifier> multimapMuscleSpeedAttribute;
    private static final HashMultimap<Attribute, AttributeModifier> multimapMuscleDamageAttribute;

    static {
        multimapMuscleSpeedAttribute = HashMultimap.create();
        multimapMuscleSpeedAttribute.put(Attributes.ATTACK_SPEED, new AttributeModifier(idMuscleSpeedAttribute, "Muscle speed upgrade", 1.5F, AttributeModifier.Operation.ADDITION));
        multimapMuscleDamageAttribute = HashMultimap.create();
        multimapMuscleDamageAttribute.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(idMuscleDamageAttribute, "Muscle damage upgrade", 3F, AttributeModifier.Operation.ADDITION));
    }

    public MuscleUpgradeItem(Properties properties, EnumSlot slots, Quality q) {
        super(properties, slots, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onAdded(LivingEntity entityLivingBase, ItemStack stack)
    {
        if (stack.getItem() == ItemInit.MUSCLE_REFLEXES.get())
        {
            entityLivingBase.getAttributes().addTransientAttributeModifiers(multimapMuscleSpeedAttribute);
        }
        else if (stack.getItem() == ItemInit.MUSCLE_REPLACEMENTS.get())
        {
            entityLivingBase.getAttributes().addTransientAttributeModifiers(multimapMuscleDamageAttribute);
        }
    }

    @Override
    public void onRemoved(LivingEntity entityLivingBase, ItemStack stack)
    {
        if (stack.getItem() == ItemInit.MUSCLE_REFLEXES.get())
        {
            entityLivingBase.getAttributes().removeAttributeModifiers(multimapMuscleSpeedAttribute);
        }
        else if (stack.getItem() == ItemInit.MUSCLE_REPLACEMENTS.get())
        {
            entityLivingBase.getAttributes().removeAttributeModifiers(multimapMuscleDamageAttribute);
        }
    }

    @Override
    public int maxInstalledStackSize(ItemStack stack)
    {
        return stack.getItem() == ItemInit.MUSCLE_REFLEXES.get() ? 3 : 1;
    }

    /*@SubscribeEvent
    public void handleHurt(LivingHurtEvent event)
    {
        if (event.isCanceled()) return;
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!(entityLivingBase instanceof PlayerEntity)) return;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackWiredReflexes = cyberwareUserData.getCybercraft(ItemInit.MUSCLE_REFLEXES.get());
        int rank = itemStackWiredReflexes.getCount();
        if ( rank > 1
                && EnableDisableHelper.isEnabled(itemStackWiredReflexes)
                && setIsStrengthPowered.contains(entityLivingBase.getUUID()) )
        {
            PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
            if ( event.getSource() instanceof EntityDamageSource
                    && !(event.getSource() instanceof IndirectEntityDamageSource) )
            {
                EntityDamageSource source = (EntityDamageSource) event.getSource();
                Entity attacker = source.getEntity();
                //i don't know if this is ok
                //ReflectionHelper.getPrivateValue(CombatTracker.class, entityPlayer.getCombatTracker(), 2); this is the method of 1.12.2
                int lastAttacked = entityPlayer.getCombatTracker().lastDamageTime;

                if (entityPlayer.tickCount - lastAttacked > 120)
                {
                    int indexWeapon = -1;
                    ItemStack itemMainhand = entityPlayer.getMainHandItem();
                    if (!itemMainhand.isEmpty())
                    {
                        if ( entityPlayer.getUseItemRemainingTicks() > 0
                                || itemMainhand.getItem() instanceof SwordItem
                                || itemMainhand.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, itemMainhand).containsKey(Attributes.ATTACK_DAMAGE) )
                        {
                            indexWeapon = entityPlayer.inventory.selected;
                        }
                    }

                    if (indexWeapon == -1)
                    {
                        double mostDamage = 0F;

                        for (int indexHotbar = 0; indexHotbar < 10; indexHotbar++)
                        {
                            if (indexHotbar != entityPlayer.inventory.selected)
                            {
                                ItemStack potentialWeapon = entityPlayer.inventory.items.get(indexHotbar);
                                if (!potentialWeapon.isEmpty())
                                {
                                    Multimap<Attribute, AttributeModifier> modifiers = potentialWeapon.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, potentialWeapon);
                                    if (modifiers.containsKey(Attributes.ATTACK_DAMAGE))
                                    {
                                        double damage = modifiers.get(Attributes.ATTACK_DAMAGE).iterator().next().getAmount();

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
                        entityPlayer.inventory.selected = indexWeapon;

                        CCNetwork.sendTo(new SwitchHeldItemAndRotationPacket(indexWeapon, entityPlayer.getId(),
                                        rank > 2 && attacker != null ? attacker.getId() : -1 ),
                                (ServerPlayerEntity) entityPlayer);

                        ServerWorld worldServer = (ServerWorld) entityPlayer.level;

                        for (PlayerEntity trackingPlayer : worldServer.getEntityTracker().getTrackingPlayers(entityPlayer))
                        {
                            CCNetwork.sendTo(new SwitchHeldItemAndRotationPacket(indexWeapon, entityPlayer.getId(),
                                            rank > 2 && attacker != null ? attacker.getId() : -1 ),
                                    (ServerPlayerEntity) trackingPlayer);
                        }
                    }
                }
            }
        }
    }*/

    private Set<UUID> setIsSpeedPowered = new HashSet<>();
    private Set<UUID> setIsStrengthPowered = new HashSet<>();

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackMuscleReplacement = cyberwareUserData.getCybercraft(ItemInit.MUSCLE_REPLACEMENTS.get());
        if (!itemStackMuscleReplacement.isEmpty())
        {
            boolean wasPowered = setIsStrengthPowered.contains(entityLivingBase.getUUID());
            boolean isPowered = entityLivingBase.tickCount % 20 == 0
                    ? cyberwareUserData.usePower(itemStackMuscleReplacement, getPowerConsumption(itemStackMuscleReplacement))
                    : wasPowered;
            if (isPowered)
            {
                if ( !entityLivingBase.isInWater()
                        && entityLivingBase.isOnGround()
                        && entityLivingBase.zza > 0 )
                {
                    entityLivingBase.moveRelative(0F, new Vector3d(0.0F,.5F, 0.075F));
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
        else if (entityLivingBase.tickCount % 20 == 0)
        {
            onRemoved(entityLivingBase, itemStackMuscleReplacement);
            setIsStrengthPowered.remove(entityLivingBase.getUUID());
        }

        if (entityLivingBase.tickCount % 20 == 0)
        {
            ItemStack itemStackWiredReflexes = cyberwareUserData.getCybercraft(ItemInit.MUSCLE_REFLEXES.get());
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
        return stack.getItem() == ItemInit.MUSCLE_REFLEXES.get() ? LibConstants.REFLEXES_CONSUMPTION : LibConstants.REPLACEMENTS_CONSUMPTION;
    }

    @Override
    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        if (stack.getItem() == ItemInit.MUSCLE_REFLEXES.get())
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
        return stack.getItem() == ItemInit.MUSCLE_REFLEXES.get();
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
        return stack.getItem() == ItemInit.MUSCLE_REPLACEMENTS.get();
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other)
    {
        return stack.getItem() == ItemInit.MUSCLE_REPLACEMENTS.get()
                && CybercraftAPI.getCybercraft(other).isEssential(other);
    }
}
