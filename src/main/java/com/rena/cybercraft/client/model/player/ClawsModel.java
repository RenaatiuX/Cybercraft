package com.rena.cybercraft.client.model.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

public class ClawsModel extends BipedModel<PlayerEntity> {

    public ModelRenderer claw1;
    public ModelRenderer claw2;
    public ModelRenderer claw3;

    public ClawsModel(float modelSize) {
        super(modelSize);

        texWidth = 64;
        texHeight = 64;

        claw1 = new ModelRenderer(this, 0, 0);
        claw1.addBox(-2.5F, 10.0F, -1.8F, 1, 7, 1, modelSize);
        claw1.setPos(-5.0F, 2.0F, 0.0F);

        claw2 = new ModelRenderer(this, 0, 0);
        claw2.addBox(-2.5F, 10.0F, -0.3F, 1, 7, 1, modelSize);
        claw1.addChild(claw2);

        claw3 = new ModelRenderer(this, 0, 0);
        claw3.addBox(-2.5F, 10.0F, 1.2F, 1, 7, 1, modelSize);
        claw1.addChild(claw3);

    }


    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder builder, int packedLightIn, int packedOverlayIn, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.claw1.render(matrixStack, builder, packedLightIn, packedOverlayIn);
    }
}
