package com.rena.cybercraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.common.block.EngineeringTableBlock;
import com.rena.cybercraft.common.block.RotatableBlock;
import com.rena.cybercraft.common.tileentities.TileEntityEngineeringTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class TileEntityEngineeringRender extends TileEntityRenderer<TileEntityEngineeringTable> {
    public TileEntityEngineeringRender(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileEntityEngineeringTable te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (te != null) {
            Minecraft mc = Minecraft.getInstance();
            float rotation = 0;
            Direction facing = te.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
            switch (facing) {
                case EAST:
                    rotation = 90;
                    break;
                case NORTH:
                    rotation = 180;
                    break;
                case SOUTH:
                    break;
                case WEST:
                    rotation = 270;
                    break;
                default:
                    break;
            }
            renderItem(te.getItem(0), new double[]{0d, 0.9d, 0d},Vector3f.YP.rotation(rotation), matrixStack, buffer, combinedOverlay, getLightLevel(mc.player.level, te.getBlockPos()), 0.5f);
        }
    }

    protected int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getBrightness(LightType.BLOCK, pos);
        int sLight = world.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    protected void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
                              IRenderTypeBuffer buffer, int combinedOverlay, int lightLevel, float scale) {
        Minecraft mc = Minecraft.getInstance();
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rotation);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = mc.getItemRenderer().getModel(stack, null, null);
        mc.getItemRenderer().render(stack, ItemCameraTransforms.TransformType.NONE, true, matrixStack, buffer,
                lightLevel, combinedOverlay, model);
        matrixStack.popPose();
    }
}
