package com.rena.cybercraft.client.screens.button;

public class PageConfiguration {

    private float rotation;
    private float x;
    private float y;
    private float scale;
    private float boxWidth;
    private float boxHeight;
    private float boxX;
    private float boxY;

    PageConfiguration(float rotation, float x, float y, float scale)
    {
        this(rotation, x, y, scale, 0, 0, 0, 0);
    }

    PageConfiguration(float rotation, float x, float y, float scale, float boxWidth, float boxHeight, float boxX, float boxY)
    {
        this.rotation = rotation;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.boxHeight = boxHeight;
        this.boxWidth = boxWidth;
        this.boxX = boxX;
        this.boxY = boxY;
    }

    public PageConfiguration copy()
    {
        return new PageConfiguration(rotation, x, y, scale, boxWidth, boxHeight, boxX, boxY);
    }

}
