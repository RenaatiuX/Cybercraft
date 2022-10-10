package com.rena.cybercraft.client.screens.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.rena.cybercraft.Cybercraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static com.rena.cybercraft.client.ClientUtils.drawTexturedModalRect;

public class InterfaceButton extends Button {

    private static final ResourceLocation SURGERY_GUI_TEXTURES = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/surgery.png");

    private final Type type;

    public InterfaceButton(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, ITextComponent p_i232255_5_, IPressable p_i232255_6_, Type type) {
        super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_);
        this.type = type;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (visible)
        {
            matrixStack.pushPose();
            GlStateManager._enableBlend();
            float trans = 0.4F;
            boolean isHovering = mouseX >= x
                    && mouseY >= y
                    && mouseX < x + width
                    && mouseY < y + height;
            if (isHovering) trans = 0.6F;

            GlStateManager._color4f(1.0F, 1.0F, 1.0F, trans);

            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bind(SURGERY_GUI_TEXTURES);

            drawTexturedModalRect(x, y, type.left + type.width, type.top, type.width, type.height);

            GlStateManager._color4f(1.0F, 1.0F, 1.0F, trans / 2F);
            drawTexturedModalRect(x, y, type.left, type.top, type.width, type.height);

            matrixStack.popPose();
        }
    }
}
