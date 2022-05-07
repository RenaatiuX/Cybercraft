package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.recipe.BlueprintCraftingRecipe;
import com.rena.cybercraft.common.recipe.ComponentSalvageRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;

import java.util.Optional;

public class RecipeInit {

    public static final IRecipeType<BlueprintCraftingRecipe> BLUEPRINT_RECIPE_TYPE = register("blueprintrecipe");
    public static final IRecipeType<ComponentSalvageRecipe> COMPONENT_UPGRADE_RECIPE = register("component_recipe");

    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, BLUEPRINT_RECIPE_TYPE, BlueprintCraftingRecipe.SERIALIZER);
        registerRecipe(event, COMPONENT_UPGRADE_RECIPE, ComponentSalvageRecipe.SERIALIZER);
    }

    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type, IRecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer.setRegistryName(new ResourceLocation(type.toString())));
    }

    private static <T extends IRecipe<?>> IRecipeType<T> register(String name){
        return new IRecipeType<T>() {
            @Override
            public String toString() {
                return Cybercraft.modLoc(name).toString();
            }
        };
    }
}
