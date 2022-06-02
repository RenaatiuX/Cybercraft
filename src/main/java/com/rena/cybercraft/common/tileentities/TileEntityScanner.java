package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.container.ScannerContainer;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.recipe.BlueprintCraftingRecipe;
import com.rena.cybercraft.core.init.RecipeInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityScanner extends LockableLootTileEntity implements ITickableTileEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int maxCounter = 1, counter = 0, counterPercentage;

    public TileEntityScanner() {
        super(TileEntityTypeInit.SCANNER_TE.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            BlueprintCraftingRecipe recipe = getRecipe();
            if (recipe != null) {
                if (canProcess(recipe)) {
                    if (counter == 0) {
                        startProcessing(recipe);
                        counter++;
                    } else {
                        if (counter < maxCounter)
                            process(recipe);
                        else if (counter >= maxCounter) {
                            finishProcessing(recipe);
                        }
                    }
                }
                if (!canProcess(recipe)) {
                    reset();
                }
            } else
                reset();
            counterPercentage = (int) (((double) counter) * 10000d / ((double) maxCounter));
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(super.getUpdateTag());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 0, this.save(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    private void reset() {
        counter = 0;
    }

    private void startProcessing(BlueprintCraftingRecipe recipe) {
        counter = 0;
        maxCounter = recipe.getNeededWorkTime();
        blockUpdate();
    }

    private void finishProcessing(BlueprintCraftingRecipe recipe) {
        counter = 0;
        if (new Random().nextDouble() <= getBlueprintChance(this))
            setItem(2, recipe.assemble(this).copy());
        removeItem(0, 1);
        removeItem(1, 1);
        blockUpdate();
    }

    public static double getBlueprintChance(LockableLootTileEntity te) {
        return Math.min(CybercraftConfig.C_MACHINES.scannerChance.get() + (te.getItem(1).getCount() - 1) * CybercraftConfig.C_MACHINES.scannerChanceAddl.get(), CybercraftConfig.C_MACHINES.maxChance.get()) / 100;
    }

    private void process(BlueprintCraftingRecipe recipe) {
        counter++;
    }

    private boolean canProcess(BlueprintCraftingRecipe recipe) {
        if (!getItem(2).isEmpty())
            return false;
        return getItem(0).getItem() == Items.PAPER && !getItem(0).isEmpty();
    }

    @Nullable
    private BlueprintCraftingRecipe getRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.BLUEPRINT_RECIPE_TYPE, this, this.level).orElse(null);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        if (!this.tryLoadLootTable(nbt))
            nbt = ItemStackHelper.saveAllItems(nbt, this.items);
        nbt.putInt("counter", this.counter);
        nbt.putInt("maxCounter", this.maxCounter);
        nbt.putInt("counterPercentage", this.counterPercentage);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        readItems(nbt);
        this.counter = nbt.getInt("counter");
        this.maxCounter = nbt.getInt("maxCounter");
        this.counterPercentage = nbt.getInt("counterPercentage");
    }

    protected void readItems(CompoundNBT nbt) {
        if (!this.tryLoadLootTable(nbt))
            ItemStackHelper.loadAllItems(nbt, this.items);
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".scanner").withStyle(TextFormatting.AQUA);
    }

    @Override
    protected Container createMenu(int id, PlayerInventory inv) {
        return new ScannerContainer(id, inv, this);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    public int getCounterPercentage() {
        return counterPercentage;
    }

    public void setCounterPercentage(int counterPercentage) {
        this.counterPercentage = counterPercentage;
    }

    private void blockUpdate() {
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }
}
