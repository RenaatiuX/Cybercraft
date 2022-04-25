package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.common.util.LibConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CybereyesItem extends CybercraftItem{

    private static boolean isBlind;

    public CybereyesItem(Properties properties, EnumSlot[] slots) {
        super(properties, slots);
    }

    @Override
    public boolean isEssential(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other)
    {
        return CybercraftAPI.getCybercraft(other).isEssential(other);
    }

    @SubscribeEvent
    public void handleBlindnessImmunity(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!entityLivingBase.addEffect(new EffectInstance(Effects.BLINDNESS))) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        if (cyberwareUserData.isCybercraftInstalled(getCachedStack(0)))
        {
            entityLivingBase.removeEffect(Effects.BLINDNESS);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void handleMissingEssentials(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.ticksExisted % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackCybereye = cyberwareUserData.getCybercraft(getCachedStack(0));
        if (!itemStackCybereye.isEmpty())
        {
            boolean isPowered = cyberwareUserData.usePower(itemStackCybereye, getPowerConsumption(itemStackCybereye));
            if ( entityLivingBase.level.isClientSide
                    && entityLivingBase == Minecraft.getInstance().player )
            {
                isBlind = !isPowered;
            }
        }
        else if ( entityLivingBase.level.isClientSide
                && entityLivingBase == Minecraft.getInstance().player )
        {
            isBlind = false;
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void overlayPre(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
        {
            PlayerEntity entityPlayer = Minecraft.getInstance().player;

            if (isBlind && !entityPlayer.isCreative())
            {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
                Minecraft.getInstance().getTextureManager().bind(EssentialsMissingHandler.BLACK_PX);
                ClientUtils.drawTexturedModalRect(0, 0, 0, 0, Minecraft.getInstance().displayWidth, Minecraft.getInstance().displayHeight);
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return LibConstants.CYBEREYES_CONSUMPTION;
    }
}
