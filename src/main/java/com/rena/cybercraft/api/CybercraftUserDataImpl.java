package com.rena.cybercraft.api;

import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.ICybercraft.EnumSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CybercraftUserDataImpl implements ICybercraftUserData{

    /*public static final Capability.IStorage<ICybercraftUserData> STORAGE = new CybercraftUserDataStorage();*/

    private NonNullList<NonNullList<ItemStack>> cyberwaresBySlot = NonNullList.create();
    private boolean[] missingEssentials = new boolean[EnumSlot.values().length * 2];

    private int power_stored = 0;
    private int power_production = 0;
    private int power_lastProduction = 0;
    private int power_consumption = 0;
    private int power_lastConsumption = 0;
    private int power_capacity = 0;
    private Map<ItemStack, Integer> power_buffer = new HashMap<>();
    private Map<ItemStack, Integer> power_lastBuffer = new HashMap<>();
    private NonNullList<ItemStack> nnlPowerOutages = NonNullList.create();
    private List<Integer> ticksPowerOutages = new ArrayList<>();
    private int missingEssence = 0;
    private NonNullList<ItemStack> specialBatteries = NonNullList.create();
    private NonNullList<ItemStack> activeItems = NonNullList.create();
    private NonNullList<ItemStack> hudjackItems = NonNullList.create();
    private Map<Integer, ItemStack> hotkeys = new HashMap<>();
    private CompoundNBT hudData;
    private boolean hasOpenedRadialMenu = false;

    private int hudColor = 0x00FFFF;
    private float[] hudColorFloat = new float[] { 0.0F, 1.0F, 1.0F };

   /* public CybercraftUserDataImpl(){

        hudData = new CompoundNBT();
        for (EnumSlot slot : EnumSlot.values())
        {
            NonNullList<ItemStack> nnlCyberwaresInSlot = NonNullList.create();
            for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++)
            {
                nnlCyberwaresInSlot.add(ItemStack.EMPTY);
            }
            cyberwaresBySlot.add(nnlCyberwaresInSlot);
        }
        resetWare(null);

    }*/

    @Override
    public NonNullList<ItemStack> getInstalledCybercraft(ICybercraft.EnumSlot slot) {
        return null;
    }

    @Override
    public void setInstalledCybercraft(LivingEntity livingEntity, ICybercraft.EnumSlot slot, List<ItemStack> cybercraft) {

    }

    @Override
    public void setInstalledCybercraft(LivingEntity livingEntity, ICybercraft.EnumSlot slot, NonNullList<ItemStack> cybercraft) {

    }

    @Override
    public boolean isCybercraftInstalled(ItemStack cybercraft) {
        return false;
    }

    @Override
    public int getCybercraftRank(ItemStack cybercraft) {
        return 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT tagCompound) {

    }

    @Override
    public boolean hasEssential(ICybercraft.EnumSlot slot) {
        return false;
    }

    @Override
    public void setHasEssential(ICybercraft.EnumSlot slot, boolean hasLeft, boolean hasRight) {

    }

    @Override
    public ItemStack getCybercraft(ItemStack cybercraft) {
        return null;
    }

    @Override
    public void updateCapacity() {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public void addPower(int amount, ItemStack inputter) {

    }

    @Override
    public boolean isAtCapacity(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isAtCapacity(ItemStack stack, int buffer) {
        return false;
    }

    @Override
    public float getPercentFull() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public int getStoredPower() {
        return 0;
    }

    @Override
    public int getProduction() {
        return 0;
    }

    @Override
    public int getConsumption() {
        return 0;
    }

    @Override
    public boolean usePower(ItemStack stack, int amount) {
        return false;
    }

    @Override
    public List<ItemStack> getPowerOutages() {
        return null;
    }

    @Override
    public List<Integer> getPowerOutageTimes() {
        return null;
    }

    @Override
    public void setImmune() {

    }

    @Override
    public boolean usePower(ItemStack stack, int amount, boolean isPassive) {
        return false;
    }

    @Override
    public boolean hasEssential(ICybercraft.EnumSlot slot, ICybercraft.ISidedLimb.EnumSide side) {
        return false;
    }

    @Override
    public void resetWare(LivingEntity livingEntity) {

    }

    /*@Override
    public void resetWare(LivingEntity livingEntity) {
        for (NonNullList<ItemStack> nnlCyberwaresInSlot : cyberwaresBySlot)
        {
            for (ItemStack item : nnlCyberwaresInSlot)
            {
                if (CybercraftAPI.isCybercraft(item))
                {
                    CybercraftAPI.getCybercraft(item).onRemoved(livingEntity, item);
                }
            }
        }
        missingEssence = 0;
        for (EnumSlot slot : EnumSlot.values())
        {
            NonNullList<ItemStack> nnlCyberwaresInSlot = NonNullList.create();
            NonNullList<ItemStack> startItems = CyberwareConfig.getStartingItems(slot);
            for (ItemStack startItem : startItems)
            {
                nnlCyberwaresInSlot.add(startItem.copy());
            }
            cyberwaresBySlot.set(slot.ordinal(), nnlCyberwaresInSlot);
        }
        missingEssentials = new boolean[EnumSlot.values().length * 2];
        updateCapacity();
    }*/

    @Override
    public int getNumActiveItems() {
        return 0;
    }

    @Override
    public List<ItemStack> getActiveItems() {
        return null;
    }

    @Override
    public void removeHotkey(int i) {

    }

    @Override
    public void addHotkey(int i, ItemStack stack) {

    }

    @Override
    public ItemStack getHotkey(int i) {
        return null;
    }

    @Override
    public Iterable<Integer> getHotkeys() {
        return null;
    }

    @Override
    public List<ItemStack> getHudjackItems() {
        return null;
    }

    @Override
    public void setHudData(CompoundNBT tagCompound) {

    }

    @Override
    public CompoundNBT getHudData() {
        return null;
    }

    @Override
    public boolean hasOpenedRadialMenu() {
        return false;
    }

    @Override
    public void setOpenedRadialMenu(boolean hasOpenedRadialMenu) {

    }

    @Override
    public void setHudColor(int color) {

    }

    @Override
    public void setHudColor(float[] color) {

    }

    @Override
    public int getHudColorHex() {
        return 0;
    }

    @Override
    public float[] getHudColor() {
        return new float[0];
    }

    @Override
    public int getMaxTolerance(@Nonnull LivingEntity livingEntity) {
        return 0;
    }

    @Override
    public void setTolerance(@Nonnull LivingEntity livingEntity, int amount) {

    }

    @Override
    public int getTolerance(@Nonnull LivingEntity livingEntity) {
        return 0;
    }

    @Override
    public int getEssence() {
        return 0;
    }

    @Override
    public void setEssence(int essence) {

    }

    @Override
    public int getMaxEssence() {
        return 0;
    }
}
