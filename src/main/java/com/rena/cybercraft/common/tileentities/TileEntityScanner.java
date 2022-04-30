package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.container.ScannerContainer;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.recipe.BlueprintCraftingRecipe;
import com.rena.cybercraft.core.init.RecipeInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class TileEntityScanner extends LockableLootTileEntity implements ITickableTileEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int maxCounter = 1, counter = 0;

    public TileEntityScanner() {
        super(TileEntityTypeInit.SCANNER_TE.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()){
            BlueprintCraftingRecipe recipe = getRecipe();
            if (recipe != null){
                if (canProcess(recipe)){
                    if (counter == 0){
                        startProcessing(recipe);
                        counter++;
                    }else{
                        if (counter < maxCounter)
                            process(recipe);
                        else if (counter >= maxCounter){
                            finishProcessing(recipe);
                        }
                    }
                }
            }
        }
    }

    private void startProcessing(BlueprintCraftingRecipe recipe){
        counter = 0;
        maxCounter = recipe.getNeededWorkTime();
    }

    private void finishProcessing(BlueprintCraftingRecipe recipe){
        counter = 0;
        setItem(2, recipe.assemble(this).copy());
        removeItem(0, 1);
        removeItem(1, 1);
    }

    private void process(BlueprintCraftingRecipe recipe){
        counter++;
    }

    private boolean canProcess(BlueprintCraftingRecipe recipe){
        if (!getItem(2).isEmpty())
            return false;
        return getItem(0).getItem() == Items.PAPER && !getItem(0).isEmpty();
    }

    @Nullable
    private BlueprintCraftingRecipe getRecipe(){
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.BLUEPRINT_RECIPE_TYPE, this, this.level).orElse(null);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
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
}
