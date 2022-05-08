package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.container.EngineeringTableContainer;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.recipe.ComponentSalvageRecipe;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.RecipeInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Arrays;

public class TileEntityEngineeringTable extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {

    private static final int[] SLOTS_UP = new int[]{2,3,4,5,6,7};
    private static final int[] SLOTS_DOWN = new int[]{1, 9};
    private static final int[] SLOTS_SIDE = new int[]{0, 8};
    protected LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH);

    NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);

    public TileEntityEngineeringTable() {
        super(TileEntityTypeInit.ENGINEERING_TABLE.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()){

        }
    }

    public void salvage(){
        if (!level.isClientSide() && !getItem(0).isEmpty()){
            ComponentSalvageRecipe recipe = getSalvageRecipe();
            if (recipe != null){
                Inventory components = getComponentInventory();
                for (ItemStack stack : recipe.getComponents()) {
                    ItemStack added = components.addItem(stack);
                    if (!added.isEmpty()){
                        InventoryHelper.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY() + 1.0f, this.getBlockPos().getZ(), stack);
                    }
                }
                this.removeItem(0, 1);
                ItemStack blueprint = BlueprintItem.getBlueprintForItem(this.getItem(0));
                if (this.level.random.nextDouble() < CybercraftConfig.C_MACHINES.engineeringChance.get() && !getItem(1).isEmpty() &&
                        (getItem(8).isEmpty() ||ItemStack.matches(blueprint, getItem(8)))){
                    this.setItem(8, blueprint);
                    this.removeItem(1, 1);
                }
            }
        }
    }

    /**
     * just makes recipe gathering faster
     */
    @Nullable
    private ComponentSalvageRecipe getSalvageRecipe(){
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.COMPONENT_UPGRADE_RECIPE, new Inventory(getItem(0)), this.level).orElse(null);
    }
    @Nullable
    private ComponentSalvageRecipe getBlueprintRecipe(){
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.COMPONENT_UPGRADE_RECIPE, this, this.level).orElse(null);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = items;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Cybercraft.MOD_ID + ".engineering_table");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory inv) {
        return new EngineeringTableContainer(id, inv, this);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN)
            return SLOTS_DOWN;
        if (side == Direction.UP)
            return SLOTS_UP;
        return SLOTS_SIDE;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 1)
            return stack.getItem() == Items.PAPER;
        if (slot == 9)
            return false;
        if (slot == 8)
            return stack.getItem() instanceof BlueprintItem;
        if (Arrays.stream(SLOTS_UP).anyMatch(index -> index == slot))
            return Tags.Items.COMPONENTS.contains(stack.getItem());
        return super.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot != 0 && slot != 1;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return itemHandler[0].cast();
            else if (facing == Direction.DOWN)
                return itemHandler[1].cast();
            else
                return itemHandler[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    protected void invalidateCaps() {
        for (int x = 0; x < itemHandler.length; x++)
            itemHandler[x].invalidate();
        super.invalidateCaps();
    }

    public Inventory getComponentInventory(){
        Inventory ret = new Inventory(6);
        for (int i = 2;i<8;i++){
            ret.setItem(i, this.getItem(i));
        }
        return ret;
    }
}
