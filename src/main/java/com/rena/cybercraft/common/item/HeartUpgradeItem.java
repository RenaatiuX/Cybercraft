package com.rena.cybercraft.common.item;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.core.network.CCNetwork;
import com.rena.cybercraft.core.network.ParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeartUpgradeItem extends CybercraftItem{

    public static final int META_INTERNAL_DEFIBRILLATOR  = 0;
    public static final int META_PLATELET_DISPATCHER     = 1;
    public static final int META_STEM_CELL_SYNTHESIZER   = 2;
    public static final int META_CARDIOVASCULAR_COUPLER  = 3;

    private static Map<UUID, Integer> timesPlatelets = new HashMap<>();

    public HeartUpgradeItem(Properties properties, EnumSlot[] slots, String... subnames) {
        super(properties, slots, subnames);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isIncompatible(ItemStack stack, ItemStack comparison) {
        return comparison.getItem() == ItemInit.CYBER_HEART.get() && ( CybercraftAPI.getMetaData(stack) == META_INTERNAL_DEFIBRILLATOR || CybercraftAPI.getMetaData(stack) == META_CARDIOVASCULAR_COUPLER );
    }

    @SubscribeEvent
    public void handleDeath(LivingDeathEvent event)
    {
        if (event.isCanceled()) return;
        LivingEntity entityLivingBase = event.getEntityLiving();
        ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(entityLivingBase);
        if (cyberwareUserData == null) return;

        ItemStack itemStackInternalDefibrillator = cyberwareUserData.getCybercraft(getCachedStack(META_INTERNAL_DEFIBRILLATOR));
        if (!itemStackInternalDefibrillator.isEmpty())
        {
            if ( (!CybercraftAPI.getCybercraftNBT(itemStackInternalDefibrillator).contains("used"))
                    && cyberwareUserData.usePower(itemStackInternalDefibrillator, getPowerConsumption(itemStackInternalDefibrillator), false) )
            {
                if (entityLivingBase instanceof PlayerEntity)
                {
                    NonNullList<ItemStack> items = cyberwareUserData.getInstalledCybercraft(EnumSlot.HEART);
                    NonNullList<ItemStack> itemsNew = NonNullList.create();
                    itemsNew.addAll(items);
                    for (int index = 0; index < items.size(); index++)
                    {
                        ItemStack item = items.get(index);
                        if ( !item.isEmpty()
                                && item.getItem() == this
                                && CybercraftAPI.getMetaData(item) == META_INTERNAL_DEFIBRILLATOR)
                        {
                            itemsNew.set(index, ItemStack.EMPTY);
                            break;
                        }
                    }
                    cyberwareUserData.setInstalledCybercraft(entityLivingBase, EnumSlot.HEART, itemsNew);
                    cyberwareUserData.updateCapacity();
                    if (!entityLivingBase.level.isClientSide)
                    {
                        CybercraftAPI.updateData(entityLivingBase);
                    }
                }
                else
                {
                    itemStackInternalDefibrillator = cyberwareUserData.getCybercraft(itemStackInternalDefibrillator);
                    CompoundNBT tagCompoundCyberware = CybercraftAPI.getCybercraftNBT(itemStackInternalDefibrillator);
                    tagCompoundCyberware.putBoolean("used", true);

                    CybercraftAPI.updateData(entityLivingBase);
                }
                entityLivingBase.setHealth(entityLivingBase.getMaxHealth() / 3F);
                /*
                CCNetwork.PACKET_HANDLER.send(new ParticlePacket(1, (float) entityLivingBase.getX(), (float) entityLivingBase.getY() + entityLivingBase.getEyeHeight() / 2F, (float) entityLivingBase.getZ()),
                        new PacketDistributor.TargetPoint(entityLivingBase.getX(), entityLivingBase.getY(), entityLivingBase.getZ(), 20, entityLivingBase.level.dimension()));
                event.setCanceled(true);
                */
            }
        }
    }

}
