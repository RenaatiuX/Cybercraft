package com.rena.cybercraft.client.renderer.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.renderer.entity.CyberZombieRenderer;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;

public class ZombieHighlightLayer<T extends CyberZombieEntity> extends LayerRenderer<T, ZombieModel<T>>{

    private static final RenderType HIGHLIGHT = RenderType.eyes(new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_highlight.png"));
    private static final RenderType HIGHLIGHT_BRUTE = RenderType.eyes(new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_brute_highlight.png"));

    public ZombieHighlightLayer(IEntityRenderer<T, ZombieModel<T>> renderer) {
        super(renderer);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing,float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
