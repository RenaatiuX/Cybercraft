package com.rena.cybercraft.client.screens.button;

public enum Type {

    BACK(176, 111, 18, 10),
    INDEX(176, 122, 12, 11);

    public int left;
    public int top;
    public int width;
    public int height;

    Type(int left, int top, int width, int height)
    {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

}
