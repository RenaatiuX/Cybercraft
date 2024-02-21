package com.rena.cybercraft.client.screens.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.hud.HudElementBase;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.common.events.HudHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;

public class PowerDisplay extends HudElementBase {

    private final static float[] colorLowPowerFloats = {1.0F, 0.0F, 0.125F};
    private final static int colorLowPowerHex = 0xFF0020;

    private static float cache_percentFull = 0;
    private static int cache_power_capacity = 0;
    private static int cache_power_stored = 0;
    private static int cache_power_production = 0;
    private static int cache_power_consumption = 0;
    private static float[] cache_hudColor = colorLowPowerFloats;
    private static int cache_hudColorHex = 0x00FFFF;

    public PowerDisplay() {
        super("cybercraft:power");
        setDefaultX(5);
        setDefaultY(5);
        setHeight(25);
        setWidth(101);
    }

    @Override
    public void renderElement(int x, int y, PlayerEntity entityPlayer, MatrixStack stack, boolean hudjackAvailable, boolean isConfigOpen, float partialTicks) {
        if (this.isHidden() || !hudjackAvailable) {
            return;
        }
        boolean isRightAnchored = getHorizontalAnchor() == EnumAnchorHorizontal.RIGHT;
        if (entityPlayer.tickCount % 20 == 0) {
            ICybercraftUserData iCybercraftUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if (iCybercraftUserData == null) {
                return;
            }
            cache_percentFull = iCybercraftUserData.getPercentFull();
            cache_power_capacity = iCybercraftUserData.getCapacity();
            cache_power_stored = iCybercraftUserData.getStoredPower();
            cache_power_production = iCybercraftUserData.getProduction();
            cache_power_consumption = iCybercraftUserData.getConsumption();
            cache_hudColor = iCybercraftUserData.getHudColor();
            cache_hudColorHex = iCybercraftUserData.getHudColorHex();
        }

        if (cache_power_capacity == 0) {
            return;
        }

        boolean isLowPower = cache_percentFull <= 0.2F;
        boolean isCriticalPower = cache_percentFull <= 0.05F;
        if (isCriticalPower && entityPlayer.tickCount % 4 == 0) {
            return;
        }
        float[] colorFloats = isLowPower ? colorLowPowerFloats : cache_hudColor;
        int colorHex = isLowPower ? colorLowPowerHex : cache_hudColorHex;

        RenderSystem.pushMatrix();
        FontRenderer fontRenderer = Minecraft.getInstance().font;

        //battery icon
        Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int uOffset = isLowPower ? 39 : 0;
        int xOffset = isRightAnchored ? (x + getWidth() - 13) : x;
        int yBatterySize = Math.round(21F * cache_percentFull);

        RenderSystem.color3f(colorFloats[0], colorFloats[1], colorFloats[2]);

        // battery top part
        ClientUtils.drawTexturedModalRect(xOffset, y, uOffset, 0, 13, 2 + (21 - yBatterySize));
        // battery background
        ClientUtils.drawTexturedModalRect(xOffset, y + 2 + (21 - yBatterySize), 13 + uOffset, 2 + (21 - yBatterySize), 13, yBatterySize + 2);
        // battery foreground
        ClientUtils.drawTexturedModalRect(xOffset, y + 2 + (21 - yBatterySize), 26 + uOffset, 2 + (21 - yBatterySize), 13, yBatterySize + 2);

        // storage stats
        String textPowerStorage = cache_power_stored + " / " + cache_power_capacity;
        int xPowerStorage = isRightAnchored ? x + getWidth() - 15 - fontRenderer.width(textPowerStorage) : x + 15;
        fontRenderer.drawShadow(stack, textPowerStorage, xPowerStorage, y + 4, colorHex);

        // progression stats
        String textPowerProgression = "-" + cache_power_consumption + " / +" + cache_power_production;
        int xPowerProgression = isRightAnchored ? x + getWidth() - 15 - fontRenderer.width(textPowerProgression) : x + 15;
        fontRenderer.drawShadow(stack, textPowerProgression, xPowerProgression, y + 14, colorHex);

        RenderSystem.popMatrix();
    }
}
