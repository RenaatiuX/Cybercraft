package com.rena.cyberware.api;

import com.rena.cyberware.api.item.ICybercraft;
import com.rena.cyberware.api.item.ICybercraft.EnumSlot;
import com.rena.cyberware.api.item.ICybercraft.ISidedLimb.EnumSide;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.List;

public interface ICybercraftUserData {

    NonNullList<ItemStack> getInstalledCyberware(ICybercraft.EnumSlot slot);
    void setInstalledCyberware(LivingEntity livingEntity, EnumSlot slot, List<ItemStack> cyberware);
    void setInstalledCyberware(LivingEntity livingEntity, EnumSlot slot, NonNullList<ItemStack> cyberware);
    boolean isCyberwareInstalled(ItemStack cyberware);
    int getCyberwareRank(ItemStack cyberware);

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT tagCompound);


    boolean hasEssential(EnumSlot slot);
    void setHasEssential(EnumSlot slot, boolean hasLeft, boolean hasRight);
    ItemStack getCyberware(ItemStack cyberware);
    void updateCapacity();
    void resetBuffer();
    void addPower(int amount, ItemStack inputter);
    boolean isAtCapacity(ItemStack stack);
    boolean isAtCapacity(ItemStack stack, int buffer);
    float getPercentFull();
    int getCapacity();
    int getStoredPower();
    int getProduction();
    int getConsumption();
    boolean usePower(ItemStack stack, int amount);
    List<ItemStack> getPowerOutages();
    List<Integer> getPowerOutageTimes();
    void setImmune();
    boolean usePower(ItemStack stack, int amount, boolean isPassive);
    boolean hasEssential(EnumSlot slot, EnumSide side);
    void resetWare(LivingEntity livingEntity);
    int getNumActiveItems();
    List<ItemStack> getActiveItems();
    void removeHotkey(int i);
    void addHotkey(int i, ItemStack stack);
    ItemStack getHotkey(int i);
    Iterable<Integer> getHotkeys();
    List<ItemStack> getHudjackItems();
    void setHudData(CompoundNBT tagCompound);
    CompoundNBT getHudData();
    boolean hasOpenedRadialMenu();
    void setOpenedRadialMenu(boolean hasOpenedRadialMenu);
    void setHudColor(int color);
    void setHudColor(float[] color);
    int getHudColorHex();
    float[] getHudColor();
    int getMaxTolerance(@Nonnull LivingEntity livingEntity);
    void setTolerance(@Nonnull LivingEntity livingEntity, int amount);
    int getTolerance(@Nonnull LivingEntity livingEntity);

    @Deprecated
    int getEssence();
    @Deprecated
    void setEssence(int essence);
    @Deprecated
    int getMaxEssence();

}
