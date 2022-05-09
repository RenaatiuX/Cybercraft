package com.rena.cybercraft.common.recipe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.RecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComponentSalvageRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();
    private final ImmutableList<ItemStack> components;
    private final ResourceLocation id;
    private final ItemStack upgrade;
    private final float[] probabilities;

    public ComponentSalvageRecipe(ResourceLocation id, ItemStack upgrade, List<ItemStack> components, float[] probabilities) {
        this.components = ImmutableList.copyOf(components);
        this.id = id;
        this.upgrade = upgrade;
        this.probabilities = probabilities;
    }

    /**
     * this matches method works in two directions, either in ur inventory are all components in order to build with a blueprint a upgrade,
     * or u have the upgrade and want to find all components to it.
     * not nbt-sensitive
     */
    @Override
    public boolean matches(IInventory inv, World world) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (inv.getItem(i).isEmpty())
                continue;
            else if (inv.getItem(i).getItem() ==  this.upgrade.getItem() && inv.getItem(i).getCount() >= this.upgrade.getCount()) {
                return true;
            }

        }
        return false;
    }


    @Override
    public ItemStack assemble(IInventory inv) {
        return upgrade.copy();
    }

    public ImmutableList<ItemStack> getComponents() {
        return components;
    }

    public float[] getProbabilities() {
        return probabilities;
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
        return RecipeInit.COMPONENT_UPGRADE_RECIPE;
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
            float[] probabilities = new float[comps.size()];
            int i = 0;
            for (JsonElement element : comps) {
                components.add(CraftingHelper.getItemStack(element.getAsJsonObject(), true));
                probabilities[i] = JSONUtils.getAsFloat(element.getAsJsonObject(),"probability", 0.15f);
                i++;
            }
            ItemStack upgrade = CraftingHelper.getItemStack(getJsonElement(json, "output").getAsJsonObject(), true);
            return new ComponentSalvageRecipe(id, upgrade, components, probabilities);
        }

        @Nullable
        @Override
        public ComponentSalvageRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
            int size = buffer.readInt();
            List<ItemStack> components = Lists.newArrayList();
            float[] probabilities = new float[size];
            for (int i = 0; i < size; i++) {
                components.add(buffer.readItem());
                probabilities[i] = buffer.readFloat();
            }
            ItemStack result = buffer.readItem();
            return new ComponentSalvageRecipe(id, result, components, probabilities);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, ComponentSalvageRecipe recipe) {
            buffer.writeInt(recipe.components.size());
            for (int i = 0;i<recipe.components.size();i++) {
                buffer.writeItem(recipe.components.get(i));
                buffer.writeFloat(recipe.probabilities[i]);
            }
            buffer.writeItem(recipe.upgrade);
        }
    }
}
