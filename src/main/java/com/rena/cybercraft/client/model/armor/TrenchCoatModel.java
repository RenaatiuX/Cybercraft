package com.rena.cybercraft.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class TrenchCoatModel extends BipedModel<LivingEntity> {

    public ModelRenderer bottomThing;
    private BipedModel modelBaseParent;

    public TrenchCoatModel(float modelSize) {
        super(modelSize);
        bottomThing = new ModelRenderer(this, 16, 0);
        bottomThing.addBox(-4.0F, 0F, -1.7F, 8, 12, 4, modelSize);
        bottomThing.setPos(0, 12.0F, 0.0F);
    }

    public void setDefaultModel(BipedModel modelBiped) {
        modelBaseParent = modelBiped;
    }

    @Override
    public void copyPropertiesTo(BipedModel<LivingEntity> modelBase) {
        super.copyPropertiesTo(modelBase);
        modelBaseParent.copyPropertiesTo(modelBase);
    }

    @Override
    public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
        modelBaseParent.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        modelBaseParent.setAllVisible(visible);
    }

   /* @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        modelBaseParent.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        bottomThing.setPos(0, leftLeg.y, leftLeg.z);
        bottomThing.xRot = Math.max(leftLeg.xRot, rightLeg.xRot) + 0.055F;

        modelBaseParent.swimAmount = swimAmount;
        modelBaseParent.head.visible     = head.visible    ;
        modelBaseParent.hat.visible = hat.visible;
        modelBaseParent.body.visible     = body.visible    ;
        modelBaseParent.rightArm.visible = rightArm.visible;
        modelBaseParent.leftArm.visible  = leftArm.visible ;
        modelBaseParent.rightLeg.visible = rightLeg.visible;
        modelBaseParent.leftLeg.visible  = leftLeg.visible ;
        modelBaseParent.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // add the bottom part of the trench coat
        GlStateManager.pushMatrix();

        if (entity.isBaby())
        {
            float factor = 0.5F;
            GlStateManager.scale(factor, factor, factor);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
        }
        else
        {
            if (entity.isCrouching())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
        }
        bottomThing.render(scale);

        GlStateManager.popMatrix();
    }*/
}
