package com.rena.cybercraft.common.effect;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.Cybercraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NeuropozyneEffect extends Effect {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/gui/potions.png");
    private int iconIndex;

    public NeuropozyneEffect(EffectType type, int color, int iconIndex) {
        super(type, color);
        this.iconIndex = iconIndex;
    }




    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) {
        render(x + 3, y + 3, alpha);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {
        render(x + 6, y + 7, 1);
    }

    @OnlyIn(Dist.CLIENT)
    private void render(int x, int y, float alpha)
    {

        Minecraft.getInstance().getTextureManager().bind(TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuilder();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        RenderSystem.color4f(1, 1, 1, alpha);

        int textureX = iconIndex % 8 * 18;
        int textureY = 198 + iconIndex / 8 * 18;

        buf.vertex(x, y + 18, 0).uv(textureX * 0.00390625F, (textureY + 18) * 0.00390625F).endVertex();
        buf.vertex(x + 18, y + 18, 0).uv((textureX + 18) * 0.00390625F, (textureY + 18) * 0.00390625F).endVertex();
        buf.vertex(x + 18, y, 0).uv((textureX + 18F) * 0.00390625F, textureY * 0.00390625F).endVertex();
        buf.vertex(x, y, 0).uv(textureX * 0.00390625F, textureY * 0.00390625F).endVertex();

        tessellator.end();
    }
}
