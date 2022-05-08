package com.rena.cybercraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class CyberZombieRenderer extends ZombieRenderer {

    private static final ResourceLocation ZOMBIE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie.png");
    private static final ResourceLocation HIGHLIGHT = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_highlight.png");
    private static final ResourceLocation ZOMBIE_BRUTE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_brute.png");
    private static final ResourceLocation HIGHLIGHT_BRUTE = new ResourceLocation(Cybercraft.MOD_ID + ":textures/entity/cyberzombie_brute_highlight.png");

    public CyberZombieRenderer(EntityRendererManager p_i46127_1_) {
        super(p_i46127_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieEntity entity) {
        CyberZombieEntity cz = (CyberZombieEntity) entity;
        if (cz.isBrute())
        {
            return ZOMBIE_BRUTE;
        }
        return ZOMBIE;
    }

    @Override
    protected void scale(ZombieEntity zombie, MatrixStack matrixStack, float partialTickTime) {
        CyberZombieEntity cz = (CyberZombieEntity) zombie;
        if (cz.getEyeHeight() == (1.95F * 1.2F))
        {
            matrixStack.scale(1.2F, 1.2F, 1.2F);
        }
    }
}
