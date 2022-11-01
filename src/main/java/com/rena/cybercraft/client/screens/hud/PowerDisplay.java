package com.rena.cybercraft.client.screens.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.api.hud.HudElementBase;
import net.minecraft.entity.player.PlayerEntity;

public class PowerDisplay extends HudElementBase {

    private final static float[] colorLowPowerFloats = { 1.0F, 0.0F, 0.125F };
    private final static int colorLowPowerHex = 0xFF0020;

    private static float cache_percentFull = 0;
    private static int cache_power_capacity = 0;
    private static int cache_power_stored = 0;
    private static int cache_power_production = 0;
    private static int cache_power_consumption = 0;
    private static float[] cache_hudColor = colorLowPowerFloats;
    private static int cache_hudColorHex = 0x00FFFF;

    public PowerDisplay()
    {
        super("cybercraft:power");
        setDefaultX(5);
        setDefaultY(5);
        setHeight(25);
        setWidth(101);
    }

    @Override
    public void renderElement(int x, int y, PlayerEntity entityPlayer, MatrixStack stack, boolean hudjackAvailable, boolean isConfigOpen, float partialTicks) {

    }

    /*@Override
    public void renderElement(int x, int y, PlayerEntity entityPlayer, MatrixStack matrixStack, boolean isHUDjackAvailable, boolean isConfigOpen, float partialTicks)
    {
        if ( isHidden()
                || !isHUDjackAvailable ) {
            return;
        }

        boolean isRightAnchored = getHorizontalAnchor() == EnumAnchorHorizontal.RIGHT;

        if (entityPlayer.tickCount % 20 == 0)
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if (cyberwareUserData == null) return;

            cache_percentFull = cyberwareUserData.getPercentFull();
            cache_power_capacity = cyberwareUserData.getCapacity();
            cache_power_stored = cyberwareUserData.getStoredPower();
            cache_power_production = cyberwareUserData.getProduction();
            cache_power_consumption = cyberwareUserData.getConsumption();
            cache_hudColor = cyberwareUserData.getHudColor();
            cache_hudColorHex = cyberwareUserData.getHudColorHex();
        }

        if (cache_power_capacity == 0) return;

        boolean isLowPower = cache_percentFull <= 0.2F;
        boolean isCriticalPower = cache_percentFull <= 0.05F;

        if ( isCriticalPower
                && entityPlayer.tickCount % 4 == 0 )
        {
            return;
        }

        float[] colorFloats = isLowPower ? colorLowPowerFloats : cache_hudColor;
        int colorHex = isLowPower ? colorLowPowerHex : cache_hudColorHex;

        matrixStack.pushPose();

        FontRenderer fontRenderer = Minecraft.getInstance().font;

        // battery icon
        Minecraft.getInstance().getTextureManager().bind(HudHandler.HUD_TEXTURE);
        matrixStack.disableAlpha();
        matrixStack.enableBlend();
        matrixStack.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        int uOffset = isLowPower ? 39 : 0;
        int xOffset = isRightAnchored ? (x + getWidth() - 13) : x;
        int yBatterySize = Math.round(21F * cache_percentFull);

        GlStateManager.color(colorFloats[0], colorFloats[1], colorFloats[2]);

        // battery top part
        ClientUtils.drawTexturedModalRect(xOffset, y, uOffset, 0, 13, 2 + (21 - yBatterySize));
        // battery background
        ClientUtils.drawTexturedModalRect(xOffset, y + 2 + (21 - yBatterySize), 13 + uOffset, 2 + (21 - yBatterySize), 13, yBatterySize + 2);
        // battery foreground
        ClientUtils.drawTexturedModalRect(xOffset, y + 2 + (21 - yBatterySize), 26 + uOffset, 2 + (21 - yBatterySize), 13, yBatterySize + 2);

        // storage stats
        String textPowerStorage = cache_power_stored + " / " + cache_power_capacity;
        int xPowerStorage = isRightAnchored ? x + getWidth() - 15 - fontRenderer.width(textPowerStorage) : x + 15;
        fontRenderer.drawStringWithShadow(textPowerStorage, xPowerStorage, y + 4, colorHex);

        // progression stats
        String textPowerProgression = "-" + cache_power_consumption + " / +" + cache_power_production;
        int xPowerProgression = isRightAnchored ? x + getWidth() - 15 - fontRenderer.width(textPowerProgression) : x + 15;
        fontRenderer.drawStringWithShadow(textPowerProgression, xPowerProgression, y + 14, colorHex);

        matrixStack.popPose();
    }*/

}
