package com.rena.cybercraft.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.ICybercraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VanillaWares {

    public static class SpiderEyeWare implements ICybercraft {

        public SpiderEyeWare()
        {
            MinecraftForge.EVENT_BUS.register(this);
        }

        @Override
        public EnumSlot getSlot(ItemStack stack)
        {
            return EnumSlot.EYES;
        }

        @Override
        public int maxInstalledStackSize(ItemStack stack)
        {
            return 1;
        }

        @Override
        public boolean isIncompatible(ItemStack stack, ItemStack other)
        {
            return CybercraftAPI.getCybercraft(other).isEssential(other);
        }

        @Override
        public boolean isEssential(ItemStack stack)
        {
            return true;
        }

        @Override
        public List<ITextComponent> getInfo(ItemStack stack) {
            return null;
        }

        @SubscribeEvent
        public void handleSpiderNightVision(CybercraftUpdateEvent event)
        {
            LivingEntity entityLivingBase = event.getEntityLiving();
            if (entityLivingBase.tickCount % 20 != 0) return;

            ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();
            if (cyberwareUserData.isCybercraftInstalled(Items.SPIDER_EYE))
            {
                entityLivingBase.addEffect(new EffectInstance(Effects.NIGHT_VISION, Integer.MAX_VALUE, -53, true, false));
            }
            else
            {
                EffectInstance effect = entityLivingBase.getEffect(Effects.NIGHT_VISION);
                if (effect != null && effect.getAmplifier() == -53)
                {
                    entityLivingBase.removeEffect(Effects.NIGHT_VISION);
                }
            }
        }

        /*@OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void onDrawScreenPost(RenderGameOverlayEvent.Pre event)
        {
            MatrixStack matrixStack = event.getMatrixStack();
            if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
            {
                PlayerEntity entityPlayer = Minecraft.getInstance().player;
                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
                if ( cyberwareUserData != null
                        && cyberwareUserData.isCybercraftInstalled(Items.SPIDER_EYE) )
                {
                    matrixStack.translate(0, event.getMatrixStack().getScaledHeight() / 5, 0);
                }
            }
        }


        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void onDrawScreenPost(RenderGameOverlayEvent.Post event)
        {
            MatrixStack matrixStack = event.getMatrixStack();
            if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
            {
                PlayerEntity entityPlayer = Minecraft.getInstance().player;
                ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
                if ( cyberwareUserData != null
                        && cyberwareUserData.isCybercraftInstalled(Items.SPIDER_EYE) )
                {
                    GlStateManager.translate(0, -event.getResolution().getScaledHeight() / 5, 0);
                }
            }
        }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public void handleSpiderVision(TickEvent.ClientTickEvent event)
        {
            if (event.phase != TickEvent.Phase.START) return;

            PlayerEntity entityPlayer = Minecraft.getInstance().player;

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && cyberwareUserData.isCybercraftInstalled(Items.SPIDER_EYE) )
            {
                if (Minecraft.getInstance().entityRenderer.getShaderGroup() == null)
                {
                    Minecraft.getInstance().entityRenderer.loadShader(new ResourceLocation("shaders/post/spider.json"));
                }
            }
            else if (entityPlayer != null && !entityPlayer.isSpectator())
            {
                ShaderGroup shaderGroup = Minecraft.getInstance().entityRenderer.getShaderGroup();
                if (shaderGroup != null && shaderGroup.getShaderGroupName().equals("minecraft:shaders/post/spider.json"))
                {
                    Minecraft.getInstance().entityRenderer.stopUseShader();
                }
            }
        }*/

        /*@Override
        public List<String> getInfo(ItemStack stack)
        {
            List<String> ret = new ArrayList<>();
            String[] desc = this.getDesciption(stack);
            if (desc != null && desc.length > 0)
            {
                String format = desc[0];
                if (format.length() > 0)
                {
                    ret.addAll(Arrays.asList(desc));
                }
            }
            return ret;
        }*/

        private String[] getDesciption(ItemStack stack)
        {
            return I18n.get("cybercraft.tooltip.spider_eye").split("\\\\n");
        }

        @Override
        public int getCapacity(ItemStack wareStack)
        {
            return 0;
        }

        @Override
        public NonNullList<Item> requiredInstalledItems() {
            return NonNullList.create();
        }

        @Override
        public void onAdded(LivingEntity entityLivingBase, ItemStack stack) {}

        @Override
        public void onRemoved(LivingEntity entityLivingBase, ItemStack stack) {}

        @Override
        public int getEssenceCost(ItemStack stack)
        {
            return 5;
        }

        @Override
        public Quality getQuality()
        {
            return null;
        }

        @Override
        public Item withQuality(Quality quality) {
            return Items.SPIDER_EYE;
        }


        @Override
        public boolean canHoldQuality(Quality quality)
        {
            return false;
        }
    }

}
