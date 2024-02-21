package com.rena.cybercraft.common.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.hud.CybercraftHudDataEvent;
import com.rena.cybercraft.api.hud.CybercraftHudEvent;
import com.rena.cybercraft.api.hud.IHudElement;
import com.rena.cybercraft.api.hud.NotificationInstance;
import com.rena.cybercraft.api.item.IHudjack;
import com.rena.cybercraft.client.screens.hud.MissingPowerDisplay;
import com.rena.cybercraft.client.screens.hud.NotificationDisplay;
import com.rena.cybercraft.client.screens.hud.PowerDisplay;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HudHandler {

    public static final HudHandler INSTANCE = new HudHandler();

    // http://stackoverflow.com/a/16206356/1754640
    private static class NotificationStack<T> extends Stack<T> {
        private int maxSize;

        public NotificationStack(int size) {
            super();
            this.maxSize = size;
        }

        @Override
        public T push(T object) {
            while (this.size() >= maxSize) {
                this.remove(0);
            }
            return super.push(object);
        }
    }

    public static void addNotification(NotificationInstance notification) {
        notifications.push(notification);
    }

    public static final ResourceLocation HUD_TEXTURE = new ResourceLocation(Cybercraft.MOD_ID, "textures/gui/hud.png");
    public static Stack<NotificationInstance> notifications = new NotificationStack<>(5);

    private static PowerDisplay powerDisplay = new PowerDisplay();
    private static MissingPowerDisplay missingPowerDisplay = new MissingPowerDisplay();
    private static NotificationDisplay notificationDisplay = new NotificationDisplay();

    static {
        notificationDisplay.setHorizontalAnchor(IHudElement.EnumAnchorHorizontal.LEFT);
        notificationDisplay.setVerticalAnchor(IHudElement.EnumAnchorVertical.BOTTOM);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void addHudElements(CybercraftHudEvent event) {
        if (event.isHudjackAvailable()) {
            event.addElement(powerDisplay);
            event.addElement(missingPowerDisplay);
            event.addElement(notificationDisplay);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void saveHudElements(CybercraftHudDataEvent event) {
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRender(@Nonnull RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.CHAT) {
            drawHUD(event.getMatrixStack(), event.getPartialTicks());
        }
    }

    private void drawHUD(MatrixStack matrixStack, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity clientPlayer = minecraft.player;
        if (clientPlayer == null) return;
        if (clientPlayer.tickCount != cache_tickExisted) {
            cache_tickExisted = clientPlayer.tickCount;
            ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(clientPlayer);
            if (cybercraftUserData == null) return;

            cache_floatingFactor = 0.0F;
            boolean isHUDjackAvailable = false;

            List<ItemStack> listHUDjackItems = cybercraftUserData.getHudjackItems();
            for (ItemStack stack : listHUDjackItems) {
                if (((IHudjack) CybercraftAPI.getCybercraft(stack)).isActive(stack)) {
                    isHUDjackAvailable = true;
                    if (CybercraftConfig.C_HUD.enableFloat.get()) {
                        if (CybercraftAPI.getCybercraft(stack) == ItemInit.EYES_UPGRADES_HUDLENS.get()) {
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
            cache_promptToOpenMenu = !cybercraftUserData.getActiveItems().isEmpty()
                    && !cybercraftUserData.hasOpenedRadialMenu();
            cache_hudColorHex = cybercraftUserData.getHudColorHex();
        }

        matrixStack.pushPose();
        double accelLastY = lastVelY - lastLastVelY;
        double accelY = clientPlayer.getY() - lastVelY;
        double accelPitch = accelLastY + (accelY - accelLastY) * (partialTick + clientPlayer.tickCount - lastTickExisted) / 2F;
    }
}
