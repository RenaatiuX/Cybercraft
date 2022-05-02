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
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.DodgePacket;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class BrainUpgradeItem extends CybercraftItem implements IMenuItem {

    public BrainUpgradeItem(Properties properties, EnumSlot slots, Quality q) {
        super(properties, slots, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        return other.getItem() == this
                && stack.getItem() == ItemInit.BRAIN_UPGRADES_CORTICAL_STACK.get()
                && other.getItem() == ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER.get();
    }

    @SubscribeEvent
    public void handleTeleJam(EntityTeleportEvent.EnderEntity event)
    {
        LivingEntity livingEntity = event.getEntityLiving();
        if (!isTeleportationAllowed(livingEntity))
        {
            event.setCanceled(true);
        }
    }

    public static boolean isTeleportationAllowed(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null) return true;
        ICybercraftUserData cyberwareUserDataSelf = CybercraftAPI.getCapabilityOrNull(livingEntity);
        if (cyberwareUserDataSelf != null) {
            ItemStack itemStackJammerSelf = cyberwareUserDataSelf.getCybercraft(ItemInit.BRAIN_UPGRADES_ENDER_HAMMER.get());
            if ( !itemStackJammerSelf.isEmpty()
                    && EnableDisableHelper.isEnabled(itemStackJammerSelf) )
            {
                return false;
            }
        }

        float range = 25F;
        List<LivingEntity> entitiesInRange = livingEntity.level.getEntitiesOfClass(
                LivingEntity.class,
                new AxisAlignedBB(livingEntity.getX() - range, livingEntity.getY() - range, livingEntity.getZ() - range,
                        livingEntity.getX() + livingEntity.getBbWidth() + range, livingEntity.getY() + livingEntity.getEyeHeight() + range, livingEntity.getZ() + livingEntity.getBbWidth() + range));
        for (LivingEntity entityInRange : entitiesInRange)
        {
            if (livingEntity.distanceToSqr(entityInRange) <= range * range)
            {
                ICybercraftUserData cyberwareUserDataInRange = CybercraftAPI.getCapabilityOrNull(entityInRange);
                if (cyberwareUserDataInRange != null)
                {
                    ItemStack itemStackJammerInRange = cyberwareUserDataInRange.getCybercraft(ItemInit.BRAIN_UPGRADES_ENDER_HAMMER.get());
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

            if (entityPlayerOriginal.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                return;
            }

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayerOriginal);
            if (cyberwareUserData == null) return;

            if (cyberwareUserData.isCybercraftInstalled(ItemInit.BRAIN_UPGRADES_CORTICAL_STACK.get()))
            {
                if (!entityPlayerOriginal.level.isClientSide)
                {
                    ItemStack stack = new ItemStack(ItemInit.EXP_CAPSULE.get());
                    CompoundNBT tagCompound = new CompoundNBT();
                    tagCompound.putInt("xp", entityPlayerOriginal.totalExperience);
                    stack.setTag(tagCompound);
                    ItemEntity item = new ItemEntity(entityPlayerOriginal.level, entityPlayerOriginal.getX(), entityPlayerOriginal.getY(), entityPlayerOriginal.getZ(), stack);
                    entityPlayerOriginal.level.addFreshEntity(item);
                }
            }
            else if (cyberwareUserData.isCybercraftInstalled(ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER.get()))
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

        ItemStack itemStackNeuralContextualizer = cybercraftUserData.getCybercraft(ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get());
        if ( !itemStackNeuralContextualizer.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackNeuralContextualizer)
                && isContextWorking(entityPlayer)
                && !entityPlayer.isShiftKeyDown() )
        {
            BlockState state = event.getState();
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

        ItemStack itemStackNeuralContextualizer = cybercraftUserData.getCybercraft(ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get());
        if ( !itemStackNeuralContextualizer.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackNeuralContextualizer) )
        {
            isContextWorking.put(entityLivingBase.getUUID(), cybercraftUserData.usePower(itemStackNeuralContextualizer, getPowerConsumption(itemStackNeuralContextualizer)));
        }
        else
        {
            isContextWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        ItemStack itemStackThreatMatrix = cybercraftUserData.getCybercraft(ItemInit.BRAIN_UPGRADES_MATRIX.get());
        if (!itemStackThreatMatrix.isEmpty())
        {
            isMatrixWorking.put(entityLivingBase.getUUID(), cybercraftUserData.usePower(itemStackThreatMatrix, getPowerConsumption(itemStackThreatMatrix)));
        }
        else
        {
            isMatrixWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        ItemStack itemStackRadio = cybercraftUserData.getCybercraft(ItemInit.BRAIN_UPGRADES_RADIO.get());
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

    public static boolean isRadioWorking(LivingEntity entityLivingBase)
    {
        if (!isRadioWorking.containsKey(entityLivingBase.getUUID()))
        {
            isRadioWorking.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }

        return isRadioWorking.get(entityLivingBase.getUUID());
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

    public boolean isToolEffective(ItemStack tool, BlockState state)
    {
        if (!tool.isEmpty())
        {
            for (ToolType toolType : tool.getItem().getToolTypes(tool))
            {
                if (state.getBlock().isToolEffective(state, toolType))
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

        if ( cyberwareUserData.isCybercraftInstalled(ItemInit.BRAIN_UPGRADES_CORTICAL_STACK.get())
                || cyberwareUserData.isCybercraftInstalled(ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER.get()) )
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

        if (cyberwareUserData.isCybercraftInstalled(ItemInit.BRAIN_UPGRADES_MATRIX.get()))
        {
            if ( !entityLivingBase.level.isClientSide
                    && event.getSource() instanceof EntityDamageSource)
            {
                Entity attacker = event.getSource().getDirectEntity();
                if (entityLivingBase instanceof PlayerEntity)
                {
                    String str = entityLivingBase.getId() + " " + entityLivingBase.tickCount + " " + (attacker == null ? -1 : attacker.getId());
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
                        ObfuscationReflectionHelper.setPrivateValue(LivingEntity.class, entityLivingBase, 9999F, "field_110153_bc ");
                        CCNetwork.sendToPlayerInTRange(new PacketDistributor.TargetPoint(entityLivingBase.getX(), entityLivingBase.getY(), entityLivingBase.getZ(), 50, entityLivingBase.level.dimension()), new DodgePacket(entityLivingBase.getId()));
                    }
                }
            }
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getItem() == ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get() ? LibConstants.CONTEXTUALIZER_CONSUMPTION
                : stack.getItem() == ItemInit.BRAIN_UPGRADES_MATRIX.get() ? LibConstants.MATRIX_CONSUMPTION
                : stack.getItem() == ItemInit.BRAIN_UPGRADES_RADIO.get() ? LibConstants.RADIO_CONSUMPTION
                : 0;
    }

    @Override
    public boolean hasMenu(ItemStack stack) {
        return stack.getItem() == ItemInit.BRAIN_UPGRADES_ENDER_HAMMER.get()
                || stack.getItem() == ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get()
                || stack.getItem() == ItemInit.BRAIN_UPGRADES_RADIO.get();
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
