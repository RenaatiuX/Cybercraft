package com.rena.cybercraft.client.model.block;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BeaconLargeModel extends Model {

    public ModelRenderer bar1;
    public ModelRenderer bar2;
    public ModelRenderer bar3;
    public ModelRenderer bar4;
    public ModelRenderer base;

    public ModelRenderer[] crossbars;

    public BeaconLargeModel()
    {
        super(RenderType::entitySolid);
        texWidth = 128;
        texHeight = 256;

        float angle = 173.8F;

        bar1 = new ModelRenderer(this, 0, 0);
        bar1.addBox(-1.5F, 8F, -2F, 3, 163, 3);
        bar1.yRot = (float) Math.toRadians(45F);
        bar1.xRot = (float) Math.toRadians(angle);

        bar2 = new ModelRenderer(this, 0, 0);
        bar2.addBox(-1.5F, 8F, -2F, 3, 163, 3);
        bar2.yRot = (float) Math.toRadians(135F);
        bar2.xRot = (float) Math.toRadians(angle);

        bar3 = new ModelRenderer(this, 0, 0);
        bar3.addBox(-1.5F, 8F, -2F, 3, 163, 3);
        bar3.yRot = (float) Math.toRadians(-45F);
        bar3.xRot = (float) Math.toRadians(angle);

        bar4 = new ModelRenderer(this, 0, 0);
        bar4.addBox(-1.5F, 8F, -2F, 3, 163, 3);
        bar4.yRot = (float) Math.toRadians(-135F);
        bar4.xRot = (float) Math.toRadians(angle);

        float hPercent = (float) -Math.cos(Math.toRadians(angle));
        float wPercent = (float) Math.sin(Math.toRadians(angle));

        int num = 6;

        float progressChg = 25F;
        crossbars = new ModelRenderer[num * 4];
        float x;
        float y;
        float z;
        float progress = 10F + progressChg;
        float pi4 = (float) Math.PI / 4F;
        for (int i = 0; i < num; i++)
        {
            x = (float) Math.ceil(.3F + -wPercent * progress * pi4);
            z = -1.6F + -wPercent * progress * (pi4 - .1F);

            y = -hPercent * progress;

            ModelRenderer bar = new ModelRenderer(this, 12, 0);
            bar.addBox(x, y, z, (int) (-x * 2F), 2, 2);
            crossbars[i * 4] = bar;

            ModelRenderer bar2 = new ModelRenderer(this, 12, 0);
            bar2.addBox(x, y, -z - 2F, (int) (-x * 2F), 2, 2);
            crossbars[i * 4 + 1] = bar2;


            ModelRenderer bar3 = new ModelRenderer(this, 12, 0);
            bar3.addBox(z, y, x, 2, 2, (int) (-x * 2F));
            crossbars[i * 4 + 2] = bar3;


            ModelRenderer bar4 = new ModelRenderer(this, 12, 0);
            bar4.addBox(-z - 2F, y, x, 2, 2, (int) (-x * 2F));
            crossbars[i * 4 + 3] = bar4;

            progress += progressChg;

        }

        texWidth = 256;
        texWidth = 64;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-24F, -168F, -24F, 48, 4, 48);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int f1, int f2, float f3, float f4, float f5, float scale) {
        bar1.render(matrixStack, vertexBuilder, f1, f2, f3, f4, f5, scale);
        bar2.render(matrixStack, vertexBuilder, f1, f2, f3, f4, f5, scale);
        bar3.render(matrixStack, vertexBuilder, f1, f2, f3, f4, f5, scale);
        bar4.render(matrixStack, vertexBuilder, f1, f2, f3, f4, f5, scale);

        for (ModelRenderer bar : crossbars)
        {
            if (bar != null)
            {
                bar.render(matrixStack, vertexBuilder, f1, f2, f3, f4, f5, scale);
            }
        }
    }

    public void renderBase(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int f, int f1, float f3, float f4, float f5, float scale) {

            base.render(matrixStack, vertexBuilder, f, f1, f3, f4, f5, scale);
    }

    public void setupAnim(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
