package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.IHudjack;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CybereyeUpgradeItem extends CybercraftItem implements IMenuItem, IHudjack {

    public static final int META_NIGHT_VISION = 0;
    public static final int META_UNDERWATER_VISION = 1;
    public static final int META_HUDJACK = 2;
    public static final int META_TARGETING = 3;
    public static final int META_ZOOM = 4;

    public CybereyeUpgradeItem(Properties properties, EnumSlot slot, Quality q) {
        super(properties, slot, q);
    }

    @Override
    public NonNullList<Item> requiredInstalledItems() {
        if (this == ItemInit.CYBER_EYE_UPGRADES_TARGETING.get()) {
            return NonNullList.of(
                    ItemInit.CYBER_EYES.get(),
                    ItemInit.CYBER_EYE_UPGRADES_HUDJACK.get());
        }

        return NonNullList.of(ItemInit.CYBER_EYES.get());
    }

    private static int cache_tickExisted = -1;
    private static boolean cache_isHighlighting = false;
    private static AxisAlignedBB cache_aabbHighlight = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    private static List<LivingEntity> entitiesInRange = new ArrayList<>(16);
    private static final float HIGHLIGHT_RANGE = 25F;

    /*@SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void handleHighlight(TickEvent.RenderTickEvent event)
    {
        PlayerEntity entityPlayer = Minecraft.getInstance().player;
        if (entityPlayer == null) return;

        if (entityPlayer.tickCount != cache_tickExisted)
        {
            cache_tickExisted = entityPlayer.tickCount;

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if (cyberwareUserData == null) return;
            ItemStack itemStackTargeting = cyberwareUserData.getCybercraft(getCachedStack(META_TARGETING));
            cache_isHighlighting = !itemStackTargeting.isEmpty()
                    && EnableDisableHelper.isEnabled(itemStackTargeting);
            if (cache_isHighlighting)
            {
                cache_aabbHighlight = new AxisAlignedBB(entityPlayer.posX - HIGHLIGHT_RANGE, entityPlayer.posY - HIGHLIGHT_RANGE, entityPlayer.posZ - HIGHLIGHT_RANGE,
                        entityPlayer.posX + entityPlayer.width + HIGHLIGHT_RANGE, entityPlayer.posY + entityPlayer.height + HIGHLIGHT_RANGE, entityPlayer.posZ + entityPlayer.width + HIGHLIGHT_RANGE);
            }
        }

        if (cache_isHighlighting)
        {
            if (event.phase == TickEvent.Phase.START)
            {
                entitiesInRange.clear();
                List<LivingEntity> entityLivingBases = entityPlayer.level.getEntitiesOfClass(LivingEntity.class, cache_aabbHighlight);
                double rangeSq = HIGHLIGHT_RANGE * HIGHLIGHT_RANGE;
                for (LivingEntity entityLivingBase : entityLivingBases)
                {
                    if ( entityPlayer.distanceToSqr(entityLivingBase) <= rangeSq
                            && entityLivingBase != entityPlayer
                            && !entityLivingBase.isGlowing() )
                    {
                        entityLivingBase.setGlowing(true);
                        entitiesInRange.add(entityLivingBase);
                    }
                }
            }
            else if (event.phase == TickEvent.Phase.END)
            {
                for (LivingEntity entityLivingBase : entitiesInRange)
                {
                    entityLivingBase.setGlowing(false);
                }
            }
        }
    }*/

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void handleFog(EntityViewRenderEvent.FogDensity event)
    {
        PlayerEntity entityPlayer = Minecraft.getInstance().player;
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData == null) return;

        if (cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_EYE_UPGRADES_UNDERWATER_VISION.get()))
        {
            if (entityPlayer.isEyeInFluid(FluidTags.WATER))
            {
                event.setDensity(0.01F);
                event.setCanceled(true);
            }
            else if (entityPlayer.isEyeInFluid(FluidTags.LAVA))
            {
                event.setDensity(0.7F);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void handleNightVision(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();
        ItemStack itemStackNightVision = cyberwareUserData.getCybercraft(ItemInit.CYBER_EYE_UPGRADES_NIGHT_VISION.get());

        if ( !itemStackNightVision.isEmpty()
                && EnableDisableHelper.isEnabled(itemStackNightVision) )
        {
            entityLivingBase.addEffect(new EffectInstance(Effects.NIGHT_VISION, Integer.MAX_VALUE, 53, true, false));
        }
        else
        {
            EffectInstance effect = entityLivingBase.getEffect(Effects.NIGHT_VISION);
            if (effect != null && effect.getAmplifier() == 53)
            {
                entityLivingBase.removeEffect(Effects.NIGHT_VISION);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void handleWaterVision(RenderBlockOverlayEvent event)
    {
        PlayerEntity entityPlayer = event.getPlayer();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData == null) return;
        ItemStack itemStackUnderwaterVision = cyberwareUserData.getCybercraft(ItemInit.CYBER_EYE_UPGRADES_UNDERWATER_VISION.get());

        if (!itemStackUnderwaterVision.isEmpty())
        {
            if ( event.getBlockForOverlay().getMaterial() == Material.WATER
                    || event.getBlockForOverlay().getMaterial() == Material.LAVA )
            {
                event.setCanceled(true);
            }
        }
    }

    private static boolean inUse = false;
    private static boolean wasInUse = false;

    private static int zoomSettingOn = 0;
    private static float fov = 0F;
    private static double sensitivity = 0D;
    private static PlayerEntity player = null;

    /*@SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void tickStart(TickEvent.ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START)
        {
            wasInUse = inUse;

            PlayerEntity entityPlayer = mc.player;

            if (!inUse && !wasInUse)
            {
                fov = mc.options.fovEffectScale;
                sensitivity = mc.options.sensitivity;
            }

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && cyberwareUserData.isCybercraftInstalled(getCachedStack(META_ZOOM)) )
            {
                player = entityPlayer;

                if (mc.options.cameraType == 0)
                {
                    switch (zoomSettingOn)
                    {
                        case 0:
                            mc.options.fovEffectScale = fov;
                            mc.options.sensitivity = sensitivity;
                            break;

                        case 1:
                            mc.options.fovEffectScale = fov;
                            mc.options.sensitivity = sensitivity;
                            int i = 0;
                            while (Math.abs((mc.options.fovEffectScale - ((fov + 5F)) / 2.0F)) > 2.5F && i < 200)
                            {
                                mc.options.fovEffectScale -= 2.5F;
                                mc.options.sensitivity -= 0.01F;
                                i++;
                            }
                            break;

                        case 2:
                            mc.options.fovEffectScale = fov;
                            mc.options.sensitivity = sensitivity;
                            i = 0;
                            while (Math.abs((mc.options.fovEffectScale - ((fov + 5F)) / 5.0F)) > 2.5F && i < 200)
                            {
                                mc.options.fovEffectScale -= 2.5F;
                                mc.options.sensitivity -= 0.01F;
                                i++;
                            }
                            break;

                        case 3:
                            mc.options.fovEffectScale = fov;
                            mc.options.sensitivity = sensitivity;
                            i = 0;
                            while (Math.abs((mc.options.fovEffectScale - ((fov + 5F)) / 12.0F)) > 2.5F && i < 200)
                            {
                                mc.options.fovEffectScale -= 2.5F;
                                mc.options.sensitivity -= 0.01F;
                                i++;
                            }
                            break;
                    }
                }

            }
            else
            {
                zoomSettingOn = 0;
            }
            inUse = zoomSettingOn != 0;

            if (!inUse && wasInUse)
            {
                mc.options.fovEffectScale = fov;
                mc.options.sensitivity = sensitivity;
            }

        }
    }*/


    @Override
    public boolean hasMenu(ItemStack stack) {
        return stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_NIGHT_VISION.get()
                || stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_HUDJACK.get()
                || stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_TARGETING.get()
                || stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_ZOOM.get();
    }

    @Override
    public void use(Entity entity, ItemStack stack) {
        if (stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_ZOOM.get())
        {
            if (entity == player)
            {
                if (entity.isShiftKeyDown())
                {
                    zoomSettingOn = (zoomSettingOn + 4 - 1) % 4;
                }
                else
                {
                    zoomSettingOn = (zoomSettingOn + 1) % 4;
                }
            }
            return;
        }
        EnableDisableHelper.toggle(stack);
    }

    @Override
    public String getUnlocalizedLabel(ItemStack stack) {
        if (stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_ZOOM.get())
        {
            return "cybercraft.gui.active.zoom";
        }
        return EnableDisableHelper.getUnlocalizedLabel(stack);
    }

    private static final float[] f = new float[] { 1.0F, 0.0F, 0.0F };

    @Override
    public float[] getColor(ItemStack stack) {
        if (stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_ZOOM.get())
        {
            return null;
        }
        return EnableDisableHelper.isEnabled(stack) ? f : null;
    }

    @Override
    public boolean isActive(ItemStack stack) {
        return stack.getItem() == ItemInit.CYBER_EYE_UPGRADES_HUDJACK.get()
                && EnableDisableHelper.isEnabled(stack);
    }
}
