package com.rena.cybercraft.common.tileentities;

import com.google.common.collect.Lists;
import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.client.renderer.tileentity.TileEntityEngineeringRender;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.container.EngineeringTableContainer;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.recipe.ComponentSalvageRecipe;
import com.rena.cybercraft.common.util.NNLUtil;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.RecipeInit;
import com.rena.cybercraft.core.init.TileEntityTypeInit;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TileEntityEngineeringTable extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity {

    private static final int[] SLOTS_UP = new int[]{2, 3, 4, 5, 6, 7};
    private static final int[] SLOTS_DOWN = new int[]{1, 9};
    private static final int[] SLOTS_SIDE = new int[]{0, 8};
    protected boolean isGuiOpen;
    private int numPlayerOpenGui = 0;
    private Item prevItem = null;
    private double cooldown = TileEntityEngineeringRender.MAX_HEIGHT;
    private boolean playAnimation = false;
    protected LazyOptional<IItemHandlerModifiable>[] itemHandler = SidedInvWrapper.create(this, Direction.DOWN, Direction.UP, Direction.NORTH);

    NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);

    public TileEntityEngineeringTable() {
        super(TileEntityTypeInit.ENGINEERING_TABLE.get());
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            if (playAnimation) {
                cooldown -= 0.01d;
                double distance = TileEntityEngineeringRender.MAX_HEIGHT - (TileEntityEngineeringRender.MAX_HEIGHT - TileEntityEngineeringRender.MIN_HEIGHT) * 2;
                if (cooldown <= distance) {
                    cooldown = TileEntityEngineeringRender.MAX_HEIGHT;
                    playAnimation = false;
                }
            }
            if (!getItem(8).isEmpty()) {
                ComponentSalvageRecipe recipe = getBlueprintRecipe();
                if (recipe != null && checkComponents(recipe)) {
                    setItem(9, recipe.getResultItem().copy());
                }
            }
        }
    }

    public void salvage() {
        if (!level.isClientSide() && !getItem(0).isEmpty()) {
            ComponentSalvageRecipe recipe = getSalvageRecipe();
            Inventory components = getComponentInventory();
            if (recipe != null && !isFull(components) && !playAnimation) {
                this.playAnimation = true;
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

    private boolean checkComponents(ComponentSalvageRecipe recipe) {
        Inventory inv = getComponentInventory();
        NonNullList<ItemStack> components = NNLUtil.deepCopyList(recipe.getComponents());
        for (int j = 0; j < components.size(); j++) {
            ItemStack shouldComponent = components.get(j);
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack component = inv.getItem(i);
                if (shouldComponent.getItem() == component.getItem()) {
                    shouldComponent.shrink(component.getCount());
                }
            }
        }
        for (ItemStack stack : components){
            if (!stack.isEmpty())
                return false;
        }
        return true;
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
    public ComponentSalvageRecipe getSalvageRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.COMPONENT_UPGRADE_RECIPE, new Inventory(getItem(0)), this.level).orElse(null);
    }

    @Nullable
    public ComponentSalvageRecipe getBlueprintRecipe() {
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.COMPONENT_UPGRADE_RECIPE, new Inventory(BlueprintItem.getItemFromBlueprint(getItem(8))), this.level).orElse(null);
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

    /**
     * gets u the component inventory but keep in mind that this is just a deep copy
     * if u want any changes to happen on the actual inventory use {@link TileEntityEngineeringTable#setComponentInventory(IInventory)}
     */
    public Inventory getComponentInventory() {
        Inventory ret = new Inventory(6);
        for (int i = 2; i < 8; i++) {
            ret.setItem(i - 2, this.getItem(i).copy());
        }
        return ret;
    }

    /**
     * sets the contents of this inventory as components, the inventory should only contain components and must have a size bigger then 6
     */
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

    @OnlyIn(Dist.CLIENT)
    public void setPlayAnimation(boolean playAnimation) {
        this.playAnimation = playAnimation;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isPlayAnimation() {
        return playAnimation;
    }
}
