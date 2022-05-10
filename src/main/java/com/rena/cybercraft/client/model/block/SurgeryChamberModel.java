package com.rena.cybercraft.client.model.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SurgeryChamberModel extends Model {

    public ModelRenderer left;
    public ModelRenderer right;
    public SurgeryChamberModel() {
        super(RenderType::entitySolid);
        this.texWidth = 14;
        this.texHeight = 29;

        this.left = new ModelRenderer(this, 0, 0);
        this.left.addBox(0F, -22F, -1F, 6, 28, 1);

        this.right = new ModelRenderer(this, 0, 0);
        this.right.addBox(-6F, -22F, -1F, 6, 28, 1);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder builder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.left.render(matrixStack, builder, packedLight, packedOverlay);
    }
    public void renderRight(MatrixStack matrixStack, IVertexBuilder builder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.right.render(matrixStack, builder, packedLight, packedOverlay);
    }
}
