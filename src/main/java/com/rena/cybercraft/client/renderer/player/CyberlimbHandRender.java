package com.rena.cybercraft.client.renderer.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.common.events.EssentialsMissingHandler;
import com.rena.cybercraft.common.events.EssentialsMissingHandlerClient;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.storage.MapData;

import javax.annotation.Nullable;

public class CyberlimbHandRender {

    private Minecraft mc = Minecraft.getInstance();
    private EntityRendererManager renderManager = mc.getEntityRenderDispatcher();
    private ItemRenderer itemRenderer = mc.getItemRenderer();
    public ItemStack itemStackMainHand;
    public ItemStack itemStackOffHand;
    MatrixStack matrixStack = new MatrixStack();
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");

    public static CyberlimbHandRender INSTANCE = new CyberlimbHandRender();

    public void renderItemInFirstPerson(AbstractClientPlayerEntity clientPlayer, float p_187457_2_, float p_187457_3_, Hand hand, float p_187457_5_, @Nullable ItemStack itemStack, float p_187457_7_) {
        boolean flag = hand == Hand.MAIN_HAND;
        HandSide handSide = flag ? clientPlayer.getMainArm() : clientPlayer.getMainArm().getOpposite();
        matrixStack.pushPose();
        if (itemStack.isEmpty()) {
            if (flag && !clientPlayer.isInvisible()) {
                this.renderArmFirstPerson(p_187457_7_, p_187457_5_, handSide);
            }
        } else if (itemStack.getItem() instanceof MapItem) {
            if (flag && itemStackOffHand.isEmpty()) {
                this.renderMapFirstPerson(p_187457_3_, p_187457_7_, p_187457_5_);
            } else {
                this.renderMapFirstPersonSide(p_187457_7_, handSide, p_187457_5_, itemStack);
            }
        } else {
            boolean flag1 = handSide == HandSide.RIGHT;
            if (clientPlayer.isUsingItem() && clientPlayer.getUseItemRemainingTicks() > 0 && clientPlayer.getUsedItemHand() == hand) {
                int j = flag1 ? 1 : -1;
                switch (itemStack.getUseAnimation()) {
                    case NONE:
                        this.transformSideFirstPerson(handSide, p_187457_7_);
                        break;
                    case EAT:
                    case DRINK:
                        this.transformEatFirstPerson(p_187457_2_, handSide, itemStack);
                        this.transformSideFirstPerson(handSide, p_187457_7_);
                        break;
                    case BLOCK:
                        this.transformSideFirstPerson(handSide, p_187457_7_);
                        break;
                    case BOW:
                        this.transformSideFirstPerson(handSide, p_187457_7_);
                        matrixStack.translate((float) j * -0.2785682F, 0.18344387F, 0.15731531F);
                        matrixStack.translate((float) j * -0.2785682F, 0.18344387F, 0.15731531F);
                        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-13.935F));
                        matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) j * 35.3F));
                        matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) j * -9.785F));
                        float f5 = (float) itemStack.getUseDuration() - ((float) this.mc.player.getUseItemRemainingTicks() - p_187457_2_ + 1.0F);
                        float f6 = f5 / 20.0F;
                        f6 = (f6 * f6 + f6 * 2.0F) / 3.0F;

                        if (f6 > 1.0F) {
                            f6 = 1.0F;
                        }

                        if (f6 > 0.1F) {
                            float f7 = MathHelper.sin((f5 - 0.1F) * 1.3F);
                            float f3 = f6 - 0.1F;
                            float f4 = f7 * f3;
                            matrixStack.translate(f4 * 0.0F, f4 * 0.004F, f4 * 0.0F);
                        }

                        matrixStack.translate(f6 * 0.0F, f6 * 0.0F, f6 * 0.04F);
                        matrixStack.scale(1.0F, 1.0F, 1.0F + f6 * 0.2F);
                        matrixStack.mulPose(Vector3f.YN.rotationDegrees((float) j * 45.0F));
                        break;
                }
            } else {
                float f = -0.4F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * (float) Math.PI);
                float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt(p_187457_5_) * ((float) Math.PI * 2F));
                float f2 = -0.2F * MathHelper.sin(p_187457_5_ * (float) Math.PI);
                int i = flag1 ? 1 : -1;
                matrixStack.translate((float) i * f, f1, f2);
                this.transformSideFirstPerson(handSide, p_187457_7_);
                this.transformFirstPerson(handSide, p_187457_5_);
            }
            this.renderItemSide(clientPlayer, itemStack, flag1 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
        }
        matrixStack.popPose();
    }

    private void renderArmFirstPerson(float p_187456_1_, float p_187456_2_, HandSide handSide) {
        boolean flag = handSide != HandSide.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = MathHelper.sqrt(p_187456_2_);
        float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
        float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
        float f4 = -0.4F * MathHelper.sin(p_187456_2_ * (float) Math.PI);
        matrixStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_187456_1_ * -0.6F, f4 + -0.71999997F);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
        float f5 = MathHelper.sin(p_187456_2_ * p_187456_2_ * (float) Math.PI);
        float f6 = MathHelper.sin(f1 * (float) Math.PI);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayerEntity clientPlayer = this.mc.player;
        this.mc.getTextureManager().bind(clientPlayer.getSkinTextureLocation());
        matrixStack.translate(f * -1.0F, 3.6F, 3.5F);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(200.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
        matrixStack.translate(f * 5.6F, 0.0F, 0.0F);
        PlayerRenderer renderplayer = getEntityRenderObject(this.mc.player, handSide);
        RenderSystem.disableCull();
        if (flag) {
            //renderplayer.renderRightHand(matrixStack, clientPlayer);
        } else {
            //renderplayer.renderLeftHand(clientPlayer);
        }
        RenderSystem.enableCull();
    }

    private void transformSideFirstPerson(HandSide handSide, float p_187459_2_) {
        int i = handSide == HandSide.RIGHT ? 1 : -1;
        matrixStack.translate((float) i * 0.56F, -0.52F + p_187459_2_ * -0.6F, -0.72F);
    }

    private void transformFirstPerson(HandSide p_187453_1_, float p_187453_2_) {
        int i = p_187453_1_ == HandSide.RIGHT ? 1 : -1;
        float f = MathHelper.sin(p_187453_2_ * p_187453_2_ * (float) Math.PI);
        matrixStack.mulPose((Vector3f.YP.rotationDegrees((float) i * (45.0F + f * -20.0F))));
        float f1 = MathHelper.sin(MathHelper.sqrt(p_187453_2_) * (float) Math.PI);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) i * f1 * -20.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(f1 * -80.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) i * -45.0F));
    }

    public void renderItemSide(LivingEntity entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean p_187462_4_) {
        if (!heldStack.isEmpty()) {
            Item item = heldStack.getItem();
            Block block = ((BlockItem) item).getBlock();
            matrixStack.pushPose();
            //boolean flag = this.itemRenderer.shouldRenderItemIn3D(heldStack) && this.isBlockTranslucent(block);
            IBakedModel model = itemRenderer.getModel(heldStack, entitylivingbaseIn.level, entitylivingbaseIn);
            boolean flag = model.isGui3d() && this.isBlockTranslucent(block);
            if (flag) {
                RenderSystem.depthMask(false);
            }
            //this.itemRenderer.renderItem(heldStack, entitylivingbaseIn, transform, p_187462_4_);
            if (flag) {
                RenderSystem.depthMask(true);
            }
            matrixStack.popPose();
        }
    }

    private boolean isBlockTranslucent(@Nullable Block blockIn) {
        return blockIn != null && RenderTypeLookup.canRenderInLayer(blockIn.defaultBlockState(), RenderType.translucent());
    }

    private void renderMapFirstPersonSide(float p_187465_1_, HandSide p_187465_2_, float p_187465_3_, ItemStack p_187465_4_) {
        float f = p_187465_2_ == HandSide.RIGHT ? 1.0F : -1.0F;
        matrixStack.translate(f * 0.125F, -0.125F, 0.0F);

        if (!this.mc.player.isInvisible()) {
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 10.0F));
            this.renderArmFirstPerson(p_187465_1_, p_187465_3_, p_187465_2_);
            matrixStack.popPose();
        }

        matrixStack.pushPose();
        matrixStack.translate(f * 0.51F, -0.08F + p_187465_1_ * -1.2F, -0.75F);
        float f1 = MathHelper.sqrt(p_187465_3_);
        float f2 = MathHelper.sin(f1 * (float) Math.PI);
        float f3 = -0.5F * f2;
        float f4 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
        float f5 = -0.3F * MathHelper.sin(p_187465_3_ * (float) Math.PI);
        matrixStack.translate(f * f3, f4 - 0.3F * f2, f5);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(f2 * -45.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f2 * -45.0F));
        this.renderMapFirstPerson(p_187465_4_);
        matrixStack.popPose();
    }

    private void transformEatFirstPerson(float p_187454_1_, HandSide p_187454_2_, ItemStack p_187454_3_) {
        float f = (float) this.mc.player.getUseItemRemainingTicks() - p_187454_1_ + 1.0F;
        float f1 = f / (float) p_187454_3_.getUseDuration();

        if (f1 < 0.8F) {
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);
            matrixStack.translate(0.0F, f2, 0.0F);
        }

        float f3 = 1.0F - (float) Math.pow(f1, 27.0D);
        int i = p_187454_2_ == HandSide.RIGHT ? 1 : -1;
        matrixStack.translate(f3 * 0.6F * (float) i, f3 * -0.5F, f3 * 0.0F);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees((float) i * f3 * 90.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(f3 * 10.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) i * f3 * 30.0F));
    }

    private void renderMapFirstPerson(float p_187463_1_, float p_187463_2_, float p_187463_3_) {
        float f = MathHelper.sqrt(p_187463_3_);
        float f1 = -0.2F * MathHelper.sin(p_187463_3_ * (float) Math.PI);
        float f2 = -0.4F * MathHelper.sin(f * (float) Math.PI);
        matrixStack.translate(0.0F, -f1 / 2.0F, f2);
        float f3 = this.getMapAngleFromPitch(p_187463_1_);
        matrixStack.translate(0.0F, 0.04F + p_187463_2_ * -1.2F + f3 * -0.5F, -0.72F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(f3 * -85.0F));
        this.renderArms();
        float f4 = MathHelper.sin(f * (float) Math.PI);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(f4 * 20.0F));
        matrixStack.scale(2.0F, 2.0F, 2.0F);
        this.renderMapFirstPerson(this.itemStackMainHand);
    }

    private void renderMapFirstPerson(ItemStack stack) {
        if (!stack.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            matrixStack.scale(0.38F, 0.38F, 0.38F);
            RenderSystem.disableLighting();
            this.mc.getTextureManager().bind(RES_MAP_BACKGROUND);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuilder();
            matrixStack.translate(-0.5F, -0.5F, 0.0F);
            matrixStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.vertex(-7.0D, 135.0D, 0.0D).uv(0.0F, 1.0F).endVertex();
            vertexbuffer.vertex(135.0D, 135.0D, 0.0D).uv(1.0F, 1.0F).endVertex();
            vertexbuffer.vertex(135.0D, -7.0D, 0.0D).uv(1.0F, 0.0F).endVertex();
            vertexbuffer.vertex(-7.0D, -7.0D, 0.0D).uv(0.0F, 0.0F).endVertex();
            tessellator.end();

            MapData mapdata = FilledMapItem.getOrCreateSavedData(stack, this.mc.level);

            if (mapdata != null) {
                //this.mc.gameRenderer.getMapRenderer().render(matrixStack, buffer, mapdata, false, combinedLight);
            }

            RenderSystem.enableLighting();
            matrixStack.popPose();
        }
    }

    private void renderArms() {
        if (!this.mc.player.isInvisible()) {
            RenderSystem.disableCull();
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
            this.renderArm(HandSide.RIGHT);
            this.renderArm(HandSide.LEFT);
            matrixStack.popPose();
            RenderSystem.enableCull();
        }
    }

    private void renderArm(HandSide p_187455_1_) {
        this.mc.getTextureManager().bind(this.mc.player.getSkinTextureLocation());
        EntityRenderer<AbstractClientPlayerEntity> render = getEntityRenderObject(this.mc.player, p_187455_1_);
        PlayerRenderer renderplayer = (PlayerRenderer) render;
        matrixStack.pushPose();
        float f = p_187455_1_ == HandSide.RIGHT ? 1.0F : -1.0F;
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(92.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * -41.0F));
        matrixStack.translate(f * 0.3F, -1.1F, 0.45F);
        if (p_187455_1_ == HandSide.RIGHT) {
            //renderplayer.renderRightHand(this.mc.player);
        } else {
            //renderplayer.renderLeftHand(this.mc.player);
        }

        matrixStack.popPose();
    }

    public boolean leftRobot = false;
    public boolean rightRobot = false;

    private PlayerRenderer getEntityRenderObject(AbstractClientPlayerEntity p, HandSide side) {
        if (side == HandSide.RIGHT) {
            if (rightRobot) {
                return EssentialsMissingHandlerClient.renderLargeArms;
            }
        } else {
            if (leftRobot) {
                return EssentialsMissingHandlerClient.renderLargeArms;
            }
        }
        return (PlayerRenderer) this.renderManager.getRenderer(p);
    }

    private float getMapAngleFromPitch(float pitch) {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = -MathHelper.cos(f * (float) Math.PI) * 0.5F + 0.5F;
        return f;
    }
}
