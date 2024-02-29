package com.rena.cybercraft.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUpdateEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LungsUpgradeItem extends CybercraftItem{

    public LungsUpgradeItem(Properties properties, EnumSlot slots, Quality q) {
        super(properties, slots, q);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onDrawScreenPost(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR)
        {
            PlayerEntity entityPlayer = Minecraft.getInstance().player;
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityPlayer);
            if (cyberwareUserData == null) return;

            ItemStack itemStackCompressedOxygen = cyberwareUserData.getCybercraft(ItemInit.LUNGS_OXYGEN.get());
            if ( !itemStackCompressedOxygen.isEmpty()
                    && !entityPlayer.isCreative() )
            {
                MatrixStack matrixStack = event.getMatrixStack();
                matrixStack.pushPose();
                int air = getAir(itemStackCompressedOxygen);

                Minecraft.getInstance().getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);

                RenderSystem.enableBlend();
                int left = Minecraft.getInstance().getWindow().getWidth() / 2 + 91;
                int top = Minecraft.getInstance().getWindow().getHeight() - 49 - 8;

                float r = 1.0F;
                float b = 1.0F;
                float g = 1.0F;

                if (entityPlayer.isEyeInFluid(FluidTags.WATER))
                {
                    while (air > 0)
                    {
                        r += 1.0F;
                        b -= 0.25F;
                        g += 0.25F;
                        RenderSystem.color3f(r, g, b);
                        int drawAir = Math.min(300, air);
                        int full = MathHelper.ceil((drawAir - 2) * 10.0D / 300.0D);
                        int partial = MathHelper.ceil(drawAir * 10.0D / 300.0D) - full;

                        for (int i = 0; i < full + partial; i++)
                        {
                            ClientUtils.drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
                        }

                        air -= 300;
                        top -= 8;
                    }
                }

                RenderSystem.color3f(1.0F, 1.0F, 1.0F);
                //GlStateManager.disableBlend();
                matrixStack.popPose();
            }
        }
    }

    private Map<UUID, Boolean> mapIsOxygenPowered = new HashMap<>();

    @SubscribeEvent
    public void handleLivingUpdate(CybercraftUpdateEvent event)
    {
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = event.getCybercrafteUserData();

        ItemStack itemStackCompressedAir = cyberwareUserData.getCybercraft(ItemInit.LUNGS_OXYGEN.get());
        if (!itemStackCompressedAir.isEmpty())
        {
            int air = getAir(itemStackCompressedAir);
            if (entityLivingBase.getAirSupply() < 300 && air > 0)
            {
                int toAdd = Math.min(300 - entityLivingBase.getAirSupply(), air);
                entityLivingBase.setAirSupply(entityLivingBase.getAirSupply() + toAdd);
                CybercraftAPI.getCybercraftNBT(itemStackCompressedAir).putInt("air", air - toAdd);
            }
            else if (entityLivingBase.getAirSupply() == 300 && air < 900)
            {
                CybercraftAPI.getCybercraftNBT(itemStackCompressedAir).putInt("air", air + 1);
            }
        }

        ItemStack itemStackHyperoxygenationBoost = cyberwareUserData.getCybercraft(ItemInit.LUNGS_HYPEROXYGENATION.get());
        if (!itemStackHyperoxygenationBoost.isEmpty())
        {
            if ((entityLivingBase.isSprinting() || entityLivingBase instanceof MobEntity) && !entityLivingBase.isInWater() && entityLivingBase.isOnGround())
            {
                boolean wasPowered = getIsOxygenPowered(entityLivingBase);
                int ranks = itemStackHyperoxygenationBoost.getCount();
                boolean isPowered = entityLivingBase.tickCount % 20 == 0
                        ? cyberwareUserData.usePower(itemStackHyperoxygenationBoost, getPowerConsumption(itemStackHyperoxygenationBoost))
                        : wasPowered;
                if (isPowered)
                {
                    entityLivingBase.moveRelative(0F, new Vector3d(0.0F, .2F * ranks, 0.075F));
                }

                mapIsOxygenPowered.put(entityLivingBase.getUUID(), isPowered);
            }
        }
    }

    private boolean getIsOxygenPowered(LivingEntity entityLivingBase)
    {
        if (!mapIsOxygenPowered.containsKey(entityLivingBase.getUUID()))
        {
            mapIsOxygenPowered.put(entityLivingBase.getUUID(), Boolean.TRUE);
        }
        return mapIsOxygenPowered.get(entityLivingBase.getUUID());
    }

    @Override
    public int maxInstalledStackSize(ItemStack stack)
    {
        return stack.getItem() == ItemInit.LUNGS_HYPEROXYGENATION.get() ? 3 : 1;
    }

    private int getAir(ItemStack stack)
    {
        CompoundNBT tagCompound = CybercraftAPI.getCybercraftNBT(stack);
        if (!tagCompound.contains("air"))
        {
            tagCompound.putInt("air", 900);
        }
        return tagCompound.getInt("air");
    }

    @Override
    public int getPowerConsumption(ItemStack stack)
    {
        return stack.getItem() == ItemInit.LUNGS_HYPEROXYGENATION.get() ? LibConstants.HYPEROXYGENATION_CONSUMPTION * stack.getCount() : 0;
    }

    @Override
    protected int getUnmodifiedEssenceCost(ItemStack stack)
    {
        if (stack.getItem() == ItemInit.LUNGS_HYPEROXYGENATION.get())
        {
            switch (stack.getCount())
            {
                case 1:
                    return 2;
                case 2:
                    return 4;
                case 3:
                    return 5;
            }
        }
        return super.getUnmodifiedEssenceCost(stack);
    }
}
