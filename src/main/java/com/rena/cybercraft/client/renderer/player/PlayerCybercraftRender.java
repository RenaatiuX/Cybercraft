package com.rena.cybercraft.client.renderer.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.EnableDisableHelper;
import com.rena.cybercraft.client.model.player.ClawsModel;
import com.rena.cybercraft.common.item.HandUpgradeItem;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;

public class PlayerCybercraftRender extends PlayerRenderer {

    public boolean doMuscles = false;
    public boolean doRobo = false;
    public boolean doRusty = false;

    private static final ResourceLocation MUSCLES = new ResourceLocation(Cybercraft.MOD_ID, "textures/models/player_muscles.png");
    private static final ResourceLocation CYBER_PART = new ResourceLocation(Cybercraft.MOD_ID, "textures/models/player_robot.png");
    private static final ResourceLocation CYBER_PART_RUSTY = new ResourceLocation(Cybercraft.MOD_ID, "textures/models/player_rusty_robot.png");

    private static ClawsModel CLAWS = new ClawsModel(0.0F);
    public PlayerCybercraftRender(EntityRendererManager entity, boolean arms) {
        super(entity, arms);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractClientPlayerEntity entity) {
        return doRusty ? CYBER_PART_RUSTY : doRobo ? CYBER_PART :
                doMuscles ? MUSCLES : super.getTextureLocation(entity);
    }

    public void setMainModel(PlayerModel modelPlayer)
    {
        model = modelPlayer;
    }

    @Override
    public void renderRightHand(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, AbstractClientPlayerEntity player) {
        Minecraft.getInstance().getTextureManager().bind(CYBER_PART);
        super.renderRightHand(matrixStack, buffer, combinedLight, player);

        if ( Minecraft.getInstance().options.mainHand != HandSide.RIGHT
                || !player.getMainHandItem().isEmpty() )
        {
            return;
        }

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(player);
        if (cyberwareUserData == null) return;

        ItemStack itemStackClaws = cyberwareUserData.getCybercraft(ItemInit.HAND_UPGRADES_CLAWS.get());
        if ( !itemStackClaws.isEmpty()
                && cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_ARM_RIGHT.get())
                && EnableDisableHelper.isEnabled(itemStackClaws) )
        {
            matrixStack.pushPose();

            float percent = ((Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getFrameTime() - HandUpgradeItem.clawsTime) / 4F);
            percent = Math.min(1.0F, percent);
            percent = Math.max(0F, percent);
            percent = (float) Math.sin(percent * Math.PI / 2F);
            CLAWS.claw1.yRot = 0.00F;
            CLAWS.claw1.zRot = 0.07F;
            CLAWS.claw1.xRot = 0.00F;
            CLAWS.claw1.setPos(-5.0F, -5.0F + (7F * percent), 0.0F);
            //CLAWS.claw1.render(0.0625F);
            matrixStack.popPose();
        }
    }

    @Override
    public void renderLeftHand(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, AbstractClientPlayerEntity player) {
        Minecraft.getInstance().getTextureManager().bind(CYBER_PART);
        super.renderLeftHand(matrixStack, buffer, combinedLight, player);

        if ( Minecraft.getInstance().options.mainHand != HandSide.LEFT
                || !player.getMainHandItem().isEmpty() )
        {
            return;
        }

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(player);
        if (cyberwareUserData == null) return;

        ItemStack itemStackClaws = cyberwareUserData.getCybercraft(ItemInit.HAND_UPGRADES_CLAWS.get());
        if ( !itemStackClaws.isEmpty()
                && cyberwareUserData.isCybercraftInstalled(ItemInit.CYBER_LIMB_ARM_LEFT.get())
                && EnableDisableHelper.isEnabled(itemStackClaws))
        {
            matrixStack.pushPose();

            float percent = ((Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getFrameTime() - HandUpgradeItem.clawsTime) / 4F);
            percent = Math.min(1.0F, percent);
            percent = Math.max(0F, percent);
            percent = (float) Math.sin(percent * Math.PI / 2F);
            CLAWS.claw1.yRot = 0.00F;
            CLAWS.claw1.zRot = 0.07F;
            CLAWS.claw1.xRot = 0.00F;
            CLAWS.claw1.setPos(-5.0F, -5.0F + (7F * percent), 0.0F);
            CLAWS.claw1.render(matrixStack, buffer.getBuffer(RenderType.entityCutout(getTextureLocation(player))), combinedLight, OverlayTexture.NO_OVERLAY);
            matrixStack.popPose();
        }
    }
}
