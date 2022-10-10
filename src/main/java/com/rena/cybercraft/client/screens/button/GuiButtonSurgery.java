package com.rena.cybercraft.client.screens.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class GuiButtonSurgery extends Button {
    public GuiButtonSurgery(int buttonId, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent textComponent, IPressable pressable) {
        super(buttonId, p_i232255_2_, p_i232255_3_, p_i232255_4_, textComponent, pressable);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
    }
}
