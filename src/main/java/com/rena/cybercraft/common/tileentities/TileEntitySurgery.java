package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftSurgeryEvent;
import com.rena.cybercraft.api.ICybercraftUserData;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.block.SurgeryChamberBlock;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.item.CybercraftItem;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.common.block.events.CommonEvents;
import com.rena.cybercraft.common.block.events.EssentialsMissingHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class TileEntitySurgery extends TileEntity implements ITickableTileEntity {

    public ItemStackHandler slotsPlayer = new ItemStackHandler(ICybercraft.EnumSlot.values().length * LibConstants.WARE_PER_SLOT);
    public ItemStackHandler slots = new ItemStackHandler(ICybercraft.EnumSlot.values().length * LibConstants.WARE_PER_SLOT);
    public boolean[] discardSlots = new boolean[120];
    public boolean[] isEssentialMissing = new boolean[ICybercraft.EnumSlot.values().length * 2];
    public int essence = 0;
    public int maxEssence = 0;
    public int wrongSlot = -1;
    public int ticksWrong = 0;
    public int lastEntity = -1;
    public int cooldownTicks = 0;
    public boolean missingPower = false;

    public TileEntitySurgery(TileEntityType<?> tileEntity) {
        super(tileEntity);
    }

    public boolean isUsableByPlayer(PlayerEntity entityPlayer) {
        return this.level.getBlockEntity(worldPosition) == this
                && entityPlayer.distanceToSqr(getBlockPos().getX() + 0.5D, getBlockPos().getY() + 0.5D, getBlockPos().getZ() + 0.5D) <= 64.0D;
    }

    public void updatePlayerSlots(LivingEntity entityLivingBase, ICybercraftUserData cyberwareUserData) {
        setChanged();

        if (cyberwareUserData != null) {
            if (entityLivingBase.getId() != lastEntity) {
                for (int indexEssential = 0; indexEssential < discardSlots.length; indexEssential++) {
                    discardSlots[indexEssential] = false;
                }
                lastEntity = entityLivingBase.getId();
            }
            maxEssence = cyberwareUserData.getMaxTolerance(entityLivingBase);

            // Update slotsPlayer with the items in the player's body
            for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
                NonNullList<ItemStack> cyberwares = cyberwareUserData.getInstalledCybercraft(slot);
                for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                    ItemStack toPut = cyberwares.get(indexSlot).copy();

                    // If there's a new item, don't set it to discard by default unless it conflicts
                    if (!ItemStack.matches(toPut, slotsPlayer.getStackInSlot(slot.ordinal() * LibConstants.WARE_PER_SLOT + indexSlot))) {
                        discardSlots[slot.ordinal() * LibConstants.WARE_PER_SLOT + indexSlot] = doesItemConflict(toPut, slot, indexSlot);
                    }
                    slotsPlayer.setStackInSlot(slot.ordinal() * LibConstants.WARE_PER_SLOT + indexSlot, toPut);
                }
                updateEssential(slot);
            }

            // Check for items with requirements that are no longer fulfilled
            boolean needToCheck = true;
            while (needToCheck) {
                needToCheck = false;
                for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
                    for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                        int index = slot.ordinal() * LibConstants.WARE_PER_SLOT + indexSlot;

                        ItemStack stack = slots.getStackInSlot(index);
                        if (!stack.isEmpty()
                                && !areRequirementsFulfilled(stack, slot, indexSlot)) {
                            addItemStack(entityLivingBase, stack);
                            slots.setStackInSlot(index, ItemStack.EMPTY);
                            needToCheck = true;
                        }
                    }
                }
            }

            this.updateEssence();
        } else {
            slotsPlayer = new ItemStackHandler(120);
            this.maxEssence = CybercraftConfig.C_ESSENCE.essence.get();
            for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
                updateEssential(slot);
            }
        }
        wrongSlot = -1;
    }

    public boolean doesItemConflict(@Nonnull ItemStack stack, ICybercraft.EnumSlot slot, int indexSlotToCheck) {
        int row = slot.ordinal();
        if (!stack.isEmpty()) {
            for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                if (indexSlot != indexSlotToCheck) {
                    int index = row * LibConstants.WARE_PER_SLOT + indexSlot;
                    ItemStack slotStack = slots.getStackInSlot(index);
                    ItemStack playerStack = slotsPlayer.getStackInSlot(index);

                    ItemStack otherStack = !slotStack.isEmpty() ? slotStack : (discardSlots[index] ? ItemStack.EMPTY : playerStack);

                    // Automatically incompatible with the same item/damage. Doesn't use areCyberwareStacksEqual because items conflict even if different grades.
                    if (!otherStack.isEmpty() && (otherStack.getItem() == stack.getItem() && otherStack.getDamageValue() == stack.getDamageValue())) {
                        setWrongSlot(index);
                        return true;
                    }

                    // Incompatible if either stack doesn't like the other one
                    if (!otherStack.isEmpty() && CybercraftAPI.getCybercraft(otherStack).isIncompatible(otherStack, stack)) {
                        setWrongSlot(index);
                        return true;
                    }
                    if (!otherStack.isEmpty() && CybercraftAPI.getCybercraft(stack).isIncompatible(stack, otherStack)) {
                        setWrongSlot(index);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void setWrongSlot(int index) {
        this.wrongSlot = index;
        CommonEvents.wrong(this);
    }

    public void disableDependants(ItemStack stack, ICybercraft.EnumSlot slot, int indexSlotToCheck) {
        int row = slot.ordinal();
        if (!stack.isEmpty()) {
            for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                if (indexSlot != indexSlotToCheck) {
                    int index = row * LibConstants.WARE_PER_SLOT + indexSlot;
                    ItemStack playerStack = slotsPlayer.getStackInSlot(index);

                    if (!areRequirementsFulfilled(playerStack, slot, indexSlotToCheck)) {
                        discardSlots[index] = true;
                    }
                }
            }
        }
    }

    public void enableDependsOn(ItemStack stack, ICybercraft.EnumSlot slot, int indexSlotToCheck) {
        if (!stack.isEmpty()) {
            ICybercraft ware = CybercraftAPI.getCybercraft(stack);
            boolean found = false;
            for (Item needed : ware.requiredInstalledItems()) {
                outerLoop:
                for (int row = 0; row < ICybercraft.EnumSlot.values().length; row++) {
                    for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                        if (indexSlot != indexSlotToCheck) {
                            int index = row * LibConstants.WARE_PER_SLOT + indexSlot;
                            ItemStack playerStack = slotsPlayer.getStackInSlot(index);

                            if (!playerStack.isEmpty() && playerStack.getItem() == needed.getItem()) {
                                found = true;
                                discardSlots[index] = false;
                                break outerLoop;
                            }
                        }
                    }
                }

            }
            if (!found) {
                Cybercraft.LOGGER.error(String.format("Can't find required %s for %s in %s:%d",
                        stack, slot, indexSlotToCheck));
            }
        }
    }

    public boolean canDisableItem(ItemStack stack, ICybercraft.EnumSlot slot, int indexSlotToCheck) {
        if (!stack.isEmpty()) {
            for (int row = 0; row < ICybercraft.EnumSlot.values().length; row++) {
                for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                    if (indexSlot != indexSlotToCheck) {
                        int index = row * LibConstants.WARE_PER_SLOT + indexSlot;
                        ItemStack slotStack = slots.getStackInSlot(index);
                        ItemStack playerStack = ItemStack.EMPTY;

                        ItemStack otherStack = !slotStack.isEmpty() ? slotStack : (discardSlots[index] ? ItemStack.EMPTY : playerStack);

                        if (!areRequirementsFulfilled(otherStack, slot, indexSlotToCheck)) {
                            setWrongSlot(index);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean areRequirementsFulfilled(ItemStack stack, ICybercraft.EnumSlot slot, int indexSlotToCheck) {
        if (!stack.isEmpty()) {
            ICybercraft ware = CybercraftAPI.getCybercraft(stack);
            for (Item needed : ware.requiredInstalledItems()) {
                boolean found = false;
                int row = slot.ordinal();
                outerLoop:
                for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                    if (indexSlot != indexSlotToCheck) {
                        int index = row * LibConstants.WARE_PER_SLOT + indexSlot;
                        ItemStack slotStack = slots.getStackInSlot(index);
                        ItemStack playerStack = slotsPlayer.getStackInSlot(index);

                        ItemStack otherStack = !slotStack.isEmpty() ? slotStack : (discardSlots[index] ? ItemStack.EMPTY : playerStack);

                        if (!otherStack.isEmpty() && otherStack.getItem() == needed.getItem()) {
                            found = true;
                            break outerLoop;
                        }
                    }
                }
                if (!found) return false;
            }
        }

        return true;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT compoundNBT) {
        super.load(p_230337_1_, compoundNBT);

        slots.deserializeNBT(compoundNBT.getCompound("inv"));
        slotsPlayer.deserializeNBT(compoundNBT.getCompound("inv2"));

        CompoundNBT list = compoundNBT.getCompound("discard");
        for (int indexEssential = 0; list.contains("" + indexEssential); indexEssential++) {
            this.discardSlots[indexEssential] = list.getBoolean("" + indexEssential);
        }

        this.essence = compoundNBT.getInt("essence");
        this.maxEssence = compoundNBT.getInt("maxEssence");
        this.lastEntity = compoundNBT.getInt("lastEntity");
        this.missingPower = compoundNBT.getBoolean("missingPower");
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        compoundNBT = super.save(compoundNBT);

        compoundNBT.putInt("essence", essence);
        compoundNBT.putInt("maxEssence", maxEssence);
        compoundNBT.putInt("lastEntity", lastEntity);
        compoundNBT.putBoolean("missingPower", missingPower);

        compoundNBT.put("inv", this.slots.serializeNBT());
        compoundNBT.put("inv2", this.slotsPlayer.serializeNBT());

        CompoundNBT list = new CompoundNBT();
        int i = 0;
        for (boolean discardSlot : this.discardSlots) {
            list.putBoolean("" + i, discardSlot);
            i++;
        }
        compoundNBT.put("discard", list);

        return compoundNBT;
    }

    public void updateEssential(ICybercraft.EnumSlot slot) {
        if (slot.hasEssential()) {
            byte answer = isEssential(slot);
            boolean foundFirst = (answer & 1) > 0;
            boolean foundSecond = (answer & 2) > 0;
            this.isEssentialMissing[slot.ordinal() * 2] = !foundFirst;
            this.isEssentialMissing[slot.ordinal() * 2 + 1] = !foundSecond;
        } else {
            this.isEssentialMissing[slot.ordinal() * 2] = false;
            this.isEssentialMissing[slot.ordinal() * 2 + 1] = false;
        }
    }

    private byte isEssential(ICybercraft.EnumSlot slot) {
        byte r = 0;

        for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
            int index = slot.ordinal() * LibConstants.WARE_PER_SLOT + indexSlot;
            ItemStack slotStack = slots.getStackInSlot(index);
            ItemStack playerStack = slotsPlayer.getStackInSlot(index);

            ItemStack stack = !slotStack.isEmpty() ? slotStack : (discardSlots[index] ? ItemStack.EMPTY : playerStack);

            if (!stack.isEmpty()) {
                ICybercraft ware = CybercraftAPI.getCybercraft(stack);
                if (ware.isEssential(stack)) {
                    if (slot.isSided() && ware instanceof ICybercraft.ISidedLimb) {
                        if (((ICybercraft.ISidedLimb) ware).getSide(stack) == ICybercraft.ISidedLimb.EnumSide.LEFT && (r & 1) == 0) {
                            r += 1;
                        } else if ((r & 2) == 0) {
                            r += 2;
                        }
                    } else {
                        return 3;
                    }
                }

            }
        }
        return r;
    }

    @Override
    public void tick() {
        if (inProgress && progressTicks < 80) {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(targetEntity);
            if (targetEntity != null
                    && !targetEntity.isAlive()
                    && cyberwareUserData != null) {
                BlockPos pos = getBlockPos();

                if (progressTicks > 20 && progressTicks < 60) {
                    targetEntity.setPos(pos.getX() + .5F, targetEntity.getY(), pos.getZ() + .5F);
                }

                if (progressTicks >= 20 && progressTicks <= 60 && progressTicks % 5 == 0) {
                    targetEntity.hurt(EssentialsMissingHandler.surgery, 2F);
                }

                if (progressTicks == 60) {
                    processUpdate(cyberwareUserData);
                }

                progressTicks++;

                if (CommonEvents.workingOnPlayer(targetEntity)) {
                    workingOnPlayer = true;
                    playerProgressTicks = progressTicks;
                }
            } else {
                inProgress = false;
                progressTicks = 0;
                if (CommonEvents.workingOnPlayer(targetEntity)) {
                    workingOnPlayer = false;
                }
                targetEntity = null;

                BlockState state = level.getBlockState(getBlockPos().below());
                if (state.getBlock() instanceof SurgeryChamberBlock) {
                    ((SurgeryChamberBlock) state.getBlock()).toggleDoor(true, state, getBlockPos().below(), level);
                }
            }
        } else if (inProgress) {
            if (CommonEvents.workingOnPlayer(targetEntity)) {
                workingOnPlayer = false;
            }
            inProgress = false;
            progressTicks = 0;
            targetEntity = null;
            cooldownTicks = 60;

            BlockState state = level.getBlockState(getBlockPos().below());
            if (state.getBlock() instanceof SurgeryChamberBlock) {
                ((SurgeryChamberBlock) state.getBlock()).toggleDoor(true, state, getBlockPos().below(), level);
            }
        }

        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
    }

    public void processUpdate(ICybercraftUserData cyberwareUserData) {
        updatePlayerSlots(targetEntity, cyberwareUserData);

        for (int indexCyberSlot = 0; indexCyberSlot < ICybercraft.EnumSlot.values().length; indexCyberSlot++) {
            ICybercraft.EnumSlot slot = ICybercraft.EnumSlot.values()[indexCyberSlot];
            NonNullList<ItemStack> nnlToInstall = NonNullList.create();
            for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                nnlToInstall.add(ItemStack.EMPTY);
            }

            int indexToInstall = 0;
            for (int indexCyberware = indexCyberSlot * LibConstants.WARE_PER_SLOT; indexCyberware < (indexCyberSlot + 1) * LibConstants.WARE_PER_SLOT; indexCyberware++) {
                ItemStack itemStackSurgery = slots.getStackInSlot(indexCyberware);

                ItemStack itemStackPlayer = slotsPlayer.getStackInSlot(indexCyberware).copy();
                if (!itemStackSurgery.isEmpty()) {
                    ItemStack itemStackToSet = itemStackSurgery.copy();
                    if (CybercraftAPI.areCybercraftStacksEqual(itemStackToSet, itemStackPlayer)) {
                        int maxSize = CybercraftAPI.getCybercraft(itemStackToSet).installedStackSize(itemStackToSet);

                        if (itemStackToSet.getCount() < maxSize) {
                            int numToShift = Math.min(maxSize - itemStackToSet.getCount(), itemStackPlayer.getCount());
                            itemStackPlayer.shrink(numToShift);
                            itemStackToSet.grow(numToShift);
                        }
                    }

                    if (!itemStackPlayer.isEmpty()) {
                        itemStackPlayer = CybercraftAPI.sanitize(itemStackPlayer);

                        addItemStack(targetEntity, itemStackPlayer);
                    }

                    nnlToInstall.set(indexToInstall, itemStackToSet);
                    indexToInstall++;
                } else if (!itemStackPlayer.isEmpty()) {
                    if (discardSlots[indexCyberware]) {
                        itemStackPlayer = CybercraftAPI.sanitize(itemStackPlayer);

                        addItemStack(targetEntity, itemStackPlayer);
                    } else {
                        nnlToInstall.set(indexToInstall, slotsPlayer.getStackInSlot(indexCyberware).copy());
                        indexToInstall++;
                    }
                }
            }
            if (!level.isClientSide) {
                cyberwareUserData.setInstalledCybercraft(targetEntity, slot, nnlToInstall);
            }
            cyberwareUserData.setHasEssential(slot, !isEssentialMissing[indexCyberSlot * 2], !isEssentialMissing[indexCyberSlot * 2 + 1]);
        }
        cyberwareUserData.setTolerance(targetEntity, essence);
        cyberwareUserData.updateCapacity();
        cyberwareUserData.setImmune();
        if (!level.isClientSide) {
            CybercraftAPI.updateData(targetEntity);
        }
        slots = new ItemStackHandler(120);

        CybercraftSurgeryEvent.Post postSurgeryEvent = new CybercraftSurgeryEvent.Post(targetEntity);
        MinecraftForge.EVENT_BUS.post(postSurgeryEvent);
    }

    private void addItemStack(LivingEntity entityLivingBase, ItemStack stack) {
        boolean flag = true;

        if (entityLivingBase instanceof PlayerEntity) {
            PlayerEntity entityPlayer = ((PlayerEntity) entityLivingBase);
            flag = !entityPlayer.inventory.add(stack);
        }

        if (flag && !level.isClientSide) {
            ItemEntity item = new ItemEntity(level, getBlockPos().getX() + .5F, getBlockPos().getY() - 2F, getBlockPos().getZ() + .5F, stack);
            level.addFreshEntity(item);
        }
    }

    public void notifyChange() {
        boolean opened = level.getBlockState(getBlockPos().below()).getValue(SurgeryChamberBlock.OPEN);

        if (!opened) {
            BlockPos p = getBlockPos();
            List<LivingEntity> entityLivingBases = level.getEntitiesOfClass(LivingEntity.class,
                    new AxisAlignedBB(p.getX(), p.getY() - 2F, p.getZ(),
                            p.getX() + 1F, p.getY(), p.getZ() + 1F));
            if (entityLivingBases.size() == 1) {
                LivingEntity entityLivingBase = entityLivingBases.get(0);
                CybercraftSurgeryEvent.Pre preSurgeryEvent = new CybercraftSurgeryEvent.Pre(entityLivingBase, slotsPlayer, slots);

                if (!MinecraftForge.EVENT_BUS.post(preSurgeryEvent)) {
                    this.inProgress = true;
                    this.progressTicks = 0;
                    this.targetEntity = entityLivingBase;
                } else {
                    BlockState state = level.getBlockState(getBlockPos().below());
                    if (state.getBlock() instanceof SurgeryChamberBlock) {
                        ((SurgeryChamberBlock) state.getBlock()).toggleDoor(true, state, getBlockPos().below(), level);
                    }
                }
            }
        }
    }

    public boolean inProgress = false;
    public LivingEntity targetEntity = null;
    public int progressTicks = 0;
    public static boolean workingOnPlayer = false;
    public static int playerProgressTicks = 0;


    public boolean canOpen() {
        return !inProgress && cooldownTicks <= 0;
    }

    public void updateEssence() {
        this.essence = this.maxEssence;
        boolean hasConsume = false;
        boolean hasProduce = false;

        for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
            for (int indexSlot = 0; indexSlot < LibConstants.WARE_PER_SLOT; indexSlot++) {
                int index = slot.ordinal() * LibConstants.WARE_PER_SLOT + indexSlot;
                ItemStack slotStack = slots.getStackInSlot(index);
                ItemStack playerStack = slotsPlayer.getStackInSlot(index);

                ItemStack stack = !slotStack.isEmpty() ? slotStack : (discardSlots[index] ? ItemStack.EMPTY : playerStack);

                if (!stack.isEmpty()) {
                    ItemStack ret = stack.copy();
                    if (!slotStack.isEmpty()
                            && !ret.isEmpty()
                            && !playerStack.isEmpty()
                            && CybercraftAPI.areCybercraftStacksEqual(playerStack, ret)) {
                        int maxSize = CybercraftAPI.getCybercraft(ret).installedStackSize(ret);

                        if (ret.getCount() < maxSize) {
                            int numToShift = Math.min(maxSize - ret.getCount(), playerStack.getCount());
                            ret.grow(numToShift);
                        }
                    }
                    ICybercraft ware = CybercraftAPI.getCybercraft(ret);

                    this.essence -= ware.getEssenceCost(ret);

                    if (ware instanceof CybercraftItem && ((CybercraftItem) ware).getPowerConsumption(ret) > 0) {
                        hasConsume = true;
                    }
                    if (ware instanceof CybercraftItem && (((CybercraftItem) ware).getPowerProduction(ret) > 0 || ware == ItemInit.CREATIVE_BATTERY.get())) {
                        hasProduce = true;
                    }
                }
            }
        }

        this.missingPower = hasConsume && !hasProduce;
    }

}
