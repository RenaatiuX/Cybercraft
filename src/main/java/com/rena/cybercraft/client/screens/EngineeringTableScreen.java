package com.rena.cybercraft.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.EngineeringTableContainer;
import com.rena.cybercraft.common.recipe.ComponentSalvageRecipe;
import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.EngineeringDestroyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class EngineeringTableScreen extends ContainerScreen<EngineeringTableContainer> {
    public static final ResourceLocation TEXTURE = Cybercraft.modLoc("textures/gui/engineering.png");

    public EngineeringTableScreen(EngineeringTableContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void init() {
        super.init();
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        this.addButton(new SalvageButton(middleX + 39, middleY + 34, this::onSalvage));
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        Minecraft.getInstance().textureManager.bind(TEXTURE);
        int middleX = (this.width - this.imageWidth) / 2;
        int middleY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, middleX, middleY, 0, 0, 176, 166);
    }

    private void onSalvage(Button b){
        CCNetwork.PACKET_HANDLER.sendToServer(new EngineeringDestroyPacket(menu.getTileEntity().getBlockPos()));
        ComponentSalvageRecipe recipe = menu.getTileEntity().getSalvageRecipe();
        Inventory inv = menu.getTileEntity().getComponentInventory();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float p_230430_4_) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, p_230430_4_);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    protected static class SalvageButton extends Button {

        public SalvageButton(int x, int y, IPressable onPress) {
            super(x, y, 21, 21, StringTextComponent.EMPTY, onPress);
        }

        @Override
        public void renderButton(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
            if (this.visible){
                if (this.isHovered){
                    Minecraft.getInstance().textureManager.bind(TEXTURE);
                    this.blit(matrixStack, this.x, this.y, 0, 166, 21,21);
                }
            }
        }
    }
}
