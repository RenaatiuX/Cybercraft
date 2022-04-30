package com.rena.cybercraft.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.core.init.RecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class BlueprintCraftingRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();
    private Ingredient input;
    private final ResourceLocation id;
    private final int neededWorkTime;

    public BlueprintCraftingRecipe(Ingredient input, ResourceLocation id, int neededWorkTime) {
        this.input = input;
        this.id = id;
        this.neededWorkTime = neededWorkTime;
    }

    @Override
    public boolean matches(IInventory inventory, World world) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (this.input.test(inventory.getItem(i)))
                return true;
        }
        return false;
    }

    public int getNeededWorkTime() {
        return neededWorkTime;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return BlueprintItem.getBlueprintForItem(inv.getItem(1));
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    /**
     * not Supported, always retuns null, use the version with an IInventory
     */
    @Override
    public ItemStack getResultItem() {
        return null;
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
        return RecipeInit.BLUEPRINT_RECIPE_TYPE;
    }

    private static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BlueprintCraftingRecipe> {

        public static JsonElement getJsonElement(JsonObject obj, String name) {
            return JSONUtils.isArrayNode(obj, name) ? JSONUtils.getAsJsonArray(obj, name)
                    : JSONUtils.getAsJsonObject(obj, name);
        }

        @Override
        public BlueprintCraftingRecipe fromJson(ResourceLocation id, JsonObject json) {
            final Ingredient input = Ingredient.fromJson(getJsonElement(json, "input"));
            final int workTime = JSONUtils.getAsInt(json, "worktime", 200);
            return new BlueprintCraftingRecipe(input, id, workTime);
        }

        @Nullable
        @Override
        public BlueprintCraftingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            int worktime = buffer.readInt();
            return new BlueprintCraftingRecipe(input, id, worktime);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, BlueprintCraftingRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeInt(recipe.neededWorkTime);
        }
    }
}
