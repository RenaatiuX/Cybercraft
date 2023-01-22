package com.rena.cybercraft.client.renderer.entity.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.renderer.entity.CyberZombieRenderer;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.util.ResourceLocation;

public class ZombieHighlightLayer<T extends CyberZombieEntity> extends AbstractEyesLayer<T, ZombieModel<T>> {

    private static final ResourceLocation HIGHLIGHT = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_highlight.png");
    private static final ResourceLocation HIGHLIGHT_BRUTE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_brute_highlight.png");
    CyberZombieEntity cyberZombie;
    public ZombieHighlightLayer(IEntityRenderer<T, ZombieModel<T>> renderer) {
        super(renderer);
    }

    @Override
    public RenderType renderType() {
        return cyberZombie.isBrute() ? RenderType.eyes(HIGHLIGHT_BRUTE) : RenderType.eyes(HIGHLIGHT);
    }
}
