package com.rena.cybercraft.api.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public abstract class HudElementBase implements IHudElement {

    private int defaultX = 0;
    private int defaultY = 0;
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private boolean hidden = false;
    private String name;

    private EnumAnchorHorizontal defaultHAnchor = EnumAnchorHorizontal.LEFT;
    private EnumAnchorVertical defaultVAnchor = EnumAnchorVertical.TOP;
    private EnumAnchorHorizontal hAnchor = EnumAnchorHorizontal.LEFT;
    private EnumAnchorVertical vAnchor = EnumAnchorVertical.TOP;

    public HudElementBase(String name) {
        this.name = name;
    }

    @Override
    public void render(PlayerEntity entityPlayer, MatrixStack stack, boolean isHUDjackAvailable, boolean isConfigOpen, float partialTicks) {
        int x = getX();
        int y = getY();
        if (getHorizontalAnchor() == EnumAnchorHorizontal.RIGHT) {
            x = Minecraft.getInstance().screen.width - x - getWidth();
        }
        if (getVerticalAnchor() == EnumAnchorVertical.BOTTOM) {
            y = Minecraft.getInstance().screen.height - y - getHeight();
        }

        renderElement(x, y, entityPlayer, stack, isHUDjackAvailable, isConfigOpen, partialTicks);
    }

    public abstract void renderElement(int x, int y, PlayerEntity entityPlayer, MatrixStack stack, boolean hudjackAvailable, boolean isConfigOpen, float partialTicks);

    public void setDefaultX(int x) {
        this.defaultX = x;
        setX(x);
    }

    public void setDefaultY(int y) {
        this.defaultY = y;
        setY(y);
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean canHide() {
        return true;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public EnumAnchorHorizontal getHorizontalAnchor() {
        return hAnchor;
    }

    public void setDefaultHorizontalAnchor(EnumAnchorHorizontal anchor) {
        defaultHAnchor = anchor;
        setHorizontalAnchor(anchor);
    }

    @Override
    public void setHorizontalAnchor(EnumAnchorHorizontal anchor) {
        hAnchor = anchor;
    }

    @Override
    public EnumAnchorVertical getVerticalAnchor() {
        return vAnchor;
    }

    public void setDefaultVerticalAnchor(EnumAnchorVertical anchor) {
        defaultVAnchor = anchor;
        setVerticalAnchor(anchor);
    }

    @Override
    public void setVerticalAnchor(EnumAnchorVertical anchor) {
        vAnchor = anchor;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    @Override
    public void reset() {
        x = defaultX;
        y = defaultY;
        vAnchor = defaultVAnchor;
        hAnchor = defaultHAnchor;
    }

    @Override
    public String getUniqueName() {
        return name;
    }

    @Override
    public void save(IHudSaveData data) {
        data.setInteger("x", x);
        data.setInteger("y", y);
        data.setBoolean("top", vAnchor == EnumAnchorVertical.TOP);
        data.setBoolean("left", hAnchor == EnumAnchorHorizontal.LEFT);
    }

    @Override
    public void load(IHudSaveData data) {
        x = data.getInteger("x");
        y = data.getInteger("y");
        vAnchor = data.getBoolean("top") ? EnumAnchorVertical.TOP : EnumAnchorVertical.BOTTOM;
        hAnchor = data.getBoolean("left") ? EnumAnchorHorizontal.LEFT : EnumAnchorHorizontal.RIGHT;
    }

}
