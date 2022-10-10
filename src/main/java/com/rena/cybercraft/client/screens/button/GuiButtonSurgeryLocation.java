package com.rena.cybercraft.client.screens.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.rena.cybercraft.Cybercraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static com.rena.cybercraft.client.ClientUtils.drawTexturedModalRect;

public class GuiButtonSurgeryLocation extends Button {

    public static final int buttonSize = 16;
    public float x3;
    public float y3;
    public float z3;
    public float xPos;
    public float yPos;

    private static final ResourceLocation SURGERY_GUI_TEXTURES = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/surgery.png");

    public GuiButtonSurgeryLocation(int buttonId, float x3, float y3, float z3, ITextComponent textComponent, IPressable pressable) {
        super(buttonId, 0, 0, buttonSize, textComponent, pressable);
        this.x3 = x3;
        this.y3 = y3;
        this.z3 = z3;
        this.visible = false;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (visible)
        {
            matrixStack.pushPose();
            GlStateManager._enableBlend();

            float trans = 0.4F;
            if ( mouseX >= x
                    && mouseY >= y
                    && mouseX < x + buttonSize
                    && mouseY < y + buttonSize )
            {
                trans = 0.6F;
            }
            GlStateManager._color4f(1.0F, 1.0F, 1.0F, trans);

            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bind(SURGERY_GUI_TEXTURES);
            matrixStack.translate(xPos, yPos, 0);
            drawTexturedModalRect(0, 0, 194, 0, width, height);

            matrixStack.popPose();
        }
    }
}
