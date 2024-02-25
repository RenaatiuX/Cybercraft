package com.rena.cybercraft.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3f;

public class ScreenUtils {

    public static void renderEntityInScreen(MatrixStack stack, int x, int y, int scale, LivingEntity entityToRender){
        stack.pushPose();
        stack.translate(x,y,1);
        stack.scale(1,1,-1);
        stack.scale(scale, scale,scale);
        stack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
        stack.mulPose(Vector3f.YP.rotationDegrees(180f));
        float f2 = entityToRender.yBodyRot;
        float f3 = entityToRender.yRot;
        float f4 = entityToRender.xRot;
        float f5 = entityToRender.yHeadRotO;
        float f6 = entityToRender.yHeadRot;
        entityToRender.xRot = 0;
        entityToRender.xRotO = 0;
        entityToRender.yHeadRot = 0;
        entityToRender.yHeadRotO = 0;
        EntityRendererManager entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
        entityrenderermanager.setRenderShadow(false);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() ->{
            entityrenderermanager.render(entityToRender, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, stack, irendertypebuffer$impl, 15728880);
        });

        irendertypebuffer$impl.endBatch();
        entityrenderermanager.setRenderShadow(true);
        stack.popPose();
        entityToRender.yBodyRot = f2;
        entityToRender.yRot = f3;
        entityToRender.xRot = f4;
        entityToRender.yHeadRotO = f5;
        entityToRender.yHeadRot = f6;
    }
}
