package com.rena.cybercraft.datagen;

import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        makeComponentsSalvaging(consumer);
    }

    private void makeComponentsSalvaging(Consumer<IFinishedRecipe> consumer){
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYES.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYES.get()))
                .save(consumer);
    }
}
