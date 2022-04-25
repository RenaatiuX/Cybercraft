package com.rena.cybercraft.common.item;

import com.google.common.collect.HashMultimap;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.util.NNLUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HandUpgradeItem extends CybercraftItem implements IMenuItem {

    public static final int META_CRAFT_HANDS = 0;
    public static final int META_CLAWS = 1;
    public static final int META_MINING = 2;

    private final Item tool_level;

    private static final UUID isClawsStrengthAttribute = UUID.fromString("63c32801-94fb-40d4-8bd2-89135c1e44b1");
    private static final HashMultimap<String, AttributeModifier> multimapClawsStrengthAttribute;

    static {
        multimapClawsStrengthAttribute = HashMultimap.create();
        multimapClawsStrengthAttribute.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(isClawsStrengthAttribute, "Claws damage upgrade", 5.5F, 0));
    }

    public HandUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        this.tool_level = CybercraftConfig.C_OTHER.fistMiningLevel.get() == 3
                ? Items.DIAMOND_PICKAXE
                : CybercraftConfig.C_OTHER.fistMiningLevel.get() == 2
                ? Items.IRON_PICKAXE
                : Items.STONE_PICKAXE;
    }

    @Override
    public NonNullList<NonNullList<ItemStack>> required(ItemStack stack) {
        return NNLUtil.fromArray(new ItemStack[][] {
                new ItemStack[] { CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_LEFT_CYBER_ARM),
                        CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_RIGHT_CYBER_ARM) }});
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        return other.getItem() == this;
    }

    private Map<UUID, Boolean> lastClaws = new HashMap<>();
    public static float clawsTime;

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackClaws = cyberwareUserData.getCybercraft(getCachedStack(META_CLAWS));
        if (!itemStackClaws.isEmpty())
        {
            boolean wasEquipped = getLastClaws(entityLivingBase);
            boolean isEquipped = entityLivingBase.getHeldItemMainhand().isEmpty()
                    && ( entityLivingBase.getPrimaryHand() == EnumHandSide.RIGHT
                    ? (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_RIGHT_CYBER_ARM)))
                    : (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_LEFT_CYBER_ARM))) );
            if ( isEquipped
                    && EnableDisableHelper.isEnabled(itemStackClaws) )
            {
                addUnarmedDamage(entityLivingBase, itemStackClaws);
                lastClaws.put(entityLivingBase.getUUID(), true);

                if (!wasEquipped)
                {
                    if (FMLCommonHandler.instance().getSide() == MixinEnvironment.Side.CLIENT)
                    {
                        updateHand(entityLivingBase, true);
                    }
                }
            }
            else if (wasEquipped)
            {
                removeUnarmedDamage(entityLivingBase, itemStackClaws);
                lastClaws.put(entityLivingBase.getUUID(), false);
            }
        }
        else if (entityLivingBase.ticksExisted % 20 == 0)
        {
            removeUnarmedDamage(entityLivingBase, itemStackClaws);
            lastClaws.put(entityLivingBase.getUUID(), false);
        }
    }

    private void updateHand(LivingEntity entityLivingBase, boolean delay)
    {
        if ( Minecraft.getInstance() != null
                && Minecraft.getInstance().player != null )
        {
            if (entityLivingBase == Minecraft.getInstance().player)
            {
                clawsTime = Minecraft.getInstance().getRenderPartialTicks() + entityLivingBase.ticksExisted + (delay ? 5 : 0);
            }
        }
    }

    private boolean getLastClaws(LivingEntity entityLivingBase)
    {
        if (!lastClaws.containsKey(entityLivingBase.getUUID()))
        {
            lastClaws.put(entityLivingBase.getUUID(), Boolean.FALSE);
        }
        return lastClaws.get(entityLivingBase.getUUID());
    }

    public void addUnarmedDamage(LivingEntity entityLivingBase, ItemStack stack)
    {
        if (stack.getDamageValue() == META_CLAWS)
        {
            entityLivingBase.getAttributeMap().applyAttributeModifiers(multimapClawsStrengthAttribute);
        }
    }

    public void removeUnarmedDamage(LivingEntity entityLivingBase, ItemStack stack)
    {
        if (stack.getDamageValue() == META_CLAWS)
        {
            entityLivingBase.getAttributeMap().removeAttributeModifiers(multimapClawsStrengthAttribute);
        }
    }

    @Override
    public void onRemoved(LivingEntity livingEntity, ItemStack stack) {
        if (stack.getDamageValue() == META_CLAWS)
        {
            removeUnarmedDamage(livingEntity, stack);
        }
    }

    @SubscribeEvent
    public void handleMining(PlayerEvent.HarvestCheck event)
    {
        PlayerEntity entityPlayer = event.getPlayer();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData == null) return;

        ItemStack itemStackMining = cyberwareUserData.getCybercraft(getCachedStack(META_MINING));
        boolean rightArm = ( entityPlayer.getPrimaryHand() == EnumHandSide.RIGHT
                ? (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_RIGHT_CYBER_ARM)))
                : (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_LEFT_CYBER_ARM))) );
        if ( rightArm
                && !itemStackMining.isEmpty()
                && entityPlayer.getHeldItemMainhand().isEmpty() )
        {
            ItemStack pick = new ItemStack(tool_level);
            if (pick.canHarvestBlock(event.getTargetBlock()))
            {
                event.setCanHarvest(true);
            }
        }
    }

    @SubscribeEvent
    public void handleMineSpeed(PlayerEvent.BreakSpeed event)
    {
        PlayerEntity entityPlayer = event.getPlayer();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData == null) return;

        ItemStack itemStackMining = cyberwareUserData.getCybercraft(getCachedStack(META_MINING));
        boolean rightArm = ( entityPlayer.getPrimaryHand() == EnumHandSide.RIGHT
                ? (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_RIGHT_CYBER_ARM)))
                : (cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(CyberLimbItem.META_LEFT_CYBER_ARM))) );
        if ( rightArm
                && !itemStackMining.isEmpty()
                && entityPlayer.getHeldItemMainhand().isEmpty() )
        {
            ItemStack pick = new ItemStack(tool_level);
            event.setNewSpeed(event.getNewSpeed() * pick.getDestroySpeed(entityPlayer.level.getBlockState(event.getPos())));
        }
    }

    @Override
    public boolean hasMenu(ItemStack stack) {
        return stack.getDamageValue() == META_CLAWS;
    }

    @Override
    public void use(Entity entity, ItemStack stack) {
        EnableDisableHelper.toggle(stack);
        if (entity instanceof LivingEntity && FMLCommonHandler.instance().getSide() == MixinEnvironment.Side.CLIENT)
        {
            updateHand((LivingEntity) entity, false);
        }
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
