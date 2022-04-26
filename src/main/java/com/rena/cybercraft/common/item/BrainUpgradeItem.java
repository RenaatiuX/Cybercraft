package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.ArmorClass;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.core.network.DodgePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class BrainUpgradeItem extends CybercraftItem implements IMenuItem {

    public static final int META_CORTICAL_STACK = 0;
    public static final int META_ENDER_JAMMER = 1;
    public static final int META_CONSCIOUSNESS_TRANSMITTER = 2;
    public static final int META_NEURAL_CONTEXTUALIZER = 3;
    public static final int META_THREAT_MATRIX = 4;
    public static final int META_RADIO= 5;

    public BrainUpgradeItem(Properties properties, EnumSlot slots, String... subnames) {
        super(properties, slots, subnames);
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        return other.getItem() == this
                && stack.getDamageValue() == META_CORTICAL_STACK
                && other.getDamageValue() == META_CONSCIOUSNESS_TRANSMITTER;
    }

    @SubscribeEvent
    public void handleTeleJam(EnderTeleportEvent event)
    {
        LivingEntity livingEntity = event.getEntityLiving();
        if (!isTeleportationAllowed(livingEntity))
        {
            event.setCanceled(true);
        }
    }

    public static boolean isTeleportationAllowed(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null) return true;

        ItemStack itemStackJammer = ItemInit.BRAIN_UPGRADES.get().getCachedStack(BrainUpgradeItem.META_ENDER_JAMMER);

        ICybercraftUserData cyberwareUserDataSelf = CybercraftAPI.getCapabilityOrNull(livingEntity);
        if (cyberwareUserDataSelf != null) {
            ItemStack itemStackJammerSelf = cyberwareUserDataSelf.getCybercraft(itemStackJammer);
            if ( !itemStackJammerSelf.isEmpty()
                    && EnableDisableHelper.isEnabled(itemStackJammerSelf) )
            {
                return false;
            }
        }

        float range = 25F;
        List<LivingEntity> entitiesInRange = livingEntity.level.getEntitiesOfClass(
                LivingEntity.class,
                new AxisAlignedBB(livingEntity.posX - range, livingEntity.posY - range, livingEntity.posZ - range,
                        livingEntity.posX + livingEntity.width + range, livingEntity.posY + livingEntity.height + range, livingEntity.posZ + livingEntity.width + range));
        for (LivingEntity entityInRange : entitiesInRange)
        {
            if (livingEntity.distanceToSqr(entityInRange) <= range * range)
            {
                ICybercraftUserData cyberwareUserDataInRange = CybercraftAPI.getCapabilityOrNull(entityInRange);
                if (cyberwareUserDataInRange != null)
                {
                    ItemStack itemStackJammerInRange = cyberwareUserDataInRange.getCybercraft(itemStackJammer);
                    if ( !itemStackJammerInRange.isEmpty()
                            && EnableDisableHelper.isEnabled(itemStackJammerInRange) )
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @SubscribeEvent
    public void handleClone(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            PlayerEntity entityPlayerOriginal = event.getOriginal();

            if (entityPlayerOriginal.level.getGameRules().getBoolean("keepInventory")) {
                return;
            }

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayerOriginal);
            if (cyberwareUserData == null) return;

            if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_CORTICAL_STACK)))
            {
                if (!entityPlayerOriginal.level.isClientSide)
                {
                    ItemStack stack = new ItemStack(ItemInit.EXP_CAPSULE.get());
                    CompoundNBT tagCompound = new CompoundNBT();
                    tagCompound.putInt("xp", entityPlayerOriginal.totalExperience);
                    stack.setTag(tagCompound);
                    ItemEntity item = new ItemEntity(entityPlayerOriginal.level, entityPlayerOriginal.pos, entityPlayerOriginal.posY, entityPlayerOriginal.posZ, stack);
                    entityPlayerOriginal.level.addFreshEntity(item);
                }
            }
            else if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_CONSCIOUSNESS_TRANSMITTER)))
            {
                event.getOriginal().giveExperienceLevels((int) (Math.min(100, entityPlayerOriginal.experienceLevel * 7) * .9F));
            }
        }
    }

    @SubscribeEvent
    public void handleMining(PlayerEvent.BreakSpeed event)
    {
        PlayerEntity entityPlayer = event.getPlayer();

        ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cybercraftUserData == null) return;

        ItemStack itemStackNeuralContextualizer = cybercraftUserData.getCybercraft(getCachedStack(META_NEURAL_CONTEXTUALIZER));
        if ( !itemStackNeuralContextualizer.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackNeuralContextualizer)
                && isContextWorking(entityPlayer)
                && !entityPlayer.isShiftKeyDown() )
        {
            IBlockState state = event.getState();
            ItemStack tool = entityPlayer.getItemInHand(Hand.MAIN_HAND);

            if ( !tool.isEmpty()
                    && ( tool.getItem() instanceof SwordItem
                    || tool.getItem().getDescriptionId().contains("sword") ) )
            {
                return;
            }

            if (isToolEffective(tool, state)) return;

            for (int indexSlot = 0; indexSlot < 10; indexSlot++)
            {
                if (indexSlot != entityPlayer.inventory.selected)
                {
                    ItemStack potentialTool = entityPlayer.inventory.items.get(indexSlot);
                    if (isToolEffective(potentialTool, state))
                    {
                        entityPlayer.inventory.selected = indexSlot;
                        return;
                    }
                }
            }
        }
    }

    private static Map<UUID, Boolean> isContextWorking = new HashMap<>();
    private static Map<UUID, Boolean> isMatrixWorking = new HashMap<>();
    private static Map<UUID, Boolean> isRadioWorking = new HashMap<>();

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;

        ICybercraftUserData cybercraftUserData = event.getCybercrafteUserData();

        ItemStack itemStackNeuralContextualizer = cybercraftUserData.getCybercraft(getCachedStack(META_NEURAL_CONTEXTUALIZER));
        if ( !itemStackNeuralContextualizer.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackNeuralContextualizer) )
        {
            isContextWorking.put(entityLivingBase.getUUID(), cybercraftUserData.usePower(itemStackNeuralContextualizer, getPowerConsumption(itemStackNeuralContextualizer)));
        }
        else
        {
            isContextWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        ItemStack itemStackThreatMatrix = cybercraftUserData.getCybercraft(getCachedStack(META_THREAT_MATRIX));
        if (!itemStackThreatMatrix.isEmpty())
        {
            isMatrixWorking.put(entityLivingBase.getUUID(), cybercraftUserData.usePower(itemStackThreatMatrix, getPowerConsumption(itemStackThreatMatrix)));
        }
        else
        {
            isMatrixWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        ItemStack itemStackRadio = cybercraftUserData.getCybercraft(getCachedStack(META_RADIO));
        if ( !itemStackRadio.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackRadio) )
        {
            isRadioWorking.put(entityLivingBase.getUUID(), cybercraftUserData.usePower(itemStackRadio, getPowerConsumption(itemStackRadio)));
        }
        else
        {
            isRadioWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }
    }

    private boolean isContextWorking(LivingEntity entityLivingBase)
    {
        if (!isContextWorking.containsKey(entityLivingBase.getUUID()))
        {
            isContextWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        return isContextWorking.get(entityLivingBase.getUUID());
    }

    private boolean isMatrixWorking(LivingEntity entityLivingBase)
    {
        if (!isMatrixWorking.containsKey(entityLivingBase.getUUID()))
        {
            isMatrixWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        return isMatrixWorking.get(entityLivingBase.getUUID());
    }

    public boolean isToolEffective(ItemStack tool, IBlockState state)
    {
        if (!tool.isEmpty())
        {
            for (String toolType : tool.getItem().getToolClasses(tool))
            {
                if (state.getBlock().isToolEffective(toolType, state))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void handleXPDrop(LivingExperienceDropEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        if ( cyberwareUserData.isCybercraftInstalled(getCachedStack(META_CORTICAL_STACK))
                || cyberwareUserData.isCybercraftInstalled(getCachedStack(META_CONSCIOUSNESS_TRANSMITTER)) )
        {
            event.setCanceled(true);
        }
    }

    private static ArrayList<String> lastHits = new ArrayList<>();

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void handleHurt(LivingAttackEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!isMatrixWorking(entityLivingBase)) return;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        if (cyberwareUserData.isCybercraftInstalled(getCachedStack(META_THREAT_MATRIX)))
        {
            if ( !entityLivingBase.level.isClientSide
                    && event.getSource() instanceof EntityDamageSource)
            {
                Entity attacker = event.getSource().getTrueSource();
                if (entityLivingBase instanceof PlayerEntity)
                {
                    String str = entityLivingBase.getEntityId() + " " + entityLivingBase.tickCount + " " + (attacker == null ? -1 : attacker.getEntityId());
                    if (lastHits.contains(str))
                    {
                        return;
                    }
                    else
                    {
                        lastHits.add(str);
                    }
                }

                ArmorClass armorClass = ArmorClass.get(entityLivingBase);
                if (armorClass == ArmorClass.HEAVY) return;

                if (!((float) entityLivingBase.invulnerableTime > (float) entityLivingBase.invulnerableDuration / 2.0F))
                {
                    Random random = entityLivingBase.getRandom();
                    if (random.nextFloat() < (armorClass == ArmorClass.LIGHT ? LibConstants.DODGE_ARMOR : LibConstants.DODGE_NO_ARMOR))
                    {
                        event.setCanceled(true);
                        entityLivingBase.invulnerableTime = entityLivingBase.invulnerableDuration;
                        entityLivingBase.hurtTime = entityLivingBase.hurtDuration = 10;
                        ReflectionHelper.setPrivateValue(LivingEntity.class, entityLivingBase, 9999F, "lastDamage", "field_110153_bc");
                        CyberwarePacketHandler.INSTANCE.sendToAllAround(new DodgePacket(entityLivingBase.getEntityId()),
                                new PacketDistributor.TargetPoint(entityLivingBase.level.provider.getDimension(), entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, 50));
                    }
                }
            }
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getDamageValue() == META_NEURAL_CONTEXTUALIZER ? LibConstants.CONTEXTUALIZER_CONSUMPTION
                : stack.getDamageValue() == META_THREAT_MATRIX ? LibConstants.MATRIX_CONSUMPTION
                : stack.getDamageValue() == META_RADIO ? LibConstants.RADIO_CONSUMPTION
                : 0;
    }

    @Override
    public boolean hasMenu(ItemStack stack) {
        return stack.getDamageValue() == META_ENDER_JAMMER
                || stack.getDamageValue() == META_NEURAL_CONTEXTUALIZER
                || stack.getDamageValue() == META_RADIO;
    }

    @Override
    public void use(Entity entity, ItemStack stack) {
        EnableDisableHelper.toggle(stack);
    }

    @Override
    public String getUnlocalizedLabel(ItemStack stack) {
        return EnableDisableHelper.getUnlocalizedLabel(stack);
    }

    private static final float[] f = new float[] { 1.0F, 0.0F, 0.0F };

    @Override
    public float[] getColor(ItemStack stack) {
        return EnableDisableHelper.isEnabled(stack) ? f : null;
    }
}
