package com.rena.cybercraft.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ComponentBoxContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ComponentBoxScreen extends ContainerScreen<ComponentBoxContainer> {
    public static final ResourceLocation TEXTURE = Cybercraft.modLoc("textures/gui/component_box.png");

    public ComponentBoxScreen(ComponentBoxContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void renderLabels(MatrixStack stack, int x, int y) {
        this.font.draw(stack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(stack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY - 15, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        Minecraft.getInstance().textureManager.bind(TEXTURE);
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        this.blit(stack, middleX, middleY, 0, 0, 175, 149);
    }


}
