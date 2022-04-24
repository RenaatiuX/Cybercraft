package com.rena.cyberware.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cyberware.Cybercraft;
import com.rena.cyberware.common.container.ScannerContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ScannerScreen extends ContainerScreen<ScannerContainer> {

    public static final ResourceLocation TEXTURE = Cybercraft.modeLoc("textures/gui/scanner.png");

    public ScannerScreen(ScannerContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void renderBg(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        Minecraft.getInstance().textureManager.bind(TEXTURE);
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        this.blit(stack, middleX, middleY, 0, 0, 176, 166);
    }
}
