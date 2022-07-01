package com.rena.cybercraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.model.block.BeaconLargeModel;
import com.rena.cybercraft.common.tileentities.TileEntityBeaconPost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class PostBeaconTileEntityRenderer extends TileEntityRenderer<TileEntityBeaconPost> {
    protected static final BeaconLargeModel BEACON_MODEL = new BeaconLargeModel();
    protected static final ResourceLocation TOWER_TEXTURE = Cybercraft.modLoc("textures/models/radio.png");
    protected static final ResourceLocation BASE_TEXTURE = Cybercraft.modLoc("textures/models/radio_base.png");

    public PostBeaconTileEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileEntityBeaconPost te, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (te != null && te.isMaster() && te.isTransformed()) {
            stack.pushPose();
            stack.translate(0.5, 1.5, 0.5);
            BEACON_MODEL.renderToBuffer(stack, buffer.getBuffer(BEACON_MODEL.renderType(TOWER_TEXTURE)), combinedLight, combinedOverlay, 1, 1, 1, 1);
            BEACON_MODEL.renderBase(stack, buffer.getBuffer(BEACON_MODEL.renderType(BASE_TEXTURE)), combinedLight, combinedOverlay, 1, 1, 1, 1);
            stack.popPose();
        }
    }
}
