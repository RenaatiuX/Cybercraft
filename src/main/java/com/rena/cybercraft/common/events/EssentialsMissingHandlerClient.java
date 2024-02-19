package com.rena.cybercraft.common.events;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class EssentialsMissingHandlerClient {

    public static final EssentialsMissingHandlerClient INSTANCE = new EssentialsMissingHandlerClient();

    /*@OnlyIn(Dist.CLIENT)
    private static final PlayerCybercraftRender renderSmallArms = new PlayerCybercraftRender(Minecraft.getInstance().getRenderManager(), true);

    @OnlyIn(Dist.CLIENT)
    public static final PlayerCybercraftRender renderLargeArms = new PlayerCybercraftRender(Minecraft.getInstance().getRenderManager(), false);

    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void handleMissingSkin(RenderPlayerEvent.Pre event)
    {
        if (!CybercraftConfig.ENABLE_CUSTOM_PLAYER_MODEL) return;

        PlayerEntity entityPlayer = event.getPlayer();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData == null) return;

        boolean hasLeftLeg = cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.LEFT);
        boolean hasRightLeg = cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.RIGHT);
        boolean hasLeftArm = cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.LEFT);
        boolean hasRightArm = cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.RIGHT);

        boolean robotLeftArm  = cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_LEFT_CYBER_ARM ));
        boolean robotRightArm = cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_RIGHT_CYBER_ARM));
        boolean robotLeftLeg  = cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_LEFT_CYBER_LEG ));
        boolean robotRightLeg = cyberwareUserData.isCybercraftInstalled(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_RIGHT_CYBER_LEG));

        PlayerRenderer renderPlayer = event.getRenderer();

        if (!(renderPlayer instanceof PlayerCybercraftRender))
        {
            boolean useSmallArms = renderPlayer.smallArms;
            PlayerCybercraftRender renderToUse = useSmallArms ? renderSmallArms : renderLargeArms;

            boolean hasNoSkin = !cyberwareUserData.hasEssential(ICybercraft.EnumSlot.SKIN);
            if (hasNoSkin)
            {
                event.setCanceled(true);
                renderToUse.doMuscles = true;
            }

            boolean hasNoLegs = !hasRightLeg && !hasLeftLeg;
            if (hasNoLegs)
            {
                // Hide pants + shoes
                if (!pants.containsKey(entityPlayer.getId()))
                {
                    pants.put(entityPlayer.getId(), entityPlayer.inventory.armorInventory.set(EntityEquipmentSlot.LEGS.getIndex(), ItemStack.EMPTY));
                }
                if (!shoes.containsKey(entityPlayer.getId()))
                {
                    shoes.put(entityPlayer.getId(), entityPlayer.inventory.armorInventory.set(EntityEquipmentSlot.FEET.getIndex(), ItemStack.EMPTY));
                }
            }

            if ( !hasRightLeg  || !hasLeftLeg  || !hasRightArm  || !hasLeftArm
                    || robotRightLeg || robotLeftLeg || robotRightArm || robotLeftArm )
            {
                event.setCanceled(true);

                boolean leftArmRusty  = robotLeftArm  && CyberwareContent.cyberlimbs.getQuality(cyberwareUserData.getCyberware(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_LEFT_CYBER_ARM ))) == CyberwareAPI.QUALITY_SCAVENGED;
                boolean rightArmRusty = robotRightArm && CyberwareContent.cyberlimbs.getQuality(cyberwareUserData.getCyberware(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_RIGHT_CYBER_ARM))) == CyberwareAPI.QUALITY_SCAVENGED;
                boolean leftLegRusty  = robotLeftLeg  && CyberwareContent.cyberlimbs.getQuality(cyberwareUserData.getCyberware(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_LEFT_CYBER_LEG ))) == CyberwareAPI.QUALITY_SCAVENGED;
                boolean rightLegRusty = robotRightLeg && CyberwareContent.cyberlimbs.getQuality(cyberwareUserData.getCyberware(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_RIGHT_CYBER_LEG))) == CyberwareAPI.QUALITY_SCAVENGED;

                // Human/body pass
                renderToUse.doRobo = false;
                renderToUse.doRusty = false;
                renderToUse.doRender((AbstractClientPlayer) entityPlayer, event.getX(), event.getY() - (hasNoLegs ? (11F / 16F) : 0), event.getZ(), entityPlayer.rotationYaw, event.getPartialRenderTick());

                if (!cyberwareUserData.isCyberwareInstalled(CyberwareContent.skinUpgrades.getCachedStack(ItemSkinUpgrade.META_SYNTHETIC_SKIN)))
                {
                    PlayerModel mainModel = renderToUse.getMainModel();
                    mainModel.bipedBody.isHidden = true;
                    mainModel.bipedHead.isHidden = true;

                    // Manufactured 'ware pass
                    mainModel.bipedLeftArm.isHidden = !(robotLeftArm && !leftArmRusty);
                    mainModel.bipedRightArm.isHidden = !(robotRightArm && !rightArmRusty);
                    mainModel.bipedLeftLeg.isHidden = !(robotLeftLeg && !leftLegRusty);
                    mainModel.bipedRightLeg.isHidden = !(robotRightLeg && !rightLegRusty);

                    if ( !mainModel.bipedLeftArm.isHidden
                            || !mainModel.bipedRightArm.isHidden
                            || !mainModel.bipedLeftLeg.isHidden
                            || !mainModel.bipedRightLeg.isHidden )
                    {
                        renderToUse.doRobo = true;
                        renderToUse.doRusty = false;
                        renderToUse.doRender((AbstractClientPlayer) entityPlayer, event.getX(), event.getY() - (hasNoLegs ? (11F / 16F) : 0), event.getZ(), entityPlayer.rotationYaw, event.getPartialRenderTick());
                    }

                    // Rusty 'ware pass
                    mainModel.bipedLeftArm.isHidden = !leftArmRusty;
                    mainModel.bipedRightArm.isHidden = !rightArmRusty;
                    mainModel.bipedLeftLeg.isHidden = !leftLegRusty;
                    mainModel.bipedRightLeg.isHidden = !rightLegRusty;

                    if ( !mainModel.bipedLeftArm.isHidden
                            || !mainModel.bipedRightArm.isHidden
                            || !mainModel.bipedLeftLeg.isHidden
                            || !mainModel.bipedRightLeg.isHidden )
                    {
                        renderToUse.doRobo = true;
                        renderToUse.doRusty = true;
                        renderToUse.doRender((AbstractClientPlayer) entityPlayer, event.getX(), event.getY() - (hasNoLegs ? (11F / 16F) : 0), event.getZ(), entityPlayer.rotationYaw, event.getPartialRenderTick());
                    }

                    // restore defaults
                    mainModel.bipedBody.isHidden = false;
                    mainModel.bipedHead.isHidden = false;
                    mainModel.bipedLeftArm.isHidden = false;
                    mainModel.bipedRightArm.isHidden = false;
                    mainModel.bipedLeftLeg.isHidden = false;
                    mainModel.bipedRightLeg.isHidden = false;
                }
            }
            else if (hasNoSkin)
            {
                renderToUse.doRender((AbstractClientPlayer) entityPlayer, event.getX(), event.getY(), event.getZ(), entityPlayer.rotationYaw, event.getPartialRenderTick());
            }

            if (hasNoSkin)
            {
                renderToUse.doMuscles = false;
            }
        }

        if (!hasLeftLeg)
        {
            renderPlayer.getMainModel().bipedLeftLeg.isHidden = true;
        }

        if (!hasRightLeg)
        {
            renderPlayer.getMainModel().bipedRightLeg.isHidden = true;
        }

        if (!hasLeftArm)
        {
            renderPlayer.getMainModel().bipedLeftArm.isHidden = true;

            // Hide the main or offhand item if no arm there
            if (mc.gameSettings.mainHand == EnumHandSide.LEFT)
            {
                if ( !mainHand.containsKey(entityPlayer.getId())
                        && InventoryPlayer.isHotbar(entityPlayer.inventory.currentItem) )
                {
                    mainHand.put(entityPlayer.getId(), entityPlayer.inventory.mainInventory.set(entityPlayer.inventory.currentItem, ItemStack.EMPTY));
                }
            }
            else
            {
                if (!offHand.containsKey(entityPlayer.getId()))
                {
                    offHand.put(entityPlayer.getId(), entityPlayer.inventory.offHandInventory.set(0, ItemStack.EMPTY));
                }
            }
        }

        if (!hasRightArm)
        {
            renderPlayer.getMainModel().bipedRightArm.isHidden = true;

            // Hide the main or offhand item if no arm there
            if (mc.gameSettings.mainHand == EnumHandSide.RIGHT)
            {
                if ( !mainHand.containsKey(entityPlayer.getId())
                        && InventoryPlayer.isHotbar(entityPlayer.inventory.currentItem) )
                {
                    mainHand.put(entityPlayer.getId(), entityPlayer.inventory.mainInventory.set(entityPlayer.inventory.currentItem, ItemStack.EMPTY));
                }
            }
            else
            {
                if (!offHand.containsKey(entityPlayer.getId()))
                {
                    offHand.put(entityPlayer.getId(), entityPlayer.inventory.offHandInventory.set(0, ItemStack.EMPTY));
                }
            }
        }
    }*/

    private static Map<Integer, ItemStack> mainHand = new HashMap<>();
    private static Map<Integer, ItemStack> offHand = new HashMap<>();

    private static Map<Integer, ItemStack> pants = new HashMap<>();
    private static Map<Integer, ItemStack> shoes = new HashMap<>();

    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void handleMissingSkin(RenderPlayerEvent.Post event)
    {
        if (!CybercraftConfig.ENABLE_CUSTOM_PLAYER_MODEL) return;

        event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
        event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
        event.getRenderer().getMainModel().bipedLeftLeg.isHidden = false;
        event.getRenderer().getMainModel().bipedRightLeg.isHidden = false;

        PlayerEntity entityPlayer = event.getPlayer();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
        if (cyberwareUserData != null)
        {
            if (pants.containsKey(entityPlayer.getId()))
            {
                entityPlayer.inventory.armorInventory.set(EquipmentSlotType.LEGS.getIndex(), pants.remove(entityPlayer.getId()));
            }

            if (shoes.containsKey(entityPlayer.getId()))
            {
                entityPlayer.inventory.armorInventory.set(EquipmentSlotType.FEET.getIndex(), shoes.remove(entityPlayer.getId()));
            }

            if (mainHand.containsKey(entityPlayer.getId()))
            {
                entityPlayer.inventory.mainInventory.set(entityPlayer.inventory.currentItem, mainHand.remove(entityPlayer.getId()));
            }

            if (offHand.containsKey(entityPlayer.getId()))
            {
                entityPlayer.inventory.offHandInventory.set(0, offHand.remove(entityPlayer.getId()));
            }

            if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.LEFT))
            {
                event.getRenderer().getMainModel().bipedLeftArm.isHidden = false;
            }

            if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.RIGHT))
            {
                event.getRenderer().getMainModel().bipedRightArm.isHidden = false;
            }
        }
    }

    private static boolean missingArm = false;
    private static boolean missingSecondArm = false;
    private static boolean hasRoboLeft = false;
    private static boolean hasRoboRight = false;
    private static HandSide oldHand;

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void handleMissingEssentials(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        if (entityLivingBase != Minecraft.getInstance().player) return;

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null)
        {
            GameSettings settings = Minecraft.getInstance().options;
            boolean stillMissingArm = false;
            boolean stillMissingSecondArm = false;

            boolean leftUnpowered = false;
            ItemStack armLeft = cyberwareUserData.getCybercraft(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_LEFT_CYBER_ARM));
            if (!armLeft.isEmpty() && !CyberLimbItem.isPowered(armLeft))
            {
                leftUnpowered = true;
            }

            boolean rightUnpowered = false;
            ItemStack armRight = cyberwareUserData.getCybercraft(CyberwareContent.cyberlimbs.getCachedStack(ItemCyberlimb.META_RIGHT_CYBER_ARM));
            if (!armRight.isEmpty() && !CyberLimbItem.isPowered(armRight))
            {
                rightUnpowered = true;
            }

            boolean hasSkin = cyberwareUserData.isCybercraftInstalled(CyberwareContent.skinUpgrades.getCachedStack(ItemSkinUpgrade.META_SYNTHETIC_SKIN));
            hasRoboLeft = !armLeft.isEmpty() && !hasSkin;
            hasRoboRight = !armRight.isEmpty() && !hasSkin;
            boolean hasRightArm = cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.RIGHT) && !rightUnpowered;
            boolean hasLeftArm = cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.LEFT) && !leftUnpowered;

            if (!hasRightArm)
            {
                if (settings.mainHand != HandSide.LEFT)
                {
                    oldHand = settings.mainHand;
                    settings.mainHand = HandSide.LEFT;
                    settings.sendSettingsToServer();
                }

                missingArm = true;
                stillMissingArm = true;

                if (!hasLeftArm)
                {
                    missingSecondArm = true;
                    stillMissingSecondArm = true;
                }
            }
            else if (!hasLeftArm)
            {
                if (settings.mainHand != HandSide.RIGHT)
                {
                    oldHand = settings.mainHand;
                    settings.mainHand = HandSide.RIGHT;
                    settings.sendSettingsToServer();
                }

                missingArm = true;
                stillMissingArm = true;
            }

            if (!stillMissingArm)
            {
                missingArm = false;
                if (oldHand != null)
                {
                    settings.mainHand = oldHand;
                    settings.sendSettingsToServer();
                    oldHand = null;
                }
            }

            if (!stillMissingSecondArm)
            {
                missingSecondArm = false;
            }
        }
    }

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void handleRenderHand(RenderHandEvent event)
    {
        if (!CybercraftConfig.ENABLE_CUSTOM_PLAYER_MODEL || FMLClientHandler.instance().hasOptifine()) return;

        if (missingArm || missingSecondArm || hasRoboLeft || hasRoboRight)
        {
            float partialTicks = event.getPartialTicks();
            EntityRenderer entityRenderer = mc.entityRenderer;
            event.setCanceled(true);

            boolean isSleeping = mc.getRenderViewEntity() instanceof LivingEntity
                    && ((LivingEntity) mc.getRenderViewEntity()).isPlayerSleeping();

            if ( mc.options.thirdPersonView == 0
                    && !isSleeping
                    && !mc.options.hideGUI
                    && !mc.playerController.isSpectator() )
            {
                entityRenderer.enableLightmap();
                renderItemInFirstPerson(partialTicks);
                entityRenderer.disableLightmap();
            }
        }
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return first != null ? first : checkNotNull(second);
    }

    private void renderItemInFirstPerson(float partialTicks)
    {
        ItemRenderer itemRenderer = mc.getItemRenderer();
        AbstractClientPlayer abstractclientplayer = mc.player;
        float swingProgress = abstractclientplayer.getSwingProgress(partialTicks);

        EnumHand enumhand = firstNonNull(abstractclientplayer.swingingHand, EnumHand.MAIN_HAND);

        float rotationPitch = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
        float rotationYaw = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
        boolean doRenderMainHand = true;
        boolean doRenderOffHand = true;

        if (abstractclientplayer.isHandActive())
        {
            ItemStack itemstack = abstractclientplayer.getActiveItemStack();

            if (!itemstack.isEmpty() && itemstack.getItem() == Items.BOW) //Forge: Data watcher can desync and cause this to NPE...
            {
                EnumHand enumhand1 = abstractclientplayer.getActiveHand();
                doRenderMainHand = enumhand1 == EnumHand.MAIN_HAND;
                doRenderOffHand = !doRenderMainHand;
            }
        }

        rotateArroundXAndY(rotationPitch, rotationYaw);
        setLightmap();
        rotateArm(partialTicks);
        GlStateManager.enableRescaleNormal();

        RenderCyberlimbHand.INSTANCE.itemStackMainHand = itemRenderer.itemStackMainHand;
        RenderCyberlimbHand.INSTANCE.itemStackOffHand = itemRenderer.itemStackOffHand;

        if (doRenderMainHand && !missingSecondArm)
        {
            float f3 = enumhand == EnumHand.MAIN_HAND ? swingProgress : 0.0F;
            float f5 = 1.0F - (itemRenderer.prevEquippedProgressMainHand + (itemRenderer.equippedProgressMainHand - itemRenderer.prevEquippedProgressMainHand) * partialTicks);
            RenderCyberlimbHand.INSTANCE.leftRobot = hasRoboLeft;
            RenderCyberlimbHand.INSTANCE.rightRobot = hasRoboRight;
            RenderCyberlimbHand.INSTANCE.renderItemInFirstPerson(abstractclientplayer, partialTicks, rotationPitch, EnumHand.MAIN_HAND, f3, itemRenderer.itemStackMainHand, f5);
        }

        if (doRenderOffHand && !missingArm)
        {
            float f4 = enumhand == EnumHand.OFF_HAND ? swingProgress : 0.0F;
            float f6 = 1.0F - (itemRenderer.prevEquippedProgressOffHand + (itemRenderer.equippedProgressOffHand - itemRenderer.prevEquippedProgressOffHand) * partialTicks);
            RenderCyberlimbHand.INSTANCE.leftRobot = hasRoboLeft;
            RenderCyberlimbHand.INSTANCE.rightRobot = hasRoboRight;
            RenderCyberlimbHand.INSTANCE.renderItemInFirstPerson(abstractclientplayer, partialTicks, rotationPitch, EnumHand.OFF_HAND, f4, itemRenderer.itemStackOffHand, f6);
        }

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

    private void rotateArroundXAndY(float angle, float angleY)
    {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void setLightmap()
    {
        EntityPlayer entityPlayer = mc.player;
        int i = mc.world.getCombinedLight(new BlockPos(entityPlayer.posX, entityPlayer.posY + (double)entityPlayer.getEyeHeight(), entityPlayer.posZ), 0);
        float f = (float)(i & 65535);
        float f1 = (float)(i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    private void rotateArm(float p_187458_1_)
    {
        EntityPlayerSP entityPlayerSP = mc.player;
        float f = entityPlayerSP.prevRenderArmPitch + (entityPlayerSP.renderArmPitch - entityPlayerSP.prevRenderArmPitch) * p_187458_1_;
        float f1 = entityPlayerSP.prevRenderArmYaw + (entityPlayerSP.renderArmYaw - entityPlayerSP.prevRenderArmYaw) * p_187458_1_;
        GlStateManager.rotate((entityPlayerSP.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((entityPlayerSP.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
    }

    @SubscribeEvent
    public void handleWorldUnload(WorldEvent.Unload event)
    {
        if (missingArm)
        {
            GameSettings settings = Minecraft.getMinecraft().gameSettings;
            missingArm = false;
            settings.mainHand = oldHand;
        }
    }*/
}
