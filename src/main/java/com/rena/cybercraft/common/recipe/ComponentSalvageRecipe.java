package com.rena.cybercraft.common.recipe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.rena.cybercraft.common.item.BlueprintItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ComponentSalvageRecipe implements IRecipe<IInventory> {

    public static final  Serializer SERIALIZER = new Serializer();
    private final ImmutableList<ItemStack> components;
    private final ResourceLocation id;
    private final ItemStack upgrade;

    public ComponentSalvageRecipe(ResourceLocation id, ItemStack upgrade, List<ItemStack> components) {
        this.components = ImmutableList.copyOf(components);
        this.id = id;
        this.upgrade = upgrade;
    }

    /**
     * this matches method works in two directions, either in ur inventory are all components in order to build with a blueprint a upgrade,
     * or u have the upgrade and want to find all components to it.
     */
    @Override
    public boolean matches(IInventory inv, World world) {
        int j = 0;
        boolean hasBlueprint = false;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (ItemStack.matches(inv.getItem(i), components.get(j))) {
                j++;
            } else if (inv.getItem(i).getItem() instanceof BlueprintItem && ItemStack.matches(BlueprintItem.getItemFromBlueprint(inv.getItem(i)), this.upgrade.copy())) {
                hasBlueprint = true;
            } else if (ItemStack.matches(inv.getItem(i), this.upgrade)) {
                return true;
            }
        }
        return j == components.size() - 1 && hasBlueprint;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return upgrade.copy();
    }

    public ImmutableList<ItemStack> getComponents() {
        return components;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return upgrade.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    public static JsonElement getJsonElement(JsonObject obj, String name) {
        return JSONUtils.isArrayNode(obj, name) ? JSONUtils.getAsJsonArray(obj, name)
                : JSONUtils.getAsJsonObject(obj, name);
    }

    private static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ComponentSalvageRecipe> {

        @Override
        public ComponentSalvageRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray comps = json.getAsJsonArray("components");
            List<ItemStack> components = Lists.newArrayList();
            for (JsonElement element : comps) {
                components.add(CraftingHelper.getItemStack(element.getAsJsonObject(), true));
            }
            ItemStack upgrade = CraftingHelper.getItemStack(getJsonElement(json, "output").getAsJsonObject(), true);
            return new ComponentSalvageRecipe(id, upgrade, components);
        }

        @Nullable
        @Override
        public ComponentSalvageRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
            int size = buffer.readInt();
            List<ItemStack> components = Lists.newArrayList();
            for (int i = 0; i < size; i++) {
                components.add(buffer.readItem());
            }
            ItemStack result = buffer.readItem();
            return new ComponentSalvageRecipe(id, result, components);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ComponentSalvageRecipe recipe) {
            buffer.writeInt(recipe.components.size());
            for (ItemStack stack : recipe.components){
                buffer.writeItem(stack);
            }
            buffer.writeItem(recipe.upgrade);
        }
    }
}
