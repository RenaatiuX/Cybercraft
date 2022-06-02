package com.rena.cybercraft.client.model.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ScannerModel extends Model {

    public ModelRenderer bar;
    public ModelRenderer bar2;

    public ModelRenderer scanner;
    public ModelRenderer beam;

    public ScannerModel() {
        super(RenderType::entityTranslucent);
        this.texWidth = 34;
        this.texHeight = 10;

        this.bar = new ModelRenderer(this, 0, 0);
        this.bar.addBox(-8F, 7F, -7F, 16, 1, 1);
        this.bar2 = new ModelRenderer(this, 0, 0);
        this.bar2.addBox(-8F, 5F, -7F, 16, 1, 1);
        this.bar.addChild(bar2);

        this.scanner = new ModelRenderer(this, 0, 2);
        this.scanner.addBox(-7F, 2F, -8F, 3, 5, 3);

        this.beam = new ModelRenderer(this, 12, 2);
        this.beam.addBox(-6F, -2F, -7F, 1, 4, 1);
    }

    @Override
    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
            this.bar.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }

    public void renderScanner(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
    {
        this.scanner.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }

    public void renderBeam(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
    {
        this.beam.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }
}
