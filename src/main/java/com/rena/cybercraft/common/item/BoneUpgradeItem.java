package com.rena.cybercraft.common.item;

import com.google.common.collect.HashMultimap;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.UUID;

public class BoneUpgradeItem extends CybercraftItem {

    public static final int META_LACING = 0;
    public static final int META_FLEX = 1;
    public static final int META_BATTERY= 2;

    public static final int MAX_STACK_SIZE_LACING = 5;

    private static final UUID idBoneHealthAttribute = UUID.fromString("8bce997a-4c3a-11e6-beb8-9e71128cae77");
    private static final HashMap<Integer, HashMultimap<Attribute, AttributeModifier>> multimapBoneHealthAttributes = new HashMap<>(MAX_STACK_SIZE_LACING + 1);

    public BoneUpgradeItem(Properties properties, EnumSlot slots, String... subnames) {
        super(properties, slots, subnames);
    }

    private static HashMultimap<Attribute, AttributeModifier> getBoneHealthAttribute(int stackSize)
    {
        HashMultimap<Attribute, AttributeModifier> multimapBoneHealthAttribute = multimapBoneHealthAttributes.get(stackSize);
        if (multimapBoneHealthAttribute == null)
        {
            multimapBoneHealthAttribute = HashMultimap.create();
            multimapBoneHealthAttribute.put(Attributes.MAX_HEALTH, new AttributeModifier(idBoneHealthAttribute, "Bone hp upgrade", 4F * stackSize, AttributeModifier.Operation.ADDITION));
            multimapBoneHealthAttributes.put(stackSize, multimapBoneHealthAttribute);
        }
        return multimapBoneHealthAttribute;
    }

    @Override
    public void onAdded(LivingEntity livingEntity, ItemStack stack) {

        if (stack.getDamageValue() == META_LACING)
        {
            livingEntity.getAttributes().addTransientAttributeModifiers(getBoneHealthAttribute(stack.getCount()));
        }
    }

    @Override
    public void onRemoved(LivingEntity livingEntity, ItemStack stack) {
        if (stack.getDamageValue() == META_LACING)
        {
            livingEntity.getAttributes().removeAttributeModifiers(getBoneHealthAttribute(stack.getCount()));
        }
    }

    @SubscribeEvent
    public void handleJoinWorld(EntityJoinWorldEvent event)
    {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        if (livingEntity.tickCount % 20 != 0) return;

        ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(livingEntity);
        if (cybercraftUserData != null)
        {
            ItemStack itemStackMetalLacing = cybercraftUserData.getCybercraft(getCachedStack(META_LACING));
            if (!itemStackMetalLacing.isEmpty())
            {
                onAdded(livingEntity, cybercraftUserData.getCybercraft(itemStackMetalLacing));
            }
            else
            {
                onRemoved(livingEntity, itemStackMetalLacing);
            }
        }
    }

    @SubscribeEvent
    public void handleFallDamage(LivingHurtEvent event)
    {
        if (event.getSource() != DamageSource.FALL) return;

        LivingEntity livingEntity = event.getEntityLiving();
        ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(livingEntity);
        if (cybercraftUserData == null) return;

        if (cybercraftUserData.isCybercraftInstalled(getCachedStack(META_FLEX)))
        {
            event.setAmount(event.getAmount() * .3333F);
        }
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        return other.getItem() == this;
    }

    @Override
    public int getCapacity(ItemStack wareStack) {
        return wareStack.getDamageValue() == META_BATTERY ? LibConstants.BONE_BATTERY_CAPACITY * wareStack.getCount() : 0;
    }

    @Override
    public int installedStackSize(ItemStack stack) {
        return stack.getDamageValue() == META_LACING ? MAX_STACK_SIZE_LACING
                : stack.getDamageValue() == META_BATTERY ? 4 : 1;
    }

    @Override
    protected int getUnmodifiedEssenceCost(ItemStack stack) {
        if (stack.getDamageValue() == META_LACING)
        {
            switch (stack.getCount())
            {
                case 1:
                    return 3;
                case 2:
                    return 6;
                case 3:
                    return 9;
                case 4:
                    return 12;
                case 5:
                    return 15;
            }
        }
        if (stack.getDamageValue() == META_BATTERY)
        {
            switch (stack.getCount())
            {
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                case 4:
                    return 5;
            }
        }
        return super.getUnmodifiedEssenceCost(stack);
    }
}
