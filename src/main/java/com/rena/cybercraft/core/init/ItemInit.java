package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.item.*;
import com.rena.cybercraft.common.util.CybercraftArmorMaterial;
import com.rena.cybercraft.common.util.CybercraftItemTier;
import com.rena.cybercraft.common.util.NNLUtil;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.rena.cybercraft.api.CybercraftAPI.withMetaData;

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

    //Component
    public static final RegistryObject<Item> COMPONENT = ITEMS.register("component",
            () -> new CybercraftBaseItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), "actuator", "reactor", "titanium", "ssc", "plating", "fiberoptics", "fullerene", "synthnerves", "storage", "microelectric"));

    //Weapon
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana",
            () -> new SwordCybercraftItem(CybercraftItemTier.KATANA, 4, -2.5F, new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

    //Body Part
    public static final RegistryObject<Item> BODY_PART = ITEMS.register("body_part",
            () -> new BodyPartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB),
                    new ICybercraft.EnumSlot[]{ICybercraft.EnumSlot.EYES, ICybercraft.EnumSlot.CRANIUM, ICybercraft.EnumSlot.HEART, ICybercraft.EnumSlot.LUNGS, ICybercraft.EnumSlot.LOWER_ORGANS, ICybercraft.EnumSlot.SKIN, ICybercraft.EnumSlot.MUSCLE, ICybercraft.EnumSlot.BONE, ICybercraft.EnumSlot.ARM, ICybercraft.EnumSlot.ARM, ICybercraft.EnumSlot.LEG, ICybercraft.EnumSlot.LEG},
                    "eyes", "brain", "heart", "lungs", "stomach", "skin", "muscles", "bones", "arm_left", "arm_right", "leg_left", "leg_right"));


    //Eye
    /*public static final RegistryObject<Item> EYES = ITEMS.register("body_part_eyes",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    public static final RegistryObject<CybercraftItem> CYBER_EYES = ITEMS.register("cybereyes",
            () -> new CybereyesItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES)
                    .setEssenceCost(8) // 0.2.0 Changed from 10
                    .setWeights(UNCOMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5),
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 7)})));

    /*public static final RegistryObject<Item> EYES_UPGRADES_SCAVENGED = ITEMS.register("eye_upgrades_hudlens_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_HUDJACK = ITEMS.register("cybereye_upgrades_hudjack",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_HUDJACK_SCAVENGED = ITEMS.register("cybereye_upgrades_hudjack_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_NIGHT_VISION = ITEMS.register("cybereye_upgrades_night_vision",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_NIGHT_VISION_SCAVENGED = ITEMS.register("cybereye_upgrades_night_vision_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    public static final RegistryObject<CybercraftItem> CYBER_EYE_UPGRADES = ITEMS.register("cybereye_upgrades",
            () -> new CybereyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES,
                    "night_vision", "underwater_vision", "hudjack", "targeting", "zoom")
                    .setEssenceCost(2, 2, 1, 1, 1)
                    .setWeights(UNCOMMON, UNCOMMON, UNCOMMON, UNCOMMON, UNCOMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 7)})));

    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_TARGETING_SCAVENGED = ITEMS.register("cybereye_upgrades_targeting_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_UNDERWATER_VISION = ITEMS.register("cybereye_upgrades_underwater_vision",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_UNDERWATER_VISION_SCAVENGED = ITEMS.register("cybereye_upgrades_underwater_vision_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_ZOOM = ITEMS.register("cybereye_upgrades_zoom",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> CYBER_EYE_UPGRADES_ZOOM_SCAVENGED = ITEMS.register("cybereye_upgrades_zoom_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_EYES = ITEMS.register("cybereyes",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    public static final RegistryObject<CybercraftItem> EYE_UPGRADES = ITEMS.register("eye_upgrades",
            () -> new EyeUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.EYES, "hudlens")
                    .setEssenceCost(1)
                    .setWeights(VERY_COMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 7)})));

    //Brain
    public static final RegistryObject<CybercraftItem> BRAIN_UPGRADES = ITEMS.register("brain_upgrades",
            () -> new BrainUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.CRANIUM,
                    "cortical_stack", "ender_jammer", "consciousness_transmitter", "neural_contextualizer", "matrix", "radio")
                    .setEssenceCost(3, 10, 2, 2, 8, 2)
                    .setWeights(RARE, UNCOMMON, UNCOMMON, COMMON, UNCOMMON, UNCOMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)})));

    /*public static final RegistryObject<Item> BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER = ITEMS.register("brain_upgrades_consciousness_transmitter",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_CONSCIOUSNESS_TRANSMITTER_SCAVENGED = ITEMS.register("brain_upgrades_consciousness_transmitter_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_CORTICAL_STACK = ITEMS.register("brain_upgrades_cortical_stack",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_CORTICAL_STACK_SCAVENGED = ITEMS.register("brain_upgrades_cortical_stack_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_MATRIX = ITEMS.register("brain_upgrades_matrix",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_MATRIX_SCAVENGED = ITEMS.register("brain_upgrades_matrix_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER = ITEMS.register("brain_upgrades_neural_contextualizer",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_NEURAL_CONTEXTUALIZER_SCAVENGED = ITEMS.register("brain_upgrades_neural_contextualizer_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_RADIO = ITEMS.register("brain_upgrades_radio",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BRAIN_UPGRADES_RADIO_SCAVENGED = ITEMS.register("brain_upgrades_radio_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Capsule
    public static final RegistryObject<Item> EXP_CAPSULE = ITEMS.register("exp_capsule",
            () -> new ExpCapsuleItem(new Item.Properties().stacksTo(1).tab(Cybercraft.CYBERCRAFTAB)));

    //Heart
    public static final RegistryObject<Item> CYBER_HEART = ITEMS.register("cyberheart",
            () -> new CyberHeartItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART)
                    .setEssenceCost(5)
                    .setWeights(COMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 0),
                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)})));

    public static final RegistryObject<CybercraftItem> HEART_UPGRADES = ITEMS.register("heart_upgrades",
            () -> new HeartUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HEART,
                    "defibrillator", "platelets", "medkit", "coupler")
                    .setEssenceCost(10, 5, 15, 10)
                    .setWeights(COMMON, UNCOMMON, UNCOMMON, VERY_COMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 8)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 9)})));
    /*public static final RegistryObject<Item> CYBER_HEART_SCAVENGED = ITEMS.register("cyberheart_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> HEART_UPGRADES_COUPLER = ITEMS.register("heart_upgrades_coupler",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> HEART_UPGRADES_COUPLER_SCAVENGED = ITEMS.register("heart_upgrades_coupler_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> HEART_UPGRADES_DEFIBRILLATOR = ITEMS.register("heart_upgrades_defibrillator",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> HEART_UPGRADES_DEFIBRILLATOR_SCAVENGED = ITEMS.register("heart_upgrades_defibrillator_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> HEART_MEDKIT = ITEMS.register("heart_upgrades_medkit",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> HEART_MEDKIT_SCAVENGED = ITEMS.register("heart_upgrades_medkit_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> HEART_PLATELETS = ITEMS.register("heart_upgrades_platelets",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> HEART_PLATELETS_SCAVENGED = ITEMS.register("heart_upgrades_platelets_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Lungs
    public static final RegistryObject<CybercraftItem> LUNGS_UPGRADES = ITEMS.register("lungs_upgrades",
            () -> new LungsUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LUNGS,
                    "oxygen", "hyperoxygenation")
                    .setEssenceCost(15, 2)
                    .setWeights(UNCOMMON, COMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)})));

    /*public static final RegistryObject<Item> LUNGS_HYPEROXYGENATION = ITEMS.register("lungs_upgrades_hyperoxygenation",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LUNGS_HYPEROXYGENATION_SCAVENGED = ITEMS.register("lungs_upgrades_hyperoxygenation_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LUNGS_OXYGEN = ITEMS.register("lungs_upgrades_oxygen",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LUNGS_OXYGEN_SCAVENGED = ITEMS.register("lungs_upgrades_oxygen_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    //Organs
    public static final RegistryObject<CybercraftItem> LOWER_ORGANS_UPGRADES = ITEMS.register("lower_organs_upgrades",
            () -> new LowerOrgansUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS,
                    "liver_filter", "metabolic", "battery", "adrenaline")
                    .setEssenceCost(5, 5, 10, 5)
                    .setWeights(UNCOMMON, COMMON, VERY_COMMON, UNCOMMON)
                    .setComponents(NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 1)})));

    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_ADRENALINE = ITEMS.register("lower_organs_upgrades_adrenaline",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_ADRENALINE_SCAVENGED = ITEMS.register("lower_organs_upgrades_adrenaline_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_BATTERY = ITEMS.register("lower_organs_upgrades_battery",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_BATTERY_SCAVENGED = ITEMS.register("lower_organs_upgrades_battery_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_LIVER = ITEMS.register("lower_organs_upgrades_liver_filter",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_LIVER_SCAVENGED = ITEMS.register("lower_organs_upgrades_liver_filter_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_METABOLIC = ITEMS.register("lower_organs_upgrades_metabolic",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LOWER_ORGANS_UPGRADES_METABOLIC_SCAVENGED = ITEMS.register("lower_organs_upgrades_metabolic_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/


    //Skin
    public static final RegistryObject<CybercraftItem> SKIN_UPGRADES = ITEMS.register("skin_upgrades",
            () -> new SkinUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.SKIN,
                    "solar_skin", "subdermal_spikes", "fake_skin", "immuno")
                    .setEssenceCost(12, 12, 0, -25)
                    .setWeights(VERY_COMMON, UNCOMMON, UNCOMMON, RARE)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 8),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)})));

    /*public static final RegistryObject<Item> SKIN_FAKE = ITEMS.register("skin_upgrades_fake_skin",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> SKIN_FAKE_SCAVENGED = ITEMS.register("skin_upgrades_fake_skin_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> SKIN_IMMUNO = ITEMS.register("skin_upgrades_immuno",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> SKIN_IMMUNO_SCAVENGED = ITEMS.register("skin_upgrades_immuno_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> SKIN_SOLAR = ITEMS.register("skin_upgrades_solar_skin",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> SKIN_SOLAR_SCAVENGED = ITEMS.register("skin_upgrades_solar_skin_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> SKIN_SUBDERMAL = ITEMS.register("skin_upgrades_subdermal_spikes",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> SKIN_SUBDERMAL_SCAVENGED = ITEMS.register("skin_upgrades_subdermal_spikes_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Muscle
    public static final RegistryObject<CybercraftItem> MUSCLE_UPGRADES = ITEMS.register("muscle_upgrades",
            () -> new MuscleUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.MUSCLE,
                    "wired_reflexes", "muscle_replacements")
                    .setEssenceCost(5, 15)
                    .setWeights(UNCOMMON, RARE)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 5)})));

    /*public static final RegistryObject<Item> MUSCLE_REPLACEMENTS = ITEMS.register("muscle_upgrades_muscle_replacements",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> MUSCLE_REPLACEMENTS_SCAVENGED = ITEMS.register("muscle_upgrades_muscle_replacements_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> MUSCLE_REFLEXES = ITEMS.register("muscle_upgrades_wired_reflexes",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> MUSCLE_REFLEXES_SCAVENGED = ITEMS.register("muscle_upgrades_wired_reflexes_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Stomach
    /*public static final RegistryObject<Item> STOMACH = ITEMS.register("body_part_stomach",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Arm
    public static final RegistryObject<CybercraftItem> ARM_UPGRADES = ITEMS.register("arm_upgrades",
            () -> new ArmUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.ARM,
                    "bow")
                    .setEssenceCost(3)
                    .setWeights(RARE)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 4)})));

    /*public static final RegistryObject<Item> ARM_RIGHT = ITEMS.register("body_part_arm_right",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> ARM_UPGRADES_BOW = ITEMS.register("arm_upgrades_bow",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> ARM_UPGRADES_BOW_SCAVENGED = ITEMS.register("arm_upgrades_bow_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/


    //Bone
    public static final RegistryObject<CybercraftItem> BONE_UPGRADES = ITEMS.register("bone_upgrades",
            () -> new BoneUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.BONE,
                    "bonelacing", "boneflex", "bonebattery")
                    .setEssenceCost(3, 5)
                    .setWeights(UNCOMMON, RARE, UNCOMMON)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 6)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 1),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)})));
    /*public static final RegistryObject<Item> BONES_UPGRADES_BATTERY = ITEMS.register("bone_upgrades_bonebattery",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BONES_UPGRADES_BATTERY_SCAVENGED = ITEMS.register("bone_upgrades_bonebattery_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BONES_UPGRADES_BONEFLEX = ITEMS.register("bone_upgrades_boneflex",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BONES_UPGRADES_BONEFLEX_SCAVENGED = ITEMS.register("bone_upgrades_boneflex_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> BONES_UPGRADES_BONELACING = ITEMS.register("bone_upgrades_bonelacing",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> BONES_UPGRADES_BONELACING_SCAVENGED = ITEMS.register("bone_upgrades_bonelacing_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/


    //Hand
    public static final RegistryObject<CybercraftItem> HAND_UPGRADES = ITEMS.register("hand_upgrades",
            () -> new HandUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.HAND,
                    "craft_hands", "claws", "mining")
                    .setEssenceCost(2, 2, 1)
                    .setWeights(RARE, RARE, RARE)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 3),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 6),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 8)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 6)})));

    /*public static final RegistryObject<Item> HAND_UPGRADES_CLAWS_SCAVENGED = ITEMS.register("hand_upgrades_claws_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> HAND_UPGRADES_CRAFT = ITEMS.register("hand_upgrades_craft_hands",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> HAND_UPGRADES_CRAFT_SCAVENGED = ITEMS.register("hand_upgrades_craft_hands_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> HAND_UPGRADES_MINING = ITEMS.register("hand_upgrades_mining",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> HAND_UPGRADES_MINING_SCAVENGED = ITEMS.register("hand_upgrades_mining_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Legs
    public static final RegistryObject<CybercraftItem> LEG_UPGRADES = ITEMS.register("leg_upgrades",
            () -> new LegUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LEG,
                    "jump_boost", "fall_damage")
                    .setEssenceCost(3, 2)
                    .setWeights(RARE, RARE)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5)})));

    /*public static final RegistryObject<Item> LEG_RIGHT = ITEMS.register("body_part_leg_right",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LEG_FALL_DAMAGE = ITEMS.register("leg_upgrades_fall_damage",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LEG_FALL_DAMAGE_SCAVENGED = ITEMS.register("leg_upgrades_fall_damage_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LEG_JUMP_BOOST = ITEMS.register("leg_upgrades_jump_boost",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> LEG_JUMP_BOOST_SCAVENGED = ITEMS.register("leg_upgrades_jump_boost_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/


    //Foot
    /*public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_AQUA = ITEMS.register("foot_upgrades_aqua",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    /*public static final RegistryObject<Item> FOOT_UPGRADES_AQUA_SCAVENGED = ITEMS.register("foot_upgrades_aqua_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<CybercraftItem> FOOT_UPGRADES_SPURS = ITEMS.register("foot_upgrades_spurs",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> FOOT_UPGRADES_SPURS_SCAVENGED = ITEMS.register("foot_upgrades_spurs_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    public static final RegistryObject<CybercraftItem> FOOT_UPGRADES = ITEMS.register("foot_upgrades",
            () -> new FootUpgradeItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.FOOT, "spurs", "aqua", "wheels")
                    .setEssenceCost(1, 2, 3).setWeights(UNCOMMON, RARE, UNCOMMON)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 9)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 9)})));

    /*public static final RegistryObject<Item> FOOT_UPGRADES_WHEELS_SCAVENGED = ITEMS.register("foot_upgrades_wheels_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Limbs
    /*public static final RegistryObject<Item> CYBER_LEG_LEFT = ITEMS.register("cyberlimbs_cyberleg_left",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_LEG_LEFT_SCAVENGED = ITEMS.register("cyberlimbs_cyberleg_left_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_LEG_RIGHT = ITEMS.register("cyberlimbs_cyberleg_right",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> CYBER_LEG_RIGHT_SCAVENGED = ITEMS.register("cyberlimbs_cyberleg_right_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    public static final RegistryObject<CybercraftItem> CYBER_LIMBS = ITEMS.register("cyberlimbs",
            () -> new CyberLimbItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), new ICybercraft.EnumSlot[]{ICybercraft.EnumSlot.ARM, ICybercraft.EnumSlot.ARM, ICybercraft.EnumSlot.LEG, ICybercraft.EnumSlot.LEG},
                    "cyberarm_left", "cyberarm_right", "cyberleg_left", "cyberleg_right")
                    .setEssenceCost(15, 15, 15, 15)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)}),
                            NNLUtil.fromArray(new ItemStack[]{withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 0),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 4),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 5),
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 7)})));
    /*public static final RegistryObject<Item> LIMB_LEFT_SCAVENGED = ITEMS.register("cyberlimbs_cyberarm_left_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LIMB_RIGHT = ITEMS.register("cyberlimbs_cyberarm_right",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/
    /*public static final RegistryObject<Item> LIMB_RIGHT_SCAVENGED = ITEMS.register("cyberlimbs_cyberarm_right_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    //Battery
    public static final RegistryObject<CybercraftItem> CREATIVE_BATTERY = ITEMS.register("creative_battery",
            () -> new CreativeBatteryItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS)
                    .setEssenceCost(0));

    public static final RegistryObject<CybercraftItem> DENSE_BATTERY = ITEMS.register("dense_battery",
            () -> new DenseBatteryItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB), ICybercraft.EnumSlot.LOWER_ORGANS)
                    .setEssenceCost(15)
                    .setWeights(RARE)
                    .setComponents(
                            NNLUtil.fromArray(new ItemStack[]{
                                    withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 3), 6),
                                            withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 4), 9)})));

    /*public static final RegistryObject<Item> DENSE_BATTERY_SCAVENGED = ITEMS.register("dense_battery_scavenged",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/

    /*public static final RegistryObject<Item> COMPONENT_FIBER_OPTICS = ITEMS.register("component_fiberoptics",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_FULLERENE = ITEMS.register("component_fullerene",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_MICRO_ELECTRIC = ITEMS.register("component_microelectric",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));
    public static final RegistryObject<Item> COMPONENT_PLANTING = ITEMS.register("component_plating",
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
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));*/


    public static final RegistryObject<Item> NEUROPOZYNE = ITEMS.register("neuropozyne",
            () -> new NeuropozyneItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

    public static final RegistryObject<Item> BLUEPRINT = ITEMS.register("blueprint",
            () -> new BlueprintItem(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB).stacksTo(1)));


    //Table
    public static final RegistryObject<Item> ENGINEERING_TABLE = ITEMS.register("engineering_table",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

    public static final RegistryObject<Item> SURGERY = ITEMS.register("surgery_chamber",
            () -> new Item(new Item.Properties().tab(Cybercraft.CYBERCRAFTAB)));

}
