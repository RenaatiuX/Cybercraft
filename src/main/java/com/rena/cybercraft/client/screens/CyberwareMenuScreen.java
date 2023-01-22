package com.rena.cybercraft.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.api.CybercraftAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CyberwareMenuScreen extends Screen {
    Minecraft mc = Minecraft.getInstance();
    boolean movedWheel = false;
    int selectedPart = -1;
    int lastMousedOverPart = -1;
    private boolean editing = false;
    private boolean color = false;
    private float radiusBase = 100F;
    private float innerRadiusBase = 40F;
    private boolean close = false;
    private TextFieldWidget hex;
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

    private static final int ROW_SIZE = 5;
    protected CyberwareMenuScreen(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Override
    protected void init() {
        super.init();
        int numRows = ((colorOptions.length + ROW_SIZE - 1) / ROW_SIZE);
        hex = new TextFieldWidget(font, width / 2 - 70, height / 2 - 100 + (30 * numRows), 140, 20, new TranslationTextComponent("Cybercraft.menu"));
        String s = Integer.toHexString(CybercraftAPI.getHUDColorHex()).toUpperCase();
        while (s.length() < 6)
        {
            s = "0" + s;
        }
        hex.setValue(s);
        //hex.isEditable(false);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

}
