package com.rena.cybercraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.model.block.SurgeryChamberModel;
import com.rena.cybercraft.common.block.SurgeryChamberBlock;
import com.rena.cybercraft.common.tileentities.TileEntitySurgeryChamber;
import com.rena.cybercraft.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class TileEntitySurgeryChamberRender extends TileEntityRenderer<TileEntitySurgeryChamber> {
    private static final SurgeryChamberModel MODEL = new SurgeryChamberModel();
    public static final ResourceLocation TEXTURE = Cybercraft.modLoc("textures/models/surgery_chamber_door.png");
    public TileEntitySurgeryChamberRender(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileEntitySurgeryChamber te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer p_225616_4_, int p_225616_5_, int p_225616_6_) {
        if (te != null)
        {
            float ticks = Minecraft.getInstance().player.tickCount + partialTicks;

            /*matrixStack.pushPose();
            //RenderSystem.color(1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.translate(x+.5, y+.5, z+.5);

            BlockState state = te.getLevel().getBlockState(te.getBlockPos());
            if (state.getBlock() == BlockInit.SURGERY_CHAMBER_BLOCK.get())
            {

                Direction facing = state.getValue(SurgeryChamberBlock.FACING);

                switch (facing)
                {
                    case EAST:
                        GlStateManager.rotate(90F, 0F, 1F, 0F);
                        break;
                    case NORTH:
                        GlStateManager.rotate(180F, 0F, 1F, 0F);
                        break;
                    case SOUTH:
                        break;
                    case WEST:
                        GlStateManager.rotate(270F, 0F, 1F, 0F);
                        break;
                    default:
                        break;
                }
                ClientUtils.bindTexture(TEXTURE);

                boolean isOpen = state.getValue(SurgeryChamberBlock.OPEN);
                if (isOpen != te.lastOpen)
                {
                    te.lastOpen = isOpen;
                    te.openTicks = ticks;
                }

                float ticksPassed = Math.min(10, ticks - te.openTicks);
                double v = Math.sin(ticksPassed * ((Math.PI / 2) / 10F)) * 90F;
                float rotate = (float) v;

                if (!isOpen)
                {
                    rotate = 90F - (float) v;
                }

                matrixStack.pushPose();
                matrixStack.translate(-6F / 16F, 0F, -6F / 16F);
                GlStateManager.rotate(-rotate, 0F, 1F, 0F);
                MODEL.renderToBuffer(null, 0, 0, 0, 0, 0, .0625f);
                matrixStack.popPose();

                matrixStack.pushPose();
                matrixStack.translate(6F / 16F, 0F, -6F / 16F);
                GlStateManager.rotate(rotate, 0F, 1F, 0F);
                MODEL.renderRight(null, 0, 0, 0, 0, 0, .0625f);
                matrixStack.popPose();

                matrixStack.popPose();
            }*/
        }
    }
}
