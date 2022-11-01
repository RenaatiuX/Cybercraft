package com.rena.cybercraft.common.block.events;

import com.google.common.collect.HashMultimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.item.CyberLimbItem;
import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import com.rena.cybercraft.core.init.EffectInit;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EssentialsMissingHandler {

    public static final ResourceLocation BLACK_PX = Cybercraft.modLoc("textures/gui/blackpx.png");

    public static final DamageSource brainless = new DamageSource("cybercraft.brainless").bypassArmor().bypassMagic();
    public static final DamageSource heartless = new DamageSource("cybercraft.heartless").bypassArmor().bypassMagic();
    public static final DamageSource surgery = new DamageSource("cybercraft.surgery").bypassArmor();
    public static final DamageSource spineless = new DamageSource("cybercraft.spineless").bypassArmor().bypassMagic();
    public static final DamageSource nomuscles = new DamageSource("cybercraft.nomuscles").bypassArmor().bypassMagic();
    public static final DamageSource noessence = new DamageSource("cybercraft.noessence").bypassArmor().bypassMagic();
    public static final DamageSource lowessence = new DamageSource("cybercraft.lowessence").bypassArmor().bypassMagic();


    public static final EssentialsMissingHandler INSTANCE = new EssentialsMissingHandler();

    private static Map<Integer, Integer> timesLungs = new HashMap<>();

    private static final UUID idMissingLegSpeedAttribute = UUID.fromString("fe00fdea-5044-11e6-beb8-9e71128cae77");
    private static final HashMultimap<Attribute, AttributeModifier> multimapMissingLegSpeedAttribute;

    static {
        multimapMissingLegSpeedAttribute = HashMultimap.create();
        multimapMissingLegSpeedAttribute.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(idMissingLegSpeedAttribute, "Missing leg speed", -100F, AttributeModifier.Operation.ADDITION));
    }

    private Map<Integer, Boolean> last = new HashMap<>();
    private Map<Integer, Boolean> lastClient = new HashMap<>();

    @SubscribeEvent
    public void triggerCyberwareEvent(LivingEvent.LivingUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null)
        {
            CybercraftUpdateEvent cyberwareUpdateEvent = new CybercraftUpdateEvent(entityLivingBase, cyberwareUserData);
            MinecraftForge.EVENT_BUS.post(cyberwareUpdateEvent);
        }
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public void handleMissingEssentials(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        if (entityLivingBase.tickCount % 20 == 0)
        {
            cyberwareUserData.resetBuffer();
        }

        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.CRANIUM))
        {
            entityLivingBase.hurt(brainless, Integer.MAX_VALUE);
        }

        if ( entityLivingBase instanceof PlayerEntity
                && entityLivingBase.tickCount % 20 == 0 )
        {
            int tolerance = cyberwareUserData.getTolerance(entityLivingBase);

            if (tolerance <= 0)
            {
                entityLivingBase.hurt(noessence, Integer.MAX_VALUE);
            }

            if ( tolerance < CybercraftConfig.C_ESSENCE.criticalEssence.get()
                    && entityLivingBase.tickCount % 100 == 0
                    && !entityLivingBase.hasEffect(EffectInit.NEUROPOZYNE.get()) )
            {
                entityLivingBase.addEffect(new EffectInstance(EffectInit.REJECTION.get(), 110, 0, true, false));
                entityLivingBase.hurt(lowessence, 2F);
            }
        }

        int numMissingLegs = 0;
        int numMissingLegsVisible = 0;

        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.LEFT))
        {
            numMissingLegs++;
            numMissingLegsVisible++;
        }
        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.RIGHT))
        {
            numMissingLegs++;
            numMissingLegsVisible++;
        }

        ItemStack legLeft = cyberwareUserData.getCybercraft(ItemInit.CYBER_LIMB_LEG_LEFT.get());
        if ( !legLeft.isEmpty() && !CyberLimbItem.isPowered(legLeft)) {
            numMissingLegs++;
        }

        ItemStack legRight = cyberwareUserData.getCybercraft(ItemInit.CYBER_LIMB_LEG_RIGHT.get());
        if (!legRight.isEmpty() && !CyberLimbItem.isPowered(legRight)) {
            numMissingLegs++;
        }

        if (entityLivingBase instanceof PlayerEntity) {
            if (numMissingLegsVisible == 2) {
                entityLivingBase.eyeHeight = 1.8F - (10F / 16F);
                entityLivingBase.eyeHeight = entityLivingBase.getEyeHeight() - (10F / 16F);
                AxisAlignedBB axisalignedbb = entityLivingBase.getBoundingBox();
                entityLivingBase.setBoundingBox(new AxisAlignedBB(
                        axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ,
                        axisalignedbb.minX + entityLivingBase.getBbWidth(), axisalignedbb.minY + entityLivingBase.getBbHeight(), axisalignedbb.minZ + entityLivingBase.getBbWidth()));

                if (entityLivingBase.level.isClientSide) {
                    lastClient.put(entityLivingBase.getId(), true);
                }
                else {
                    last.put(entityLivingBase.getId(), true);
                }
            }
            else if (last(entityLivingBase.level.isClientSide, entityLivingBase)) {
                entityLivingBase.dimensions = new EntitySize(entityLivingBase.dimensions.width, 1.8f, false);
                entityLivingBase.eyeHeight = entityLivingBase.getEyeHeight();
                AxisAlignedBB axisalignedbb = entityLivingBase.getBoundingBox();
                entityLivingBase.setBoundingBox(new AxisAlignedBB(
                        axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ,
                        axisalignedbb.minX + entityLivingBase.getBbWidth(), axisalignedbb.minY + entityLivingBase.getBbHeight(), axisalignedbb.minZ + entityLivingBase.getBbWidth() ));

                if (entityLivingBase.level.isClientSide) {
                    lastClient.put(entityLivingBase.getId(), false);
                }
                else {
                    last.put(entityLivingBase.getId(), false);
                }
            }
        }

        if ( numMissingLegs >= 1 && entityLivingBase.isOnGround()) {
            entityLivingBase.getAttributes().addTransientAttributeModifiers(multimapMissingLegSpeedAttribute);
        }
        else if ( numMissingLegs >= 1 || entityLivingBase.tickCount % 20 == 0 ) {
            entityLivingBase.getAttributes().removeAttributeModifiers(multimapMissingLegSpeedAttribute);
        }

        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.HEART)) {
            entityLivingBase.hurt(heartless, Integer.MAX_VALUE);
        }

        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.BONE)) {
            entityLivingBase.hurt(spineless, Integer.MAX_VALUE);
        }

        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.MUSCLE)) {
            entityLivingBase.hurt(nomuscles, Integer.MAX_VALUE);
        }

        if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LUNGS)) {
            if (getLungsTime(entityLivingBase) >= 20) {
                timesLungs.put(entityLivingBase.getId(), entityLivingBase.tickCount);
                entityLivingBase.hurt(DamageSource.DROWN, 2F);
            }
        }
        else if (entityLivingBase.tickCount % 20 == 0) {
            timesLungs.remove(entityLivingBase.getId());
        }
    }

    private boolean last(boolean remote, LivingEntity entityLivingBase) {
        if (remote) {
            if (!lastClient.containsKey(entityLivingBase.getId())) {
                lastClient.put(entityLivingBase.getId(), false);
            }
            return lastClient.get(entityLivingBase.getId());
        }
        else {
            if (!last.containsKey(entityLivingBase.getId())) {
                last.put(entityLivingBase.getId(), false);
            }
            return last.get(entityLivingBase.getId());
        }
    }

    @SubscribeEvent
    public void handleJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null) {
            int numMissingLegs = 0;

            if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.LEFT)) {
                numMissingLegs++;
            }
            if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.RIGHT)) {
                numMissingLegs++;
            }

            ItemStack legLeft = cyberwareUserData.getCybercraft(ItemInit.CYBER_LIMB_LEG_LEFT.get());
            if (!legLeft.isEmpty() && !CyberLimbItem.isPowered(legLeft)) {
                numMissingLegs++;
            }

            ItemStack legRight = cyberwareUserData.getCybercraft(ItemInit.CYBER_LIMB_LEG_RIGHT.get());
            if (!legRight.isEmpty() && !CyberLimbItem.isPowered(legRight)) {
                numMissingLegs++;
            }

            if (numMissingLegs == 2) {
                Vector3d motion = entityLivingBase.getDeltaMovement();
                entityLivingBase.setDeltaMovement(new Vector3d(motion.x, 0.2f, motion.z));
            }
        }
    }

    private int getLungsTime(@Nonnull LivingEntity entityLivingBase) {
        Integer timeLungs = timesLungs.computeIfAbsent(entityLivingBase.getId(), k -> entityLivingBase.tickCount);
        return entityLivingBase.tickCount - timeLungs;
    }

    private static Map<Integer, Integer> mapHunger = new HashMap<>();
    private static Map<Integer, Float> mapSaturation = new HashMap<>();

    @SubscribeEvent
    public void handleEatFoodTick(LivingEntityUseItemEvent.Tick event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ItemStack stack = event.getItem();

        if (entityLivingBase == null) return;

        if ( entityLivingBase instanceof PlayerEntity && !stack.isEmpty() && stack.getItem().getUseAnimation(stack) == UseAction.EAT ) {
            PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);

            if (cyberwareUserData != null && !cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LOWER_ORGANS)) {
                mapHunger.put(entityPlayer.getId(), entityPlayer.getFoodData().getFoodLevel());
                mapSaturation.put(entityPlayer.getId(), entityPlayer.getFoodData().getSaturationLevel());
                return;
            }
        }

        mapHunger.remove(entityLivingBase.getId());
        mapSaturation.remove(entityLivingBase.getId());
    }

    @SubscribeEvent
    public void handleEatFoodEnd(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ItemStack stack = event.getItem();

        if ( entityLivingBase instanceof PlayerEntity && !stack.isEmpty() && stack.getItem().getUseAnimation(stack) == UseAction.EAT ) {
            PlayerEntity entityPlayer = (PlayerEntity) entityLivingBase;
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);

            if (cyberwareUserData != null && !cyberwareUserData.hasEssential(ICybercraft.EnumSlot.LOWER_ORGANS)) {
                Integer hunger = mapHunger.get(entityPlayer.getId());
                if (hunger != null) {
                    entityPlayer.getFoodData().setFoodLevel(hunger);
                }

                Float saturation = mapSaturation.get(entityPlayer.getId());
                if (saturation != null) {
                    entityPlayer.getFoodData().setSaturation(saturation);
                }
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void overlayPre(TickEvent.ClientTickEvent event)
    {
        if ( event.phase == TickEvent.Phase.START && Minecraft.getInstance() != null && Minecraft.getInstance().player != null ) {
            PlayerEntity entityPlayer = Minecraft.getInstance().player;

            entityPlayer.getAttributes().removeAttributeModifiers(multimapMissingLegSpeedAttribute);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void overlayPre(RenderGameOverlayEvent.Pre event)
    {
        MatrixStack matrixStack = event.getMatrixStack();

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL)
        {
            PlayerEntity entityPlayer = Minecraft.getInstance().player;
            if (entityPlayer == null) return;

            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if ( cyberwareUserData != null
                    && !cyberwareUserData.hasEssential(ICybercraft.EnumSlot.EYES)
                    && !entityPlayer.isCreative() )
            {
                matrixStack.pushPose();
                RenderSystem.enableBlend();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
                Minecraft.getInstance().getTextureManager().bind(BLACK_PX);
                ClientUtils.drawTexturedModalRect(0, 0, 0, 0, Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
                matrixStack.popPose();
            }
            if (TileEntitySurgery.workingOnPlayer) {
                float trans = 1.0F;
                float ticks = TileEntitySurgery.playerProgressTicks + event.getPartialTicks();
                if (ticks < 20F)
                {
                    trans = ticks / 20F;
                }
                else if (ticks > 60F)
                {
                    trans = (80F - ticks) / 20F;
                }
                RenderSystem.enableBlend();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, trans);
                Minecraft.getInstance().getTextureManager().bind(BLACK_PX);
                MainWindow window = Minecraft.getInstance().getWindow();
                ClientUtils.drawTexturedModalRect(0, 0, 0, 0, window.getGuiScaledWidth(), window.getGuiScaledHeight());
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
            }
        }
    }

    @SubscribeEvent
    public void handleMissingSkin(LivingHurtEvent event) {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null) {
            if (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.SKIN)) {
                if (!event.getSource().isBypassArmor() || event.getSource() == DamageSource.FALL) {
                    event.setAmount(event.getAmount() * 3F);
                }
            }
        }
    }

    @SubscribeEvent
    public void handleEntityInteract(PlayerInteractEvent.EntityInteract event) {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null) {
            processEvent(event, event.getHand(), event.getPlayer(), cyberwareUserData);
        }
    }

    @SubscribeEvent
    public void handleLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null) {
            processEvent(event, event.getHand(), event.getPlayer(), cyberwareUserData);
        }
    }

    @SubscribeEvent
    public void handleRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null) {
            processEvent(event, event.getHand(), event.getPlayer(), cyberwareUserData);
        }
    }

    @SubscribeEvent
    public void handleRightClickItem(PlayerInteractEvent.RightClickItem event) {
        LivingEntity entityLivingBase = event.getEntityLiving();

        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData != null) {
            processEvent(event, event.getHand(), event.getPlayer(), cyberwareUserData);
        }
    }

    private void processEvent(Event event, Hand hand, PlayerEntity entityPlayer, ICybercraftUserData cyberwareUserData) {
        HandSide mainHand = entityPlayer.getMainArm();
        HandSide offHand = ((mainHand == HandSide.LEFT) ? HandSide.RIGHT : HandSide.LEFT);
        ICybercraft.ISidedLimb.EnumSide correspondingMainHand = ((mainHand == HandSide.RIGHT) ? ICybercraft.ISidedLimb.EnumSide.RIGHT : ICybercraft.ISidedLimb.EnumSide.LEFT);
        ICybercraft.ISidedLimb.EnumSide correspondingOffHand = ((offHand == HandSide.RIGHT) ? ICybercraft.ISidedLimb.EnumSide.RIGHT : ICybercraft.ISidedLimb.EnumSide.LEFT);

        boolean leftUnpowered = false;
        ItemStack armLeft = cyberwareUserData.getCybercraft(ItemInit.CYBER_LIMB_ARM_LEFT.get());
        if (!armLeft.isEmpty() && !CyberLimbItem.isPowered(armLeft)) {
            leftUnpowered = true;
        }

        boolean rightUnpowered = false;
        ItemStack armRight = cyberwareUserData.getCybercraft(ItemInit.CYBER_LIMB_ARM_RIGHT.get());
        if (!armRight.isEmpty() && !CyberLimbItem.isPowered(armRight)) {
            rightUnpowered = true;
        }

        if (hand == Hand.MAIN_HAND && (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, correspondingMainHand) || leftUnpowered)) {
            event.setCanceled(true);
        }
        else if (hand == Hand.OFF_HAND && (!cyberwareUserData.hasEssential(ICybercraft.EnumSlot.ARM, correspondingOffHand) || rightUnpowered)) {
            event.setCanceled(true);
        }
    }

}
