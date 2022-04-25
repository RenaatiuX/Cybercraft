package com.rena.cybercraft.client.model.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class EngineeringModel extends Model {

    public ModelRenderer head;
    public ModelRenderer bar;

    public EngineeringModel() {
        super(RenderType::entitySolid);
        texWidth = 24;
        texHeight = 17;

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-3F, -2F, -3F, 6, 2, 6);
        bar = new ModelRenderer(this, 0, 8);
        bar.addBox(-1F, 0F, -1F, 2, 7, 2);
        head.addChild(bar);

    }


    @Override
    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {

    }
}
