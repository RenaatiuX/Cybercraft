package com.rena.cybercraft.client.screens.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.hud.HudElementBase;
import com.rena.cybercraft.api.hud.INotification;
import com.rena.cybercraft.api.hud.NotificationInstance;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.common.ArmorClass;
import com.rena.cybercraft.common.events.HudHandler;
import com.rena.cybercraft.common.tileentities.TileEntityBeacon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class NotificationDisplay extends HudElementBase {

    public NotificationDisplay() {
        super("cybercraft:notification");
        setDefaultX(5);
        setDefaultY(5 - 20);
        setWidth(5 * 18);
        setHeight(14 + 20 + 4);
        setDefaultVerticalAnchor(EnumAnchorVertical.BOTTOM);
    }

    private static int cache_tickExisted = -1;
    private static int cache_tierRadio = -1;
    private static boolean cache_isWearingLightArmor = false;
    private static final NotificationInstance[] examples = new NotificationInstance[]{
            new NotificationInstance(0, new NotificationArmor(true)),
            new NotificationInstance(0, new NotificationArmor(false)),
            new NotificationInstance(0, new NotificationArmor(true)),
            new NotificationInstance(0, new NotificationArmor(false))
    };

    @Override
    public void renderElement(int x, int y, PlayerEntity entityPlayer, MatrixStack resolution, boolean isHUDjackAvailable, boolean isConfigOpen, float partialTicks) {
        if (this.isHidden() || !isHUDjackAvailable) {
            return;
        }

        boolean isTopAnchored = getVerticalAnchor() == EnumAnchorVertical.TOP;
        boolean isRightAnchored = getHorizontalAnchor() == EnumAnchorHorizontal.RIGHT;

        float currentTime = entityPlayer.tickCount + partialTicks;

        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();

        Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);

        if (entityPlayer.tickCount != cache_tickExisted) {
            cache_tickExisted = entityPlayer.tickCount;
            boolean wasWearingLightArmor = cache_isWearingLightArmor;
            cache_isWearingLightArmor = ArmorClass.isWearingLightOrNone(entityPlayer);
            if (cache_isWearingLightArmor != wasWearingLightArmor) {
                HudHandler.addNotification(new NotificationInstance(currentTime, new NotificationArmor(cache_isWearingLightArmor)));
            }

            int tierRadioPrevious = cache_tierRadio;
            /*cache_tierRadio = TileEntityBeacon.isInRange(entityPlayer.level, entityPlayer.getX(), entityPlayer.getY(), entityPlayer.getZ());
            if (cache_tierRadio != tierRadioPrevious) {
                HudHandler.addNotification(new NotificationInstance(currentTime, new NotificationRadio(cache_tierRadio)));
            }*/
        }

        // Render some placeholder notifications if the Hud config GUI is open so that the player can see what it'll look like in use
        if (isConfigOpen) {
            for (int indexNotification = 0; indexNotification < examples.length; indexNotification++) {
                NotificationInstance notificationInstance = examples[indexNotification];
                INotification notification = notificationInstance.getNotification();
                double percentVisible = 0F;
                if (indexNotification == 0) {
                    percentVisible = (entityPlayer.tickCount % 40F) / 40F;
                }
                float yOffset = (float) (20F * Math.sin(percentVisible * Math.PI / 2F));

                RenderSystem.pushMatrix();
                RenderSystem.color3f(1.0F, 1.0F, 1.0F);
                resolution.translate(0F, isTopAnchored ? -yOffset : yOffset, 0F);
                int index = (examples.length - 1) - indexNotification;
                int xPos = isRightAnchored ? (x + getWidth() - ((index + 1) * 18)) : (x + index * 18);
                notification.render(xPos, y + (isTopAnchored ? 20 : 0));
                RenderSystem.popMatrix();
            }
        } else {
            List<NotificationInstance> notificationsElapsed = new ArrayList<>();
            for (int indexNotification = 0; indexNotification < HudHandler.notifications.size(); indexNotification++) {
                NotificationInstance notificationInstance = HudHandler.notifications.get(indexNotification);
                INotification notification = notificationInstance.getNotification();
                if (currentTime - notificationInstance.getCreatedTime() < notification.getDuration() + 25) {
                    double percentVisible = Math.max(0F, (currentTime - notificationInstance.getCreatedTime() - notification.getDuration()) / 30F);
                    float yOffset = (float) (20F * Math.sin(percentVisible * Math.PI / 2F));
                    RenderSystem.pushMatrix();
                    RenderSystem.color3f(1.0F, 1.0F, 1.0F);
                    resolution.translate(0F, isTopAnchored ? -yOffset : yOffset, 0F);
                    int index = (HudHandler.notifications.size() - 1) - indexNotification;
                    int xPos = isRightAnchored ? (x + getWidth() - ((index + 1) * 18)) : (x + index * 18);
                    notification.render(xPos, y + (isTopAnchored ? 20 : 0));
                    RenderSystem.popMatrix();
                } else {
                    notificationsElapsed.add(notificationInstance);
                }
            }
            for (NotificationInstance notificationInstance : notificationsElapsed) {
                HudHandler.notifications.remove(notificationInstance);
            }
        }
        RenderSystem.popMatrix();
    }

    @OnlyIn(Dist.CLIENT)
    private static class NotificationArmor implements INotification {
        private boolean light;

        private NotificationArmor(boolean light) {
            this.light = light;
        }

        @Override
        public void render(int x, int y) {
            Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);
            RenderSystem.pushMatrix();
            float[] color = CybercraftAPI.getHUDColor();
            RenderSystem.color3f(color[0], color[1], color[2]);
            ClientUtils.drawTexturedModalRect(x, y + 1, 0, 25, 15, 14);
            RenderSystem.popMatrix();
            RenderSystem.color3f(1.0F, 1.0F, 1.0F);
            if (light) {
                ClientUtils.drawTexturedModalRect(x + 9, y + 1 + 7, 15, 25, 7, 9);
            } else {
                ClientUtils.drawTexturedModalRect(x + 8, y + 1 + 7, 22, 25, 8, 9);
            }
        }

        @Override
        public int getDuration() {
            return 20;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static class NotificationRadio implements INotification {
        private int tier;

        private NotificationRadio(int tier) {
            this.tier = tier;
        }

        @Override
        public void render(int x, int y) {
            Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);
            if (tier > 0) {
                RenderSystem.pushMatrix();
                float[] color = CybercraftAPI.getHUDColor();
                RenderSystem.color3f(color[0], color[1], color[2]);
                ClientUtils.drawTexturedModalRect(x, y + 1, 13, 39, 15, 14);
                RenderSystem.popMatrix();

                String textRadioTier = tier == 1 ? new TranslationTextComponent("cybercraft.gui.radio_internal").getString()
                        : Integer.toString(tier - 1);
                FontRenderer fontRenderer = Minecraft.getInstance().font;
                fontRenderer.drawShadow(new MatrixStack(), textRadioTier, x + 15 - fontRenderer.width(textRadioTier), y + 9, 0xFFFFFF);
            } else {
                float[] color = CybercraftAPI.getHUDColor();
                RenderSystem.color3f(color[0], color[1], color[2]);
                ClientUtils.drawTexturedModalRect(x, y + 1, 28, 39, 15, 14);
            }
        }

        @Override
        public int getDuration() {
            return 40;
        }
    }
}
