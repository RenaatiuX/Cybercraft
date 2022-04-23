package com.rena.cyberware.common.item;

import com.rena.cyberware.Cybercraft;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum CybercraftArmorMaterial implements IArmorMaterial {

    SHADES("vanity", 5, new int[]{1, 2, 3, 1}, 15,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,() -> {
        return Ingredient.of(Blocks.GLASS);
    }),
    SHADES2("vanity_2", 5, new int[]{1, 2, 3, 1}, 15,
    SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F,() -> {
        return Ingredient.of(Blocks.GLASS);
    }),
    JACKET("jacket", 5, new int[]{1, 2, 3, 1}, 15,
            SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,() -> {
        return Ingredient.of(Items.LEATHER);
    }),
    TRENCHCOAT("trenchcoat", 5, new int[]{1, 2, 3, 1}, 15,
            SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,() -> {
        return Ingredient.of(Items.LEATHER);
    });

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    CybercraftArmorMaterial(String p_i231593_3_, int p_i231593_4_, int[] p_i231593_5_, int p_i231593_6_, SoundEvent p_i231593_7_, float p_i231593_8_, float p_i231593_9_, Supplier<Ingredient> p_i231593_10_) {
        this.name = p_i231593_3_;
        this.durabilityMultiplier = p_i231593_4_;
        this.slotProtections = p_i231593_5_;
        this.enchantmentValue = p_i231593_6_;
        this.sound = p_i231593_7_;
        this.toughness = p_i231593_8_;
        this.knockbackResistance = p_i231593_9_;
        this.repairIngredient = new LazyValue<>(p_i231593_10_);
    }

    public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
        return HEALTH_PER_SLOT[p_200896_1_.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlotType p_200902_1_) {
        return this.slotProtections[p_200902_1_.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return Cybercraft.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
