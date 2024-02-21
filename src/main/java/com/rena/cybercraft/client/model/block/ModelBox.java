package com.rena.cybercraft.client.model.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;


public class ModelBox extends Model {

    public ModelRenderer box;

    public ModelBox() {
        super(RenderType::entitySolid);
        texWidth = 48;
        texHeight = 21;

        box = new ModelRenderer(this, 0, 0);
        box.addBox(-6F, -4.5F, -6F, 12, 9, 12);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int va1, int val2, float f1, float f2, float f3, float f4) {
        this.box.render(matrixStack, vertexBuilder, va1, val2, f1, f2, f3, f4);
    }
}
