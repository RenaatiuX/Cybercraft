package com.rena.cybercraft.api;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.item.HotkeyHelper;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.api.item.ICybercraft.EnumSlot;
import com.rena.cybercraft.api.item.IHudjack;
import com.rena.cybercraft.api.item.IMenuItem;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.common.util.NNLUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rena.cybercraft.api.CybercraftAPI.withMetaData;

public class CybercraftUserDataImpl implements ICybercraftUserData{
    @Override
    public NonNullList<ItemStack> getInstalledCybercraft(EnumSlot slot) {
        return null;
    }

    @Override
    public void setInstalledCybercraft(LivingEntity livingEntity, EnumSlot slot, List<ItemStack> cybercraft) {

    }

    @Override
    public void setInstalledCybercraft(LivingEntity livingEntity, EnumSlot slot, NonNullList<ItemStack> cybercraft) {

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
    public boolean hasEssential(EnumSlot slot) {
        return false;
    }

    @Override
    public void setHasEssential(EnumSlot slot, boolean hasLeft, boolean hasRight) {

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
    public boolean hasEssential(EnumSlot slot, ICybercraft.ISidedLimb.EnumSide side) {
        return false;
    }

    @Override
    public void resetWare(LivingEntity livingEntity) {

    }

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

    /*public static final Capability.IStorage<ICybercraftUserData> STORAGE = new CybercraftUserDataStorage();

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

    public CybercraftUserDataImpl(){

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

    }

    @Override
    public void resetWare(LivingEntity entityLivingBase)
    {
        for (NonNullList<ItemStack> nnlCyberwaresInSlot : cyberwaresBySlot)
        {
            for (ItemStack item : nnlCyberwaresInSlot)
            {
                if (CybercraftAPI.isCybercraft(item))
                {
                    CybercraftAPI.getCybercraft(item).onRemoved(entityLivingBase, item);
                }
            }
        }
        missingEssence = 0;
        for (EnumSlot slot : EnumSlot.values())
        {
            NonNullList<ItemStack> nnlCyberwaresInSlot = NonNullList.create();
            NonNullList<ItemStack> startItems = CybercraftConfig.getStartingItems(slot);
            for (ItemStack startItem : startItems)
            {
                nnlCyberwaresInSlot.add(startItem.copy());
            }
            cyberwaresBySlot.set(slot.ordinal(), nnlCyberwaresInSlot);
        }
        missingEssentials = new boolean[EnumSlot.values().length * 2];
        updateCapacity();
    }

    @Override
    public List<ItemStack> getPowerOutages()
    {
        return nnlPowerOutages;
    }

    @Override
    public List<Integer> getPowerOutageTimes()
    {
        return ticksPowerOutages;
    }

    @Override
    public int getCapacity()
    {
        int specialCap = 0;
        for (ItemStack item : specialBatteries)
        {
            ISpecialBattery battery = (ISpecialBattery) CybercraftAPI.getCybercraft(item);
            specialCap += battery.getCapacity(item);
        }
        return power_capacity + specialCap;
    }

    @Override
    public int getStoredPower()
    {
        int specialStored = 0;
        for (ItemStack item : specialBatteries)
        {
            ISpecialBattery battery = (ISpecialBattery) CybercraftAPI.getCybercraft(item);
            specialStored += battery.getStoredEnergy(item);
        }
        return power_stored + specialStored;
    }

    @Override
    public float getPercentFull()
    {
        if (getCapacity() == 0) return -1F;
        return getStoredPower() / (float) getCapacity();
    }

    @Override
    public boolean isAtCapacity(ItemStack stack)
    {
        return isAtCapacity(stack, 0);
    }

    @Override
    public boolean isAtCapacity(ItemStack stack, int buffer)
    {
        // buffer = Math.min(power_capacity - 1, buffer); TODO
        int leftOverSpaceNormal = power_capacity - power_stored;

        if (leftOverSpaceNormal > buffer) return false;

        int leftOverSpaceSpecial = 0;

        for (ItemStack batteryStack : specialBatteries)
        {
            ISpecialBattery battery = (ISpecialBattery) CybercraftAPI.getCybercraft(batteryStack);
            int spaceInThisSpecial = battery.add(batteryStack, stack, buffer + 1, true);
            leftOverSpaceSpecial += spaceInThisSpecial;

            if (leftOverSpaceNormal + leftOverSpaceSpecial > buffer) return false;
        }

        return true;
    }

    @Override
    public void addPower(int amount, ItemStack inputter)
    {
        if (amount < 0)
        {
            throw new IllegalArgumentException("Amount must be positive!");
        }

        ItemStack stack = ItemStack.EMPTY;
        if (!inputter.isEmpty())
        {
            if ( inputter.hasTag()
                    || inputter.getCount() != 1 )
            {
                stack = withMetaData(new ItemStack(inputter.getItem(), 1), inputter.getDamageValue());
            }
            else
            {
                stack = inputter;
            }
        }

        Integer amountExisting = power_buffer.get(stack);
        power_buffer.put(stack, amount + (amountExisting == null ? 0 : amountExisting));

        power_production += amount;
    }

    private boolean canGiveOut = true;

    @Override
    public boolean usePower(ItemStack stack, int amount)
    {
        return usePower(stack, amount, true);
    }

    private int ComputeSum(@Nonnull Map<ItemStack, Integer> map)
    {
        int total = 0;
        for (ItemStack key : map.keySet())
        {
            total += map.get(key);
        }
        return total;
    }

    private void subtractFromBufferLast(int amount)
    {
        for (ItemStack key : power_lastBuffer.keySet())
        {
            int get = power_lastBuffer.get(key);
            int amountToSubtract = Math.min(get, amount);
            amount -= amountToSubtract;
            power_lastBuffer.put(key, get - amountToSubtract);
            if (amount <= 0) break;
        }
    }

    @Override
    public boolean usePower(ItemStack stack, int amount, boolean isPassive)
    {
        if (isImmune) return true;

        if (!canGiveOut)
        {
            if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            {
                setOutOfPower(stack);
            }
            return false;
        }

        power_consumption += amount;

        int sumPowerBufferLast = ComputeSum(power_lastBuffer);
        int amountAvailable = power_stored + sumPowerBufferLast;

        int amountAvailableSpecial = 0;
        if (amountAvailable < amount)
        {
            int amountMissing = amount - amountAvailable;

            for (ItemStack batteryStack : specialBatteries)
            {
                ISpecialBattery battery = (ISpecialBattery) CybercraftAPI.getCybercraft(batteryStack);
                int extract = battery.extract(batteryStack, amountMissing, true);

                amountMissing -= extract;
                amountAvailableSpecial += extract;

                if (amountMissing <= 0) break;
            }

            if (amountAvailableSpecial + amountAvailable >= amount)
            {
                amountMissing = amount - amountAvailable;

                for (ItemStack batteryStack : specialBatteries)
                {
                    ISpecialBattery battery = (ISpecialBattery) CybercraftAPI.getCybercraft(batteryStack);
                    int extract = battery.extract(batteryStack, amountMissing, false);

                    amountMissing -= extract;

                    if (amountMissing <= 0) break;
                }

                amount -= amountAvailableSpecial;
            }
        }

        if (amountAvailable < amount)
        {
            if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            {
                setOutOfPower(stack);
            }
            if (isPassive)
            {
                canGiveOut = false;
            }
            return false;
        }

        int leftAfterBuffer = Math.max(0, amount - sumPowerBufferLast);
        subtractFromBufferLast(amount);
        power_stored -= leftAfterBuffer;
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void setOutOfPower(ItemStack stack)
    {
        PlayerEntity entityPlayer = Minecraft.getInstance().player;
        if ( entityPlayer != null
                && !stack.isEmpty() )
        {
            int indexFound = -1;
            int indexLoop = 0;
            for (ItemStack stackExisting : nnlPowerOutages)
            {
                if ( !stackExisting.isEmpty()
                        && stackExisting.getItem() == stack.getItem()
                        && stackExisting.getDamageValue() == stack.getDamageValue() )
                {
                    indexFound = indexLoop;
                    break;
                }
                indexLoop++;
            }
            if (indexFound != -1)
            {
                nnlPowerOutages.remove(indexFound);
                ticksPowerOutages.remove(indexFound);
            }
            nnlPowerOutages.add(stack);
            ticksPowerOutages.add(entityPlayer.tickCount);
            if (nnlPowerOutages.size() >= 8)
            {
                nnlPowerOutages.remove(0);
                ticksPowerOutages.remove(0);
            }
        }
    }

    @Override
    public NonNullList<ItemStack> getInstalledCybercraft(EnumSlot slot)
    {
        return cyberwaresBySlot.get(slot.ordinal());
    }

    @Override
    public boolean hasEssential(EnumSlot slot)
    {
        return !missingEssentials[slot.ordinal() * 2];
    }

    @Override
    public boolean hasEssential(EnumSlot slot, ICybercraft.ISidedLimb.EnumSide side)
    {
        return !missingEssentials[slot.ordinal() * 2 + (side == ICybercraft.ISidedLimb.EnumSide.LEFT ? 0 : 1)];
    }

    @Override
    public void setHasEssential(EnumSlot slot, boolean hasLeft, boolean hasRight)
    {
        missingEssentials[slot.ordinal() * 2    ] = !hasLeft;
        missingEssentials[slot.ordinal() * 2 + 1] = !hasRight;
    }


    @Override
    public void setInstalledCybercraft(LivingEntity entityLivingBase, EnumSlot slot, @Nonnull List<ItemStack> cyberwaresToInstall)
    {
        while (cyberwaresToInstall.size() > LibConstants.WARE_PER_SLOT)
        {
            cyberwaresToInstall.remove(cyberwaresToInstall.size() - 1);
        }
        while (cyberwaresToInstall.size() < LibConstants.WARE_PER_SLOT)
        {
            cyberwaresToInstall.add(ItemStack.EMPTY);
        }
        setInstalledCybercraft(entityLivingBase, slot, NNLUtil.fromArray(cyberwaresToInstall.toArray(new ItemStack[0])));
    }

    @Override
    public void updateCapacity()
    {
        power_capacity = 0;
        specialBatteries = NonNullList.create();
        activeItems = NonNullList.create();
        hudjackItems = NonNullList.create();
        hotkeys = new HashMap<>();

        for (EnumSlot slot : EnumSlot.values())
        {
            for (ItemStack itemStackCyberware : getInstalledCybercraft(slot))
            {
                if (CybercraftAPI.isCybercraft(itemStackCyberware))
                {
                    ICybercraft cyberware = CybercraftAPI.getCybercraft(itemStackCyberware);

                    if ( cyberware instanceof IMenuItem
                            && ((IMenuItem) cyberware).hasMenu(itemStackCyberware) )
                    {
                        activeItems.add(itemStackCyberware);

                        int hotkey = HotkeyHelper.getHotkey(itemStackCyberware);
                        if (hotkey != -1)
                        {
                            hotkeys.put(hotkey, itemStackCyberware);
                        }
                    }

                    if (cyberware instanceof IHudjack)
                    {
                        hudjackItems.add(itemStackCyberware);
                    }

                    if (cyberware instanceof ISpecialBattery)
                    {
                        specialBatteries.add(itemStackCyberware);
                    }
                    else
                    {
                        power_capacity += cyberware.getCapacity(itemStackCyberware);
                    }
                }
            }
        }

        power_stored = Math.min(power_stored, power_capacity);
    }

    @Override
    public void setInstalledCybercraft(LivingEntity entityLivingBase, EnumSlot slot, NonNullList<ItemStack> cyberwaresToInstall)
    {
        if (cyberwaresToInstall.size() != cyberwaresBySlot.get(slot.ordinal()).size())
        {
            Cybercraft.LOGGER.error(String.format("Invalid number of cyberware to install: found %d, expecting %d",
                    cyberwaresToInstall.size(), cyberwaresBySlot.get(slot.ordinal()).size() ));
        }
        NonNullList<ItemStack> cyberwaresInstalled = cyberwaresBySlot.get(slot.ordinal());

        if (entityLivingBase != null)
        {
            for (ItemStack itemStackInstalled : cyberwaresInstalled)
            {
                if (!CybercraftAPI.isCybercraft(itemStackInstalled)) continue;

                boolean found = false;
                for (ItemStack itemStackToInstall : cyberwaresToInstall)
                {
                    if ( CybercraftAPI.areCybercraftStacksEqual(itemStackToInstall, itemStackInstalled)
                            && itemStackToInstall.getCount() == itemStackInstalled.getCount() )
                    {
                        found = true;
                        break;
                    }
                }

                if (!found)
                {
                    CybercraftAPI.getCybercraft(itemStackInstalled).onRemoved(entityLivingBase, itemStackInstalled);
                }
            }

            for (ItemStack itemStackToInstall : cyberwaresToInstall)
            {
                if (!CybercraftAPI.isCybercraft(itemStackToInstall)) continue;

                boolean found = false;
                for (ItemStack oldWare : cyberwaresInstalled)
                {
                    if ( CybercraftAPI.areCybercraftStacksEqual(itemStackToInstall, oldWare)
                            && itemStackToInstall.getCount() == oldWare.getCount() )
                    {
                        found = true;
                        break;
                    }
                }

                if (!found)
                {
                    CybercraftAPI.getCybercraft(itemStackToInstall).onAdded(entityLivingBase, itemStackToInstall);
                }
            }
        }

        cyberwaresBySlot.set(slot.ordinal(), cyberwaresToInstall);
    }

    @Override
    public boolean isCybercraftInstalled(ItemStack cyberware)
    {
        return getCybercraftRank(cyberware) > 0;
    }

    @Override
    public int getCybercraftRank(ItemStack cyberwareTemplate)
    {
        ItemStack cyberwareFound = getCybercraft(cyberwareTemplate);

        if (!cyberwareFound.isEmpty())
        {
            return cyberwareFound.getCount();
        }

        return 0;
    }

    @Override
    public ItemStack getCybercraft(ItemStack cyberware)
    {
        for (ItemStack itemStack : getInstalledCybercraft(CybercraftAPI.getCybercraft(cyberware).getSlot(cyberware)))
        {
            if ( !itemStack.isEmpty()
                    && itemStack.getItem() == cyberware.getItem()
                    && itemStack.getDamageValue() == cyberware.getDamageValue() )
            {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT tagCompound = new CompoundNBT();
        ListNBT listSlots = new ListNBT();

        for (EnumSlot slot : EnumSlot.values())
        {
            ListNBT listCyberwares = new ListNBT();
            for (ItemStack cyberware : getInstalledCybercraft(slot))
            {
                CompoundNBT tagCompoundCyberware = new CompoundNBT();
                if (!cyberware.isEmpty())
                {
                    cyberware.save(tagCompoundCyberware);
                }
                listCyberwares.appendTag(tagCompoundCyberware);
            }
            listSlots.appendTag(listCyberwares);
        }

        tagCompound.put("cybercraft", listSlots);

        ListNBT listEssentials = new ListNBT();
        for (boolean missingEssential : missingEssentials)
        {
            listEssentials.appendTag(new ByteNBT((byte) (missingEssential ? 1 : 0)));
        }
        tagCompound.put("discard", listEssentials);
        tagCompound.put("powerBuffer", serializeMap(power_buffer));
        tagCompound.put("powerBufferLast", serializeMap(power_lastBuffer));
        tagCompound.putInt("powerCap", power_capacity);
        tagCompound.putInt("storedPower", power_stored);
        tagCompound.putInt("missingEssence", missingEssence);
        tagCompound.put("hud", hudData);
        tagCompound.putInt("color", hudColor);
        tagCompound.putBoolean("hasOpenedRadialMenu", hasOpenedRadialMenu);
        return tagCompound;
    }

    private ListNBT serializeMap(@Nonnull Map<ItemStack, Integer> map)
    {
        ListNBT listMap = new ListNBT();

        for (ItemStack stack : map.keySet())
        {
            CompoundNBT tagCompoundEntry = new CompoundNBT();
            tagCompoundEntry.putBoolean("null", stack.isEmpty());
            if (!stack.isEmpty())
            {
                CompoundNBT tagCompoundItem = new CompoundNBT();
                stack.save(tagCompoundItem);
                tagCompoundEntry.put("item", tagCompoundItem);
            }
            tagCompoundEntry.putInt("value", map.get(stack));

            listMap.appendTag(tagCompoundEntry);
        }

        return listMap;
    }

    private Map<ItemStack, Integer> deserializeMap(@Nonnull ListNBT listMap)
    {
        Map<ItemStack, Integer> map = new HashMap<>();
        for (int index = 0; index < listMap.tagCount(); index++)
        {
            CompoundNBT tagCompoundEntry = listMap.getCompoundTagAt(index);
            boolean isNull = tagCompoundEntry.getBoolean("null");
            ItemStack stack = ItemStack.EMPTY;
            if (!isNull)
            {
                stack = new ItemStack(tagCompoundEntry.getCompoundTag("item"));
            }

            map.put(stack, tagCompoundEntry.getInt("value"));
        }

        return map;
    }

    @Override
    public void deserializeNBT(CompoundNBT tagCompound)
    {
        power_buffer = deserializeMap(tagCompound.getList("powerBuffer", Constants.NBT.TAG_COMPOUND));
        power_capacity = tagCompound.getInt("powerCap");
        power_lastBuffer = deserializeMap((ListNBT) tagCompound.get("powerBufferLast"));

        power_stored = tagCompound.getInt("storedPower");
        if (tagCompound.contains("essence"))
        {
            missingEssence = getMaxEssence() - tagCompound.getInt("essence");
        }
        else
        {
            missingEssence = tagCompound.getInt("missingEssence");
        }
        hudData = tagCompound.getCompound("hud");
        hasOpenedRadialMenu = tagCompound.getBoolean("hasOpenedRadialMenu");
        ListNBT listEssentials = (ListNBT) tagCompound.get("discard");
        for (int indexEssential = 0; indexEssential < listEssentials.tagCount(); indexEssential++)
        {
            missingEssentials[indexEssential] = ((ByteNBT) listEssentials.get(indexEssential)).getAsByte() > 0;
        }

        ListNBT listSlots = (ListNBT) tagCompound.get("cybercraft");
        for (int indexBodySlot = 0; indexBodySlot < listSlots.tagCount(); indexBodySlot++)
        {
            EnumSlot slot = EnumSlot.values()[indexBodySlot];

            ListNBT listCyberwares = (ListNBT) listSlots.get(indexBodySlot);
            NonNullList<ItemStack> nnlCyberwaresOfType = NonNullList.create();
            for (int indexInventorySlot = 0; indexInventorySlot < LibConstants.WARE_PER_SLOT; indexInventorySlot++){
                nnlCyberwaresOfType.add(ItemStack.EMPTY);
            }

            int countInventorySlots = Math.min(listCyberwares.tagCount(), nnlCyberwaresOfType.size());
            for (int indexInventorySlot = 0; indexInventorySlot < countInventorySlots; indexInventorySlot++)
            {
                nnlCyberwaresOfType.set(indexInventorySlot, new ItemStack(listCyberwares.getCompoundTagAt(indexInventorySlot)));
            }

            setInstalledCybercraft(null, slot, nnlCyberwaresOfType);
        }

        int color = 0x00FFFF;

        if (tagCompound.contains("color"))
        {
            color = tagCompound.getInt("color");
        }
        setHudColor(color);

        updateCapacity();
    }

    private static class CybercraftUserDataStorage implements Capability.IStorage<ICybercraftUserData>
    {
        @Override
        public INBT writeNBT(Capability<ICybercraftUserData> capability, ICybercraftUserData cyberwareUserData, Direction side)
        {
            return cyberwareUserData.serializeNBT();
        }

        @Override
        public void readNBT(Capability<ICybercraftUserData> capability, ICybercraftUserData cyberwareUserData, Direction side, INBT nbt)
        {
            if (nbt instanceof CompoundNBT)
            {
                cyberwareUserData.deserializeNBT((CompoundNBT) nbt);
            }
            else
            {
                throw new IllegalStateException("Cybercraft INBT should be a CompoundNBT!");
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT>
    {
        public static final ResourceLocation NAME = new ResourceLocation(Cybercraft.MOD_ID, "cybercraft");

        private final ICybercraftUserData cyberwareUserData = new CybercraftUserDataImpl();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, Direction facing)
        {
            return capability == CybercraftAPI.CYBERCRAFT_CAPABILITY;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, Direction facing)
        {
            if (capability == CybercraftAPI.CYBERCRAFT_CAPABILITY)
            {
                return CybercraftAPI.CYBERCRAFT_CAPABILITY.cast(cyberwareUserData);
            }

            return null;
        }

        @Override
        public CompoundNBT serializeNBT()
        {
            return cyberwareUserData.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT tagCompound)
        {
            cyberwareUserData.deserializeNBT(tagCompound);
        }
    }

    private void storePower(Map<ItemStack, Integer> map)
    {
        for (ItemStack itemStackSpecialBattery : specialBatteries)
        {
            ISpecialBattery specialBattery = (ISpecialBattery) CybercraftAPI.getCybercraft(itemStackSpecialBattery);
            for (Map.Entry<ItemStack, Integer> entryBuffer : map.entrySet())
            {
                int amountBuffer = entryBuffer.getValue();
                int amountTaken = specialBattery.add(itemStackSpecialBattery, entryBuffer.getKey(), amountBuffer, false);
                entryBuffer.setValue(amountBuffer - amountTaken);
            }
        }
        power_stored = Math.min(power_capacity, power_stored + ComputeSum(map));
    }

    @Override
    public void resetBuffer()
    {
        canGiveOut = true;
        storePower(power_lastBuffer);
        power_lastBuffer = power_buffer;
        power_buffer = new HashMap<>(power_buffer.size());
        isImmune = false;

        power_lastConsumption = power_consumption;
        power_lastProduction = power_production;
        power_production = 0;
        power_consumption = 0;
    }

    @Override
    public void setImmune()
    {
        isImmune = true;
    }

    private boolean isImmune = false;

    @Override
    @Deprecated
    public int getEssence()
    {
        return getMaxEssence() - missingEssence;
    }

    @Override
    @Deprecated
    public int getMaxEssence()
    {
        return CybercraftConfig.C_ESSENCE.essence.get();
    }

    @Override
    @Deprecated
    public void setEssence(int essence)
    {
        missingEssence = getMaxEssence() - essence;
    }

    @Override
    public int getMaxTolerance(@Nonnull LivingEntity entityLivingBase)
    {
        return (int) entityLivingBase.getAttributes().getInstance(CybercraftAPI.TOLERANCE_ATTR).getBaseValue();
    }

    @Override
    public int getTolerance(@Nonnull LivingEntity entityLivingBase)
    {
        return getMaxTolerance(entityLivingBase) - missingEssence;
    }

    @Override
    public void setTolerance(@Nonnull LivingEntity entityLivingBase, int amount)
    {
        missingEssence = getMaxTolerance(entityLivingBase) - amount;
    }



    @Override
    public int getNumActiveItems()
    {
        return activeItems.size();
    }

    @Override
    public List<ItemStack> getActiveItems()
    {
        return activeItems;
    }

    @Override
    public List<ItemStack> getHudjackItems()
    {
        return hudjackItems;
    }

    @Override
    public void removeHotkey(int i)
    {
        hotkeys.remove(i);
    }

    @Override
    public void addHotkey(int i, ItemStack stack)
    {
        hotkeys.put(i, stack);
    }

    @Override
    public ItemStack getHotkey(int i)
    {
        if (!hotkeys.containsKey(i))
        {
            return ItemStack.EMPTY;
        }
        return hotkeys.get(i);
    }

    @Override
    public Iterable<Integer> getHotkeys()
    {
        return hotkeys.keySet();
    }

    @Override
    public void setHudData(CompoundNBT tagCompound)
    {
        hudData = tagCompound;
    }

    @Override
    public CompoundNBT getHudData()
    {
        return hudData;
    }

    @Override
    public boolean hasOpenedRadialMenu()
    {
        return hasOpenedRadialMenu;
    }

    @Override
    public void setOpenedRadialMenu(boolean hasOpenedRadialMenu)
    {
        this.hasOpenedRadialMenu = hasOpenedRadialMenu;
    }

    @Override
    public void setHudColor(int hexVal)
    {
        float r = ((hexVal >> 16) & 0x0000FF) / 255F;
        float g = ((hexVal >> 8) & 0x0000FF) / 255F;
        float b = ((hexVal) & 0x0000FF) / 255F;
        setHudColor(new float[] { r, g, b });
    }

    @Override
    public int getHudColorHex()
    {
        return hudColor;
    }

    @Override
    public void setHudColor(float[] color)
    {
        hudColorFloat = color;
        int ri = Math.round(color[0] * 255);
        int gi = Math.round(color[1] * 255);
        int bi = Math.round(color[2] * 255);

        int rp = (ri << 16) & 0xFF0000;
        int gp = (gi << 8) & 0x00FF00;
        int bp = (bi) & 0x0000FF;
        hudColor = rp | gp | bp;
    }

    @Override
    public float[] getHudColor()
    {
        return hudColorFloat;
    }

    @Override
    public int getProduction()
    {
        return power_lastProduction;
    }

    @Override
    public int getConsumption()
    {
        return power_lastConsumption;
    }*/
}
