package com.rena.cybercraft.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.common.container.SurgeryContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class SurgeryScreen extends ContainerScreen<SurgeryContainer> {
    public SurgeryScreen(SurgeryContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void renderBg(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {

    }
}
