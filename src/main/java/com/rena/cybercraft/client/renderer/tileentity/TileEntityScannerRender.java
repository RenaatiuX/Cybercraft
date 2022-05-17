package com.rena.cybercraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.client.model.block.ScannerModel;
import com.rena.cybercraft.common.tileentities.TileEntityScanner;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

public class TileEntityScannerRender extends TileEntityRenderer<TileEntityScanner> {

    public static final ResourceLocation LASER_LOCATION = new ResourceLocation("textures/models/scanner.png");
    private final ScannerModel SCANNER_MODEL = new ScannerModel();

    public TileEntityScannerRender(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileEntityScanner scanner, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int combinedLight, int combinedOverlay) {
    }
}
