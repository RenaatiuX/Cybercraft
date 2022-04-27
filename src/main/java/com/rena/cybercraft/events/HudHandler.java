package com.rena.cybercraft.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.hud.CybercraftHudDataEvent;
import com.rena.cybercraft.api.hud.CybercraftHudEvent;
import com.rena.cybercraft.api.hud.IHudElement;
import com.rena.cybercraft.api.hud.NotificationInstance;
import com.rena.cybercraft.api.item.IHudjack;
import com.rena.cybercraft.client.KeyBinds;
import com.rena.cybercraft.client.screens.hud.MissingPowerDisplay;
import com.rena.cybercraft.client.screens.hud.NotificationDisplay;
import com.rena.cybercraft.client.screens.hud.PowerDisplay;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HudHandler {

    public static final HudHandler INSTANCE = new HudHandler();

    private static class NotificationStack<T> extends Stack<T>
    {
        private int maxSize;

        public NotificationStack(int size)
        {
            super();
            this.maxSize = size;
        }

        @Override
        public T push(T object)
        {
            while (this.size() >= maxSize)
            {
                this.remove(0);
            }
            return super.push(object);
        }
    }

    public static void addNotification(NotificationInstance notification)
    {
        notifications.push(notification);
    }

    public static final ResourceLocation HUD_TEXTURE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/hud.png");
    public static Stack<NotificationInstance> notifications = new NotificationStack<>(5);

    private static PowerDisplay powerDisplay = new PowerDisplay();
    private static MissingPowerDisplay missingPowerDisplay = new MissingPowerDisplay();
    private static NotificationDisplay notificationDisplay = new NotificationDisplay();

    static
    {
        notificationDisplay.setHorizontalAnchor(IHudElement.EnumAnchorHorizontal.LEFT);
        notificationDisplay.setVerticalAnchor(IHudElement.EnumAnchorVertical.BOTTOM);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void addHudElements(CybercraftHudEvent event)
    {
        if (event.isHudjackAvailable())
        {
            event.addElement(powerDisplay);
            event.addElement(missingPowerDisplay);
            event.addElement(notificationDisplay);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void saveHudElements(CybercraftHudDataEvent event)
    {
        event.addElement(powerDisplay);
        event.addElement(missingPowerDisplay);
        event.addElement(notificationDisplay);
    }

    private int cache_tickExisted = 0;
    private double cache_floatingFactor = 0.0D;
    private List<IHudElement> cache_hudElements = new ArrayList<>();
    private boolean cache_isHUDjackAvailable = false;
    private boolean cache_promptToOpenMenu = false;
    private int cache_hudColorHex = 0x00FFFF;

    private int lastTickExisted = 0;
    private double lastVelX = 0;
    private double lastVelY = 0;
    private double lastVelZ = 0;
    private double lastLastVelX = 0;
    private double lastLastVelY = 0;
    private double lastLastVelZ = 0;


    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onDrawScreenPost(TickEvent.RenderTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        ServerPlayerEntity entityPlayerSP = mc.player;
        if (entityPlayerSP == null) return;

        MatrixStack matrixStack = new MatrixStack(Minecraft.getInstance());

        if (entityPlayerSP.tickCount != cache_tickExisted) {
            cache_tickExisted = entityPlayerSP.tickCount;

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayerSP);
            if (cyberwareUserData == null) return;

            cache_floatingFactor = 0.0D;
            boolean isHUDjackAvailable = false;

            List<ItemStack> listHUDjackItems = cyberwareUserData.getHudjackItems();
            for (ItemStack stack : listHUDjackItems) {
                if (((IHudjack) CybercraftAPI.getCybercraft(stack)).isActive(stack)) {
                    isHUDjackAvailable = true;
                    if (CybercraftConfig.C_HUD.enableFloat.get()) {
                        if (CybercraftAPI.getCybercraft(stack) == ItemInit.EYE_UPGRADES.get()) {
                            cache_floatingFactor = CybercraftConfig.C_HUD.hudlensFloat.get();
                        } else {
                            cache_floatingFactor = CybercraftConfig.C_HUD.hudjackFloat.get();
                        }
                    }
                    break;
                }
            }

            CybercraftHudEvent hudEvent = new CybercraftHudEvent(matrixStack, isHUDjackAvailable);
            MinecraftForge.EVENT_BUS.post(hudEvent);
            cache_hudElements = hudEvent.getElements();
            cache_isHUDjackAvailable = hudEvent.isHudjackAvailable();
            cache_promptToOpenMenu = cyberwareUserData.getActiveItems().size() > 0
                    && !cyberwareUserData.hasOpenedRadialMenu();
            cache_hudColorHex = cyberwareUserData.getHudColorHex();
        }

        matrixStack.pushPose();

        double accelLastY = lastVelY - lastLastVelY;
        double accelY = entityPlayerSP.motionY - lastVelY;
        double accelPitch = accelLastY + (accelY - accelLastY) * (event.renderTickTime + entityPlayerSP.ticksExisted - lastTickExisted) / 2F;

        double pitchCameraMove = cache_floatingFactor * ((entityPlayerSP.prevRenderArmPitch + (entityPlayerSP.renderArmPitch - entityPlayerSP.prevRenderArmPitch) * event.renderTickTime) - entityPlayerSP.rotationPitch);
        double yawCameraMove   = cache_floatingFactor * ((entityPlayerSP.prevRenderArmYaw   + (entityPlayerSP.renderArmYaw   - entityPlayerSP.prevRenderArmYaw  ) * event.renderTickTime) - entityPlayerSP.rotationYaw  );

        matrixStack.translate(yawCameraMove, pitchCameraMove + accelPitch * 50F * cache_floatingFactor, 0);

        if (entityPlayerSP.tickCount > lastTickExisted + 1)
        {
            lastTickExisted = entityPlayerSP.tickCount;
            lastLastVelX = lastVelX;
            lastLastVelY = lastVelY;
            lastLastVelZ = lastVelZ;
            lastVelX = entityPlayerSP.motionX;
            lastVelY = entityPlayerSP.motionY;
            lastVelZ = entityPlayerSP.motionZ;
        }

        for (IHudElement hudElement : cache_hudElements)
        {
            if (hudElement.getHeight() + GuiHudConfiguration.getAbsoluteY(matrixStack, hudElement) <= 3)
            {
                GuiHudConfiguration.setYFromAbsolute(matrixStack, hudElement, 0 - hudElement.getHeight() + 4);
            }

            if (GuiHudConfiguration.getAbsoluteY(matrixStack, hudElement) >= matrixStack.getScaledHeight() - 3)
            {
                GuiHudConfiguration.setYFromAbsolute(matrixStack, hudElement, matrixStack.getScaledHeight() - 4);
            }

            if (hudElement.getWidth() + GuiHudConfiguration.getAbsoluteX(matrixStack, hudElement) <= 3)
            {
                GuiHudConfiguration.setXFromAbsolute(matrixStack, hudElement, 0 - hudElement.getWidth() + 4);
            }

            if (GuiHudConfiguration.getAbsoluteX(matrixStack, hudElement) >= matrixStack.getScaledWidth() - 3)
            {
                GuiHudConfiguration.setXFromAbsolute(matrixStack, hudElement, matrixStack.getScaledWidth() - 4);
            }

            hudElement.render(entityPlayerSP, matrixStack, cache_isHUDjackAvailable, mc.currentScreen instanceof GuiHudConfiguration, event.renderTickTime);
        }

        // Display a prompt to the user to open the radial menu if they haven't yet
        if (cache_promptToOpenMenu)
        {
            String textOpenMenu = I18n.get("cybercraft.gui.open_menu", KeyBinds.menu.getName());
            FontRenderer fontRenderer = mc.font;
            fontRenderer.drawInBatch(textOpenMenu, matrixStack.getScaledWidth() - fontRenderer.width(textOpenMenu) - 5, 5, cache_hudColorHex);
        }

        matrixStack.popPose();
    }*/


}
