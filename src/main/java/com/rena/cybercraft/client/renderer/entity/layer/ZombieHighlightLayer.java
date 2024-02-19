package com.rena.cybercraft.client.renderer.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.renderer.entity.CyberZombieRenderer;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;

public class ZombieHighlightLayer<T extends CyberZombieEntity> extends LayerRenderer<T, ZombieModel<T>> {

    private static final ResourceLocation HIGHLIGHT = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_highlight.png");
    private static final ResourceLocation HIGHLIGHT_BRUTE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_brute_highlight.png");

    public ZombieHighlightLayer(IEntityRenderer<T, ZombieModel<T>> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(entitylivingbaseIn.isBrute() ? HIGHLIGHT_BRUTE : HIGHLIGHT));
        this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
