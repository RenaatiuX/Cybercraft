package com.rena.cybercraft.client.renderer.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.client.renderer.entity.CyberZombieRenderer;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;

public class ZombieHighlightLayer extends LayerRenderer<CyberZombieEntity, ZombieModel<CyberZombieEntity>> {
    private final CyberZombieRenderer czRenderer;

    public ZombieHighlightLayer(IEntityRenderer<CyberZombieEntity, ZombieModel<CyberZombieEntity>> p_i50926_1_, CyberZombieRenderer czRenderer) {
        super(p_i50926_1_);
        this.czRenderer = czRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLightIn, CyberZombieEntity cyberZombie, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
