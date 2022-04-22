package com.rena.cyberware.api.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;

public interface IHudElement {

    public static enum EnumAnchorHorizontal
    {
        LEFT,
        RIGHT;
    }

    public static enum EnumAnchorVertical
    {
        TOP,
        BOTTOM;
    }

    public void render(PlayerEntity playerEntity, MatrixStack resolution, boolean isHUDjackAvailable, boolean isConfigOpen, float partialTicks);

    public boolean canMove();

    public void setX(int x);
    public void setY(int y);
    public int getX();
    public int getY();

    public int getWidth();
    public int getHeight();

    public boolean canHide();
    public void setHidden(boolean hidden);
    public boolean isHidden();

    public EnumAnchorHorizontal getHorizontalAnchor();
    public void setHorizontalAnchor(EnumAnchorHorizontal anchor);

    public EnumAnchorVertical getVerticalAnchor();
    public void setVerticalAnchor(EnumAnchorVertical anchor);

    public void reset();

    public String getUniqueName();

    public void save(IHudSaveData data);
    public void load(IHudSaveData data);

}
