package com.rena.cybercraft.common.tileentities;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.container.EngineeringTableContainer;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.recipe.ComponentSalvageRecipe;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.RecipeInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
import com.rena.cybercraft.core.network.CCNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
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

    private static final int[] SLOTS_UP = new int[]{2, 3, 4, 5, 6, 7};
    private static final int[] SLOTS_DOWN = new int[]{1, 9};
    private static final int[] SLOTS_SIDE = new int[]{0, 8};
    private boolean isGuiOpen;
    private int numPlayerOpenGui = 0;
    private Item prevItem = null;
    protected LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH);

    NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);

    public TileEntityEngineeringTable() {
        super(TileEntityTypeInit.ENGINEERING_TABLE.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (prevItem != getItem(0).getItem()){
                prevItem = getItem(0).getItem();
            }
        }
    }

    public void salvage() {
        if (!level.isClientSide() && !getItem(0).isEmpty()) {
            ComponentSalvageRecipe recipe = getSalvageRecipe();
            Inventory components = getComponentInventory();
            if (recipe != null && !isFull(components)) {
                for (int i = 0; i < recipe.getComponents().size(); i++) {
                    if (this.level.random.nextFloat() <= recipe.getProbabilities()[i]) {
                        ItemStack component = recipe.getComponents().get(i);
                        if (component.getCount() > 1)
                            component.shrink(this.level.random.nextInt(component.getCount()));
                        if (!component.isEmpty())
                            tryAddItemToInventory(components, component);
                    }
                }
                this.removeItem(0, 1);
                ItemStack blueprint = BlueprintItem.getBlueprintForItem(this.getItem(0));
                if (!getItem(1).isEmpty() && this.level.random.nextDouble() * 100d < CybercraftConfig.C_MACHINES.engineeringChance.get()) {
                    tryAddItemToInventory(components, blueprint);
                    this.removeItem(1, 1);
                }
                setComponentInventory(components);
            }
        }
    }

    @Override
    public void startOpen(PlayerEntity player) {
        numPlayerOpenGui++;
        isGuiOpen = numPlayerOpenGui > 0;
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        numPlayerOpenGui--;
        isGuiOpen = numPlayerOpenGui > 0;
    }

    private void tryAddItemToInventory(Inventory inv, ItemStack stack) {
        ItemStack added = inv.addItem(stack);
        if (!added.isEmpty()) {
            InventoryHelper.dropItemStack(this.level, this.getBlockPos().getX(), this.getBlockPos().getY() + 1.0f, this.getBlockPos().getZ(), stack);
        }
    }

    /**
     * just makes recipe gathering faster
     */
    @Nullable
    private ComponentSalvageRecipe getSalvageRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.COMPONENT_UPGRADE_RECIPE, new Inventory(getItem(0)), this.level).orElse(null);
    }

    @Nullable
    private ComponentSalvageRecipe getBlueprintRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.COMPONENT_UPGRADE_RECIPE, this, this.level).orElse(null);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        ItemStackHelper.loadAllItems(nbt, items);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt = super.save(nbt);
        nbt = ItemStackHelper.saveAllItems(nbt, items);
        return nbt;
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

    public Inventory getComponentInventory() {
        Inventory ret = new Inventory(6);
        for (int i = 2; i < 8; i++) {
            ret.setItem(i - 2, this.getItem(i));
        }
        return ret;
    }

    public void setComponentInventory(IInventory inv) {
        if (inv.getContainerSize() <= 6) {
            for (int i = 0; i < inv.getContainerSize(); i++) {
                this.setItem(i + 2, inv.getItem(i));
            }
        }
    }

    public static boolean isFull(IInventory inv) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (inv.getItem(i).isEmpty())
                return false;
        }
        return true;
    }
}
