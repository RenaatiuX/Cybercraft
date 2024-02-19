package com.rena.cybercraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.renderer.entity.layer.ZombieHighlightLayer;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class CyberZombieRenderer extends MobRenderer<CyberZombieEntity, ZombieModel<CyberZombieEntity>> {

    private static final ResourceLocation ZOMBIE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie.png");
    private static final ResourceLocation ZOMBIE_BRUTE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_brute.png");

    public CyberZombieRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new ZombieModel<>(0.0F, false), 0.5F);
        this.addLayer(new ZombieHighlightLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(CyberZombieEntity entity) {
        if (entity.isBrute()) {
            return ZOMBIE_BRUTE;
        }
        return ZOMBIE;
    }

}
