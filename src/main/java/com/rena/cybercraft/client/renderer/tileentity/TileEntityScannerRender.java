package com.rena.cybercraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.model.block.ScannerModel;
import com.rena.cybercraft.common.tileentities.TileEntityScanner;
import com.rena.cybercraft.common.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Random;

public class TileEntityScannerRender extends TileEntityRenderer<TileEntityScanner> {

    public static final ResourceLocation LASER_LOCATION = Cybercraft.modLoc("textures/models/scanner.png");
    private static final ScannerModel SCANNER_MODEL = new ScannerModel();
    private static final int TICKS_PER_BEAM = 14;
    private static final double MAX_MOVE = 0.3;
    private int amountOfBeams = new Random().nextInt(4) + 1, amountOfMoves = 0, beamTicks = 0;
    private double scannerX = 0, scannerZ = 0;
    private Vector3d motion = Vector3d.ZERO;

    public TileEntityScannerRender(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileEntityScanner scanner, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (scanner != null && !scanner.getItem(1).isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            float rotation = getCorrectRotation(scanner);
            RenderUtils.renderItem(scanner.getItem(1), new double[]{0.5d, 0.4d, 0.5d}, Vector3f.YP.rotationDegrees(rotation), matrixStack, buffer, combinedOverlay, RenderUtils.getLightLevel(mc.player.level, scanner.getBlockPos()), 0.7f);

            matrixStack.pushPose();
            matrixStack.translate(0.5, 0.5, 0.5);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
            matrixStack.translate(0.3 + scannerX, 0, 0.4 + scannerZ);
            SCANNER_MODEL.renderScanner(matrixStack, buffer.getBuffer(SCANNER_MODEL.renderType(LASER_LOCATION)), combinedLight, combinedOverlay, 1f, 1f, 1f, 1f);
            if (true && scanner.getCounterPercentage() > 0) {
                if (amountOfBeams > 0) {
                    if (beamTicks <= TICKS_PER_BEAM) {
                        GlStateManager._enableBlend();
                        SCANNER_MODEL.renderBeam(matrixStack, buffer.getBuffer(SCANNER_MODEL.renderType(LASER_LOCATION)), combinedLight, combinedOverlay, 1f, 1f, 1f, 0.7f);
                        GlStateManager._disableBlend();
                        beamTicks++;
                    } else {
                        amountOfBeams--;
                        beamTicks = 0;
                    }
                } else {
                    if (motion == Vector3d.ZERO) {
                        Random rand = new Random();
                        double newX = 10, newZ = 10;
                        while (Math.abs(newX + scannerX) > MAX_MOVE || Math.abs(newZ + scannerZ) > MAX_MOVE) {
                            newX = rand.nextDouble() * 2 * MAX_MOVE - MAX_MOVE - scannerX;
                            newZ = rand.nextDouble() * 2 * MAX_MOVE - MAX_MOVE - scannerZ;
                        }
                        motion = motion.add(newX, 0, newZ).scale(0.02);
                        amountOfMoves = 50;
                    } else if (amountOfMoves > 0) {
                        scannerX += motion.x;
                        scannerZ += motion.z;
                        amountOfMoves--;
                    } else {
                        motion = Vector3d.ZERO;
                        amountOfBeams = new Random().nextInt(4) + 1;
                    }
                }
            } else {
                if (Math.abs(scannerX) - 0.01 != 0 && Math.abs(scannerZ) + 0.01 != 0){
                    motion = new Vector3d(-scannerX, 0, -scannerZ).scale(0.02);
                    scannerX += motion.x;
                    scannerZ += motion.z;
                }else {
                    scannerX = 0;
                    scannerZ = 0;
                    beamTicks = 0;
                    amountOfBeams = new Random().nextInt(4) + 1;
                    motion = Vector3d.ZERO;
                    amountOfMoves = 0;
                }
            }
            matrixStack.translate(-0.3 - scannerX, 0, 0);
            SCANNER_MODEL.renderToBuffer(matrixStack, buffer.getBuffer(SCANNER_MODEL.renderType(LASER_LOCATION)), combinedLight, combinedOverlay, 1f, 1f, 1f, 1f);
            matrixStack.popPose();


        }

    }

    private float getCorrectRotation(TileEntityScanner te) {
        float rotation = 0;
        Direction facing = te.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        switch (facing) {
            case EAST:
                rotation = 270;
                break;
            case SOUTH:
                rotation = 180;
                break;
            case WEST:
                rotation = 90;
                break;
            default:
                break;
        }
        return rotation;
    }
}
