package com.rena.cybercraft.datagen;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rena.cybercraft.common.recipe.ComponentSalvageRecipe;
import com.rena.cybercraft.core.Tags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EngineeringTableRecipeBuilder {

    public static final EngineeringTableRecipeBuilder create(IItemProvider upgradeToSalvage){
        return create(upgradeToSalvage, 1);
    }
    public static final EngineeringTableRecipeBuilder create(IItemProvider upgradeToSalvage, int count){
        return new EngineeringTableRecipeBuilder(new ItemStack(upgradeToSalvage, count));
    }
    private final List<ItemStack> components = Lists.newArrayList();
    private final ArrayList<Float> probabilities = Lists.newArrayList();
    private final ItemStack toSalvage;
    private Advancement.Builder advancement;
    public EngineeringTableRecipeBuilder(ItemStack upgradeToSalvage){
        this.toSalvage = upgradeToSalvage;
    }

    public EngineeringTableRecipeBuilder addComponent(IItemProvider component, int count){
       return addComponent(component, count, 0.15f);
    }

    public EngineeringTableRecipeBuilder addComponent(IItemProvider component){
       return addComponent(component, 1);
    }

    public EngineeringTableRecipeBuilder addComponent(IItemProvider component, int count, float probabilty){
        components.add(new ItemStack(component, count));
        probabilities.add(probabilty);
        return this;
    }

    public EngineeringTableRecipeBuilder addComponent(IItemProvider component, float probability){
        return addComponent(component, 1, probability);
    }
    public EngineeringTableRecipeBuilder unlockedBy(String name, ICriterionInstance criteria) {
        this.advancement = Advancement.Builder.advancement();
        this.advancement.addCriterion(name, criteria);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> consumer) {
        this.save(consumer, ForgeRegistries.ITEMS.getKey(this.toSalvage.getItem()));
    }

    public void save(Consumer<IFinishedRecipe> consumer, String saveName) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.toSalvage.getItem());
        if ((new ResourceLocation(saveName)).equals(resourcelocation)) {
            throw new IllegalStateException("Shaped Recipe " + saveName + " should remove its 'save' argument");
        } else {
            this.save(consumer, new ResourceLocation(saveName));
        }
    }

    public void save(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        if (components.size() == 0){
            throw new IllegalStateException("builder has to have at least one component");
        }
        if (this.advancement != null) {
            this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
            consumer.accept(new Result(id, this.components, this.toSalvage, probabilities, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/" + this.toSalvage.getItem().getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
        }else{
            consumer.accept(new Result(id, this.components, this.toSalvage, probabilities));
        }
    }

    public class Result implements IFinishedRecipe{
        private final ResourceLocation id, advancementId;
        private final List<ItemStack> components;
        private final List<Float> probabilities;
        private final ItemStack crafter;
        private final Advancement.Builder advancement;

        public Result(ResourceLocation id, List<ItemStack> components, ItemStack crafter,List<Float> probabilities, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.advancementId = advancementId;
            this.components = components;
            this.crafter = crafter;
            this.advancement = advancement;
            this.probabilities = probabilities;
        }

        public Result(ResourceLocation id, List<ItemStack> components, ItemStack crafter, List<Float> probabilities) {
           this(id, components, crafter,probabilities, null, null);
        }


        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("output", parseItemStack(this.crafter));

            JsonArray array = new JsonArray();
            for (int i = 0;i<components.size();i++){
                JsonObject obj = parseItemStack(components.get(i));
                float prob = probabilities.get(i);
                if (prob != 0.15f)
                    obj.addProperty("probability", prob);
                array.add(obj);
            }
            json.add("components", array);

        }

        private JsonObject parseItemStack(ItemStack stack){
           JsonObject obj = new JsonObject();
           obj.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
           if (stack.getCount() > 1)
            obj.addProperty("count", stack.getCount());
           return obj;
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public IRecipeSerializer<?> getType() {
            return ComponentSalvageRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement == null ? null : this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
