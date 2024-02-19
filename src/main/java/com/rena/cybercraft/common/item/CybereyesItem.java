package com.rena.cybercraft.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.common.events.EssentialsMissingHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CybereyesItem extends CybercraftItem {

    private static boolean isBlind;

    public CybereyesItem(Properties properties, EnumSlot slots, Quality q) {
        super(properties, slots, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isEssential(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack other) {
        return CybercraftAPI.getCybercraft(other).isEssential(other);
    }

    @Override
    public boolean canHoldQuality(Quality quality) {
        return true;
    }

    @SubscribeEvent
    public void handleBlindnessImmunity(CybercraftUpdateEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (!entityLivingBase.addEffect(new EffectInstance(Effects.BLINDNESS))) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_EYES.get())) {
            entityLivingBase.removeEffect(Effects.BLINDNESS);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void handleMissingEssentials(CybercraftUpdateEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.tickCount % 20 != 0) return;

        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackCybereye = cyberwareUserData.getCybercraft(ItemInit.CYBER_EYES.get());
        if (!itemStackCybereye.isEmpty()) {
            boolean isPowered = cyberwareUserData.usePower(itemStackCybereye, getPowerConsumption(itemStackCybereye));
            if (entityLivingBase.level.isClientSide
                    && entityLivingBase == Minecraft.getInstance().player) {
                isBlind = !isPowered;
            }
        } else if (entityLivingBase.level.isClientSide
                && entityLivingBase == Minecraft.getInstance().player) {
            isBlind = false;
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void overlayPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            PlayerEntity entityPlayer = Minecraft.getInstance().player;

            if (isBlind && !entityPlayer.isCreative()) {
                MatrixStack stack = event.getMatrixStack();
                stack.pushPose();
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                Minecraft.getInstance().getTextureManager().bind(EssentialsMissingHandler.BLACK_PX);
                ClientUtils.drawTexturedModalRect(0, 0, 0, 0, Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getWidth());
                stack.popPose();
            }
        }
    }

    @Override
    public int getPowerConsumption(ItemStack stack) {
        return LibConstants.CYBEREYES_CONSUMPTION;
    }
}
