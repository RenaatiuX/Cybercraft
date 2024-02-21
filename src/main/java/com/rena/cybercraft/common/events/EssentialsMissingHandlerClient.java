package com.rena.cybercraft.common.events;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.client.renderer.player.PlayerCybercraftRender;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.item.CyberLimbItem;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class EssentialsMissingHandlerClient {

    public static final EssentialsMissingHandlerClient INSTANCE = new EssentialsMissingHandlerClient();
    @OnlyIn(Dist.CLIENT)
    private static final PlayerCybercraftRender renderSmallArms = new PlayerCybercraftRender(Minecraft.getInstance().getEntityRenderDispatcher(), true);

    @OnlyIn(Dist.CLIENT)
    public static final PlayerCybercraftRender renderLargeArms = new PlayerCybercraftRender(Minecraft.getInstance().getEntityRenderDispatcher(), false);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void handleMissingSkin(RenderPlayerEvent.Pre event) {
        if (!CybercraftConfig.C_OTHER.render.get()) return;
        PlayerEntity playerEntity = event.getPlayer();
        ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(playerEntity);
        if (cybercraftUserData == null) return;

        boolean hasLeftLeg = cybercraftUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.LEFT);
        boolean hasRightLeg = cybercraftUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.RIGHT);
        boolean hasLeftArm = cybercraftUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.LEFT);
        boolean hasRightArm = cybercraftUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.RIGHT);

    }



    private static Map<Integer, ItemStack> mainHand = new HashMap<>();
    private static Map<Integer, ItemStack> offHand = new HashMap<>();

    private static Map<Integer, ItemStack> pants = new HashMap<>();
    private static Map<Integer, ItemStack> shoes = new HashMap<>();


}
