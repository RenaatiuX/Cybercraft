package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.item.*;
import com.rena.cybercraft.common.util.CybercraftArmorMaterial;
import com.rena.cybercraft.common.util.CybercraftItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unchecked")
public class ItemInit {

    public static final int RARE = 10;
    public static final int UNCOMMON = 25;
    public static final int COMMON = 50;
    public static final int VERY_COMMON = 100;


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cybercraft.MOD_ID);

    //Armor
    public static final RegistryObject<Item> BIKER_HELMET = ITEMS.register("biker_helmet",
            ()-> new ArmorItem(CybercraftArmorMaterial.BIKER_HELMET, EquipmentSlotType.HEAD, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> JACKET = ITEMS.register("jacket",
            () -> new ArmorItem(CybercraftArmorMaterial.JACKET, EquipmentSlotType.CHEST, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> SHADES = ITEMS.register("shades",
            () -> new ArmorItem(CybercraftArmorMaterial.SHADES, EquipmentSlotType.HEAD, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> SHADES2 = ITEMS.register("shades2",
            () -> new ArmorItem(CybercraftArmorMaterial.SHADES2, EquipmentSlotType.HEAD, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<CybercraftArmorItem> TRENCHCOAT = ITEMS.register("trenchcoat",
            () -> new CybercraftArmorItem(CybercraftArmorMaterial.TRENCHCOAT, EquipmentSlotType.CHEST, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

    //Weapon
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana",
            () -> new SwordCybercraftItem(CybercraftItemTier.KATANA, 4, -2.5F, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

    //Components
    public static final RegistryObject<Item> COMPONENT_FIBER_OPTICS = ITEMS.register("component_fiberoptics",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_FULLERENE = ITEMS.register("component_fullerene",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_MICRO_ELECTRIC = ITEMS.register("component_microelectric",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_PLATING = ITEMS.register("component_plating",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_REACTOR = ITEMS.register("component_reactor",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_SSC = ITEMS.register("component_ssc",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_STORAGE = ITEMS.register("component_storage",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_SYNTHNERVES = ITEMS.register("component_synthnerves",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_TITANIUM = ITEMS.register("component_titanium",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_ACTUATOR = ITEMS.register("component_actuator",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));


    //Body part
    public static final RegistryObject<Item> EYES = ITEMS.register("body_part_eyes",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES));
    public static final RegistryObject<Item> BRAIN = ITEMS.register("body_part_brain",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM));
    public static final RegistryObject<Item> HEART = ITEMS.register("body_part_heart",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART));
    public static final RegistryObject<Item> LUNGS = ITEMS.register("body_part_lungs",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LUNGS));
    public static final RegistryObject<Item> STOMACH = ITEMS.register("body_part_stomach",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS));
    public static final RegistryObject<Item> SKIN = ITEMS.register("body_part_skin",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN));
    public static final RegistryObject<Item> MUSCLES = ITEMS.register("body_part_muscles",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.MUSCLE));
    public static final RegistryObject<Item> BONES = ITEMS.register("body_part_bones",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE));
    public static final RegistryObject<Item> ARM_LEFT = ITEMS.register("body_part_arm_left",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM));
    public static final RegistryObject<Item> ARM_RIGHT = ITEMS.register("body_part_arm_right",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM));
    public static final RegistryObject<Item> LEG_LEFT = ITEMS.register("body_part_leg_left",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG));
    public static final RegistryObject<Item> LEG_RIGHT = ITEMS.register("body_part_leg_right",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG));


    //Eye Upgrade
    public static final RegistryObject<CybercraftItem> CYBER_EYES = ITEMS.register("cybereyes",
            () -> new CybereyesItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(8)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYES_SCAVENGED = ITEMS.register("cybereyes_scavenged",
            () -> new CybereyesItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(8)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_NIGHT_VISION = ITEMS.register("cybereye_upgrades_night_vision",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_NIGHT_VISION_SCAVENGED = ITEMS.register("cybereye_upgrades_night_vision_scavenged",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_UNDERWATER_VISION = ITEMS.register("cybereye_upgrades_underwater_vision",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_UNDERWATER_VISION_SCAVENGED = ITEMS.register("cybereye_upgrades_underwater_vision_scavenged",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_HUDJACK = ITEMS.register("cybereye_upgrades_hudjack",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_HUDJACK_SCAVENGED = ITEMS.register("cybereye_upgrades_hudjack_scavenged",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_TARGETING = ITEMS.register("cybereye_upgrades_targeting",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_TARGETING_SCAVENGED = ITEMS.register("cybereye_upgrades_targeting_scavenged",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_ZOOM = ITEMS.register("cybereye_upgrades_zoom",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES_ZOOM_SCAVENGED = ITEMS.register("cybereye_upgrades_zoom_scavenged",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> EYES_UPGRADES_HUDLENS = ITEMS.register("eye_upgrades_hudlens",
            () -> new EyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(1)
                    .setWeight(VERY_COMMON));
    public static final RegistryObject<CybercraftItem> EYES_UPGRADES_HUDLENS_SCAVENGED = ITEMS.register("eye_upgrades_hudlens_scavenged",
            () -> new EyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(1)
                    .setWeight(VERY_COMMON));


    //Brain Upgrade

    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_CORTICAL_STACK = ITEMS.register("brain_upgrades_cortical_stack",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(3)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_CORTICAL_STACK_SCAVENGED = ITEMS.register("brain_upgrades_cortical_stack_scavenged",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(3)
                    .setWeight(RARE));

    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_ENDER_HAMMER = ITEMS.register("brain_upgrades_ender_jammer",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(3)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_ENDER_HAMMER_SCAVENGED = ITEMS.register("brain_upgrades_ender_jammer_scavenged",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(3)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER = ITEMS.register("brain_upgrades_consciousness_transmitter",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM,CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER_SCAVENGED = ITEMS.register("brain_upgrades_consciousness_transmitter_scavenged",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM,CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER = ITEMS.register("brain_upgrades_neural_contextualizer",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(COMMON));
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER_SCAVENGED = ITEMS.register("brain_upgrades_neural_contextualizer_scavenged",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(COMMON));

    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_MATRIX = ITEMS.register("brain_upgrades_matrix",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(8)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_MATRIX_SCAVENGED = ITEMS.register("brain_upgrades_matrix_scavenged",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(8)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_RADIO = ITEMS.register("brain_upgrades_radio",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES_RADIO_SCAVENGED = ITEMS.register("brain_upgrades_radio_scavenged",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(UNCOMMON));

    //Capsule
    public static final RegistryObject<Item> EXP_CAPSULE = ITEMS.register("exp_capsule",
            () -> new ExpCapsuleItem(new Item.Properties().stacksTo(1).tab(Cybercraft.CYBERCRAFTAB)));

    //Heart
    public static final RegistryObject<CybercraftItem> CYBER_HEART = ITEMS.register("cyberheart",
            () -> new CyberHeartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(COMMON));
    public static final RegistryObject<CybercraftItem> CYBER_HEART_SCAVENGED = ITEMS.register("cyberheart_scavenged",
            () -> new CyberHeartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(COMMON));


    public static final RegistryObject<CybercraftItem> HEART_UPGRADES_DEFIBRILLATOR = ITEMS.register("heart_upgrades_defibrillator",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(10)
                    .setWeight(COMMON));
    public static final RegistryObject<CybercraftItem> HEART_UPGRADES_DEFIBRILLATOR_SCAVENGED = ITEMS.register("heart_upgrades_defibrillator_scavenged",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(10)
                    .setWeight(COMMON));

    public static final RegistryObject<CybercraftItem> HEART_PLATELETS = ITEMS.register("heart_upgrades_platelets",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> HEART_PLATELETS_SCAVENGED = ITEMS.register("heart_upgrades_platelets_scavenged",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> HEART_MEDKIT = ITEMS.register("heart_upgrades_medkit",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> HEART_MEDKIT_SCAVENGED = ITEMS.register("heart_upgrades_medkit_scavenged",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> HEART_UPGRADES_COUPLER = ITEMS.register("heart_upgrades_coupler",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(10)
                    .setWeight(VERY_COMMON));
    public static final RegistryObject<CybercraftItem> HEART_UPGRADES_COUPLER_SCAVENGED = ITEMS.register("heart_upgrades_coupler_scavenged",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(10)
                    .setWeight(VERY_COMMON));


    //Lungs
    public static final RegistryObject<CybercraftItem> LUNGS_OXYGEN = ITEMS.register("lungs_upgrades_oxygen",
            () -> new LungsUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LUNGS, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> LUNGS_OXYGEN_SCAVENGED = ITEMS.register("lungs_upgrades_oxygen_scavenged",
            () -> new LungsUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LUNGS, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> LUNGS_HYPEROXYGENATION = ITEMS.register("lungs_upgrades_hyperoxygenation",
            () -> new LungsUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LUNGS, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(COMMON));
    public static final RegistryObject<CybercraftItem> LUNGS_HYPEROXYGENATION_SCAVENGED = ITEMS.register("lungs_upgrades_hyperoxygenation_scavenged",
            () -> new LungsUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LUNGS, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(COMMON));

    //Organs
    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_LIVER = ITEMS.register("lower_organs_upgrades_liver_filter",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_LIVER_SCAVENGED = ITEMS.register("lower_organs_upgrades_liver_filter_scavenged",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_METABOLIC = ITEMS.register("lower_organs_upgrades_metabolic",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(COMMON));
    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_METABOLIC_SCAVENGED = ITEMS.register("lower_organs_upgrades_metabolic_scavenged",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(COMMON));

    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_BATTERY = ITEMS.register("lower_organs_upgrades_battery",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(10)
                    .setWeight(VERY_COMMON));
    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_BATTERY_SCAVENGED = ITEMS.register("lower_organs_upgrades_battery_scavenged",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(10)
                    .setWeight(VERY_COMMON));

    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_ADRENALINE = ITEMS.register("lower_organs_upgrades_adrenaline",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES_ADRENALINE_SCAVENGED = ITEMS.register("lower_organs_upgrades_adrenaline_scavenged",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));

    //Skin
    public static final RegistryObject<CybercraftItem> SKIN_SOLAR = ITEMS.register("skin_upgrades_solar_skin",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(12)
                    .setWeight(VERY_COMMON));
    public static final RegistryObject<CybercraftItem> SKIN_SOLAR_SCAVENGED = ITEMS.register("skin_upgrades_solar_skin_scavenged",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(12)
                    .setWeight(VERY_COMMON));

    public static final RegistryObject<CybercraftItem> SKIN_SUBDERMAL = ITEMS.register("skin_upgrades_subdermal_spikes",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(12)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> SKIN_SUBDERMAL_SCAVENGED = ITEMS.register("skin_upgrades_subdermal_spikes_scavenged",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(12)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> SKIN_FAKE = ITEMS.register("skin_upgrades_fake_skin",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(0)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> SKIN_FAKE_SCAVENGED = ITEMS.register("skin_upgrades_fake_skin_scavenged",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(0)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> SKIN_IMMUNO = ITEMS.register("skin_upgrades_immuno",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(-25)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> SKIN_IMMUNO_SCAVENGED = ITEMS.register("skin_upgrades_immuno_scavenged",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(-25)
                    .setWeight(RARE));


    //Muscle
    public static final RegistryObject<CybercraftItem> MUSCLE_REFLEXES = ITEMS.register("muscle_upgrades_wired_reflexes",
            () -> new MuscleUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.MUSCLE, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> MUSCLE_REFLEXES_SCAVENGED = ITEMS.register("muscle_upgrades_wired_reflexes_scavenged",
            () -> new MuscleUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.MUSCLE, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> MUSCLE_REPLACEMENTS = ITEMS.register("muscle_upgrades_muscle_replacements",
            () -> new MuscleUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.MUSCLE, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> MUSCLE_REPLACEMENTS_SCAVENGED = ITEMS.register("muscle_upgrades_muscle_replacements_scavenged",
            () -> new MuscleUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.MUSCLE, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15)
                    .setWeight(RARE));

    //Bone
    public static final RegistryObject<CybercraftItem> BONES_UPGRADES_BONELACING = ITEMS.register("bone_upgrades_bonelacing",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(3)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> BONES_UPGRADES_BONELACING_SCAVENGED = ITEMS.register("bone_upgrades_bonelacing_scavenged",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(3)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> BONES_UPGRADES_BONEFLEX = ITEMS.register("bone_upgrades_boneflex",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(5)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> BONES_UPGRADES_BONEFLEX_SCAVENGED = ITEMS.register("bone_upgrades_boneflex_scavenged",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(5)
                    .setWeight(RARE));

    public static final RegistryObject<CybercraftItem> BONES_UPGRADES_BATTERY = ITEMS.register("bone_upgrades_bonebattery",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> BONES_UPGRADES_BATTERY_SCAVENGED = ITEMS.register("bone_upgrades_bonebattery_scavenged",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE, CybercraftAPI.QUALITY_SCAVENGED)
                    .setWeight(UNCOMMON));

    //Arm
    public static final RegistryObject<CybercraftItem> ARM_UPGRADES_BOW = ITEMS.register("arm_upgrades_bow",
            () -> new ArmUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(3)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> ARM_UPGRADES_BOW_SCAVENGED = ITEMS.register("arm_upgrades_bow_scavenged",
            () -> new ArmUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(3)
                    .setWeight(RARE));

    //Hand
    public static final RegistryObject<CybercraftItem> HAND_UPGRADES_CRAFT = ITEMS.register("hand_upgrades_craft_hands",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HAND, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> HAND_UPGRADES_CRAFT_SCAVENGED = ITEMS.register("hand_upgrades_craft_hands_scavenged",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HAND, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(RARE));

    public static final RegistryObject<CybercraftItem> HAND_UPGRADES_CLAWS = ITEMS.register("hand_upgrades_claws",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HAND, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> HAND_UPGRADES_CLAWS_SCAVENGED = ITEMS.register("hand_upgrades_claws_scavenged",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB),ICybercraft.EnumSlot.HAND, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(RARE));

    public static final RegistryObject<CybercraftItem> HAND_UPGRADES_MINING = ITEMS.register("hand_upgrades_mining",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HAND, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(1)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> HAND_UPGRADES_MINING_SCAVENGED = ITEMS.register("hand_upgrades_mining_scavenged",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HAND, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(1)
                    .setWeight(RARE));

    //Legs
    public static final RegistryObject<CybercraftItem> LEG_JUMP_BOOST = ITEMS.register("leg_upgrades_jump_boost",
            () -> new LegUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(3)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> LEG_JUMP_BOOST_SCAVENGED = ITEMS.register("leg_upgrades_jump_boost_scavenged",
            () -> new LegUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(3)
                    .setWeight(RARE));

    public static final RegistryObject<CybercraftItem> LEG_FALL_DAMAGE = ITEMS.register("leg_upgrades_fall_damage",
            () -> new LegUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> LEG_FALL_DAMAGE_SCAVENGED = ITEMS.register("leg_upgrades_fall_damage_scavenged",
            () -> new LegUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(RARE));

    //Foot
    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_SPURS = ITEMS.register("foot_upgrades_spurs",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_SPURS_SCAVENGED = ITEMS.register("foot_upgrades_spurs_scavenged",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(1)
                    .setWeight(UNCOMMON));

    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_AQUA = ITEMS.register("foot_upgrades_aqua",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(2)
                    .setWeight(RARE));
    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_AQUA_SCAVENGED = ITEMS.register("foot_upgrades_aqua_scavenged",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(2)
                    .setWeight(RARE));

    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_WHEELS = ITEMS.register("foot_upgrades_wheels",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(3)
                    .setWeight(UNCOMMON));
    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_WHEELS_SCAVENGED = ITEMS.register("foot_upgrades_wheels_scavenged",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(3)
                    .setWeight(UNCOMMON));

    //Limbs
    public static final RegistryObject<CybercraftItem> CYBER_LIMB_ARM_LEFT = ITEMS.register("cyberlimbs_cyberarm_left",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.LEFT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15));
    public static final RegistryObject<CybercraftItem> CYBER_LIMB_LEFT_SCAVENGED = ITEMS.register("cyberlimbs_cyberarm_left_scavenged",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.LEFT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15));

    public static final RegistryObject<CybercraftItem> CYBER_LIMB_ARM_RIGHT = ITEMS.register("cyberlimbs_cyberarm_right",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.RIGHT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15));
    public static final RegistryObject<CybercraftItem> CYBER_LIMB_ARM_RIGHT_SCAVENGED = ITEMS.register("cyberlimbs_cyberarm_right_scavenged",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM, ICybercraft.ISidedLimb.EnumSide.RIGHT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15));

    public static final RegistryObject<CybercraftItem> CYBER_LIMB_LEG_LEFT = ITEMS.register("cyberlimbs_cyberleg_left",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.LEFT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15));
    public static final RegistryObject<CybercraftItem> CYBER_LIMB_LEG_LEFT_SCAVENGED = ITEMS.register("cyberlimbs_cyberleg_left_scavenged",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.LEFT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15));

    public static final RegistryObject<CybercraftItem> CYBER_LIMB_LEG_RIGHT = ITEMS.register("cyberlimbs_cyberleg_right",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.RIGHT, CybercraftAPI.QUALITY_MANUFACTURED)
                    .setEssenceCost(15));
    public static final RegistryObject<CybercraftItem> CYBER_LIMB_LEG_RIGHT_SCAVENGED = ITEMS.register("cyberlimbs_cyberleg_right_scavenged",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG, ICybercraft.ISidedLimb.EnumSide.RIGHT, CybercraftAPI.QUALITY_SCAVENGED)
                    .setEssenceCost(15));

    //Battery
    public static final RegistryObject<CybercraftItem> CREATIVE_BATTERY = ITEMS.register("creative_battery",
            () -> new CreativeBatteryItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS)
                    .setEssenceCost(0));

    public static final RegistryObject<CybercraftItem> DENSE_BATTERY = ITEMS.register("dense_battery",
            () -> new DenseBatteryItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS)
                    .setEssenceCost(15)
                    .setWeight(RARE));

    public static final RegistryObject<Item> DENSE_BATTERY_SCAVENGED = ITEMS.register("dense_battery_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));


    public static final RegistryObject<Item> NEUROPOZYNE = ITEMS.register("neuropozyne",
            () -> new NeuropozyneItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

    public static final RegistryObject<Item> BLUEPRINT = ITEMS.register("blueprint",
            () -> new BlueprintItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB).stacksTo(1)));


    //Entities
    public static final RegistryObject<Item> CYBER_ZOMBIE_SPAWN_EGG = ITEMS.register("cyber_zombie_sparn_egg",
            ()-> new CybercraftSpawnEgg(EntityTypeInit.CYBER_ZOMBIE, 44975, 7969893, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

}
