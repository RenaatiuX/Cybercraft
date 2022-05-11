package com.rena.cybercraft.client.screens;

import com.rena.cybercraft.api.CybercraftAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class GuiCybercraftMenu extends Screen {
    protected GuiCybercraftMenu(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    /*Minecraft mc = Minecraft.getInstance();
    boolean movedWheel = false;
    int selectedPart = -1;
    int lastMousedOverPart = -1;
    private boolean editing = false;
    private boolean color = false;
    private float radiusBase = 100F;
    private float innerRadiusBase = 40F;
    private boolean close = false;
    private GuiTextField hex;
    private float[][] colorOptions = new float[][] {
            new float[] {        0.0F,        1.0F,        1.0F },
            new float[] {  76F / 255F,        1.0F,        0.0F },
            new float[] {        1.0F, 216F / 255F,        0.0F },
            new float[] {        1.0F, 182F / 255F,  66F / 255F },
            new float[] { 212F / 255F, 119F / 255F,        1.0F },
            new float[] {        1.0F,        0.0F,        0.0F },
            new float[] {  61F / 255F, 174F / 255F,        1.0F },
            new float[] {        1.0F,  89F / 255F, 232F / 255F },
            new float[] {  28F / 255F,        1.0F, 156F / 255F },
            new float[] {        1.0F,        1.0F,        1.0F }
    };

    public GuiCybercraftMenu(ITextComponent textComponent) {
        super(textComponent);
    }

    private static final int ROW_SIZE = 5;

    @Override
    protected void init() {
        super.init();
        int numRows = ((colorOptions.length + ROW_SIZE - 1) / ROW_SIZE);
        hex = new GuiTextField(2, fontRenderer, width / 2 - 70, height / 2 - 100 + (30 * numRows), 140, 20);
        String s = Integer.toHexString(CybercraftAPI.getHUDColorHex()).toUpperCase();
        while (s.length() < 6)
        {
            s = "0" + s;
        }
        hex.setText(s);
        hex.setEnabled(false);
    }*/


}
