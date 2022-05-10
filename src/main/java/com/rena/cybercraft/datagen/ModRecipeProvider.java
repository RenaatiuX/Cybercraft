package com.rena.cybercraft.datagen;

import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;

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
        //Eyes
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYES.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYES.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYES_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYES.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_NIGHT_VISION.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_NIGHT_VISION.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_NIGHT_VISION_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_NIGHT_VISION.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_UNDERWATER_VISION.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_UNDERWATER_VISION.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_UNDERWATER_VISION_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_UNDERWATER_VISION.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_HUDJACK.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_HUDJACK.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_HUDJACK_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_HUDJACK.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_TARGETING.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_TARGETING.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_TARGETING_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_TARGETING.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_ZOOM.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_ZOOM.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_EYE_UPGRADES_ZOOM_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_EYE_UPGRADES_ZOOM.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.EYES_UPGRADES_HUDLENS.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.EYES_UPGRADES_HUDLENS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.EYES_UPGRADES_HUDLENS_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.EYES_UPGRADES_HUDLENS.get()))
                .save(consumer);

        //Brain
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_CORTICAL_STACK.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_CORTICAL_STACK.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_CORTICAL_STACK_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_CORTICAL_STACK.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_ENDER_HAMMER.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_ENDER_HAMMER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_ENDER_HAMMER_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_ENDER_HAMMER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_MATRIX.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_MATRIX.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_MATRIX_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_MATRIX.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_RADIO.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_SSC.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_RADIO.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BRAIN_UPGRADES_RADIO_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_SSC.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.BRAIN_UPGRADES_RADIO.get()))
                .save(consumer);

        //Heart
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_HEART.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_HEART.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_HEART_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_HEART.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_UPGRADES_DEFIBRILLATOR.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_UPGRADES_DEFIBRILLATOR.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_UPGRADES_DEFIBRILLATOR_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_UPGRADES_DEFIBRILLATOR.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_PLATELETS.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_PLATELETS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_PLATELETS_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_PLATELETS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_MEDKIT.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_MEDKIT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_MEDKIT_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_MEDKIT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_UPGRADES_COUPLER.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_UPGRADES_COUPLER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HEART_UPGRADES_COUPLER_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.HEART_UPGRADES_COUPLER.get()))
                .save(consumer);

        //Lungs
        EngineeringTableRecipeBuilder.create(ItemInit.LUNGS_OXYGEN.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.LUNGS_OXYGEN.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LUNGS_OXYGEN_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.LUNGS_OXYGEN.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LUNGS_HYPEROXYGENATION.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.LUNGS_HYPEROXYGENATION.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LUNGS_HYPEROXYGENATION_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.LUNGS_HYPEROXYGENATION.get()))
                .save(consumer);

        //Dense Battery
        EngineeringTableRecipeBuilder.create(ItemInit.DENSE_BATTERY.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.DENSE_BATTERY.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.DENSE_BATTERY_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.DENSE_BATTERY.get()))
                .save(consumer);

        //Organs
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_LIVER.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_LIVER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_LIVER_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_LIVER.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_SSC.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_SSC.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_METABOLIC.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_BATTERY_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_BATTERY.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_ADRENALINE.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_ADRENALINE.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LOWER_ORGANS_UPGRADES_ADRENALINE_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .unlockedBy("hasItem", has(ItemInit.LOWER_ORGANS_UPGRADES_ADRENALINE.get()))
                .save(consumer);

        //Skin
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_SOLAR.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_SOLAR.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_SOLAR_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_SOLAR.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_SUBDERMAL.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_SUBDERMAL.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_SUBDERMAL_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_SUBDERMAL.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_FAKE.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_FAKE.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_FAKE_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_FAKE.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_IMMUNO.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_IMMUNO.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.SKIN_IMMUNO_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_REACTOR.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.SKIN_IMMUNO.get()))
                .save(consumer);

        //Muscle
        EngineeringTableRecipeBuilder.create(ItemInit.MUSCLE_REFLEXES.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.MUSCLE_REFLEXES.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.MUSCLE_REFLEXES_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_SSC.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.MUSCLE_REFLEXES.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.MUSCLE_REPLACEMENTS.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.MUSCLE_REPLACEMENTS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.MUSCLE_REPLACEMENTS_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.MUSCLE_REPLACEMENTS.get()))
                .save(consumer);

        //Bone
        EngineeringTableRecipeBuilder.create(ItemInit.BONES_UPGRADES_BONELACING.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.BONES_UPGRADES_BONELACING.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BONES_UPGRADES_BONELACING_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.BONES_UPGRADES_BONELACING.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BONES_UPGRADES_BONEFLEX.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.BONES_UPGRADES_BONEFLEX.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BONES_UPGRADES_BONEFLEX_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.BONES_UPGRADES_BONEFLEX.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BONES_UPGRADES_BATTERY.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.BONES_UPGRADES_BATTERY.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.BONES_UPGRADES_BATTERY_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_REACTOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.BONES_UPGRADES_BATTERY.get()))
                .save(consumer);

        //Arm
        EngineeringTableRecipeBuilder.create(ItemInit.ARM_UPGRADES_BOW.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.ARM_UPGRADES_BOW.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.ARM_UPGRADES_BOW_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.ARM_UPGRADES_BOW.get()))
                .save(consumer);

        //Limbs
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_ARM_LEFT.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_ARM_LEFT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_LEFT_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_ARM_LEFT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_ARM_RIGHT.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_ARM_RIGHT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_ARM_RIGHT_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_ARM_RIGHT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_LEG_LEFT.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_LEG_LEFT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_LEG_LEFT_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_LEG_LEFT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_LEG_RIGHT.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_LEG_RIGHT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.CYBER_LIMB_LEG_RIGHT_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .addComponent(ItemInit.COMPONENT_SYNTHNERVES.get())
                .unlockedBy("hasItem", has(ItemInit.CYBER_LIMB_LEG_RIGHT.get()))
                .save(consumer);

        //Hand
        EngineeringTableRecipeBuilder.create(ItemInit.HAND_UPGRADES_CRAFT.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_SSC.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.HAND_UPGRADES_CRAFT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HAND_UPGRADES_CRAFT_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_SSC.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.HAND_UPGRADES_CRAFT.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HAND_UPGRADES_CLAWS.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.HAND_UPGRADES_CLAWS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HAND_UPGRADES_CLAWS_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .addComponent(ItemInit.COMPONENT_STORAGE.get())
                .unlockedBy("hasItem", has(ItemInit.HAND_UPGRADES_CLAWS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HAND_UPGRADES_MINING.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.HAND_UPGRADES_MINING.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.HAND_UPGRADES_MINING_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_FULLERENE.get())
                .unlockedBy("hasItem", has(ItemInit.HAND_UPGRADES_MINING.get()))
                .save(consumer);

        //Legs
        EngineeringTableRecipeBuilder.create(ItemInit.LEG_JUMP_BOOST.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .unlockedBy("hasItem", has(ItemInit.LEG_JUMP_BOOST.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LEG_JUMP_BOOST_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .unlockedBy("hasItem", has(ItemInit.LEG_JUMP_BOOST.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LEG_FALL_DAMAGE.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.LEG_FALL_DAMAGE.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.LEG_FALL_DAMAGE_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .addComponent(ItemInit.COMPONENT_FIBER_OPTICS.get())
                .unlockedBy("hasItem", has(ItemInit.LEG_FALL_DAMAGE.get()))
                .save(consumer);

        //Foot
        EngineeringTableRecipeBuilder.create(ItemInit.FOOT_UPGRADES_SPURS.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.FOOT_UPGRADES_SPURS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.FOOT_UPGRADES_SPURS_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.FOOT_UPGRADES_SPURS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.FOOT_UPGRADES_AQUA.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.FOOT_UPGRADES_AQUA.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.FOOT_UPGRADES_AQUA_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.FOOT_UPGRADES_AQUA.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.FOOT_UPGRADES_WHEELS.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.FOOT_UPGRADES_WHEELS.get()))
                .save(consumer);
        EngineeringTableRecipeBuilder.create(ItemInit.FOOT_UPGRADES_WHEELS_SCAVENGED.get())
                .addComponent(ItemInit.COMPONENT_ACTUATOR.get(), 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_MICRO_ELECTRIC.get())
                .unlockedBy("hasItem", has(ItemInit.FOOT_UPGRADES_WHEELS.get()))
                .save(consumer);

        //Clothes
        EngineeringTableRecipeBuilder.create(ItemInit.SHADES.get())
                .addComponent(Items.BLACK_STAINED_GLASS, 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SHADES.get()))
                .save(consumer);

        EngineeringTableRecipeBuilder.create(ItemInit.SHADES2.get())
                .addComponent(Items.BLACK_STAINED_GLASS, 5, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.SHADES.get()))
                .save(consumer);

        EngineeringTableRecipeBuilder.create(ItemInit.JACKET.get())
                .addComponent(Items.LEATHER, 8, 0.5f)
                .addComponent(Items.INK_SAC, 2, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get(),  1, 0.5f)
                .unlockedBy("hasItem", has(ItemInit.JACKET.get()))
                .save(consumer);

        EngineeringTableRecipeBuilder.create(ItemInit.TRENCHCOAT.get())
                .addComponent(Items.LEATHER, 8, 0.5f)
                .addComponent(Items.INK_SAC, 2, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get(),  1, 0.5f)
                .unlockedBy("hasItem", has(ItemInit.TRENCHCOAT.get()))
                .save(consumer);

        EngineeringTableRecipeBuilder.create(ItemInit.BIKER_HELMET.get())
                .addComponent(Items.LEATHER, 8, 0.5f)
                .addComponent(Items.INK_SAC, 2, 0.5f)
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.BIKER_HELMET.get()))
                .save(consumer);

        EngineeringTableRecipeBuilder.create(ItemInit.KATANA.get())
                .addComponent(Items.IRON_INGOT, 2, 0.5f)
                .addComponent(ItemInit.COMPONENT_TITANIUM.get())
                .addComponent(ItemInit.COMPONENT_PLATING.get())
                .unlockedBy("hasItem", has(ItemInit.KATANA.get()))
                .save(consumer);
    }
}
