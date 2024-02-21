package com.rena.cybercraft.common.entity;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.AddRandomCyberwareEvent;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.CybercraftUserDataImpl;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.Tags;
import com.rena.cybercraft.core.init.ItemInit;
import com.rena.cybercraft.datagen.ModItemTagsProvider;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CyberZombieEntity extends ZombieEntity {

    private static final DataParameter<Integer> CYBER_VARIANT = EntityDataManager.defineId(CyberZombieEntity.class, DataSerializers.INT);

    public boolean hasRandomWare;
    private CybercraftUserDataImpl cybercraft;

    public CyberZombieEntity(EntityType<CyberZombieEntity> cyberZombie, World world) {
        super(cyberZombie, world);
        cybercraft = new CybercraftUserDataImpl();
        hasRandomWare = false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CYBER_VARIANT, 0);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!hasRandomWare && !level.isClientSide) {
            if (!isBrute() && level.random.nextFloat() < (LibConstants.NATURAL_BRUTE_CHANCE / 100F)) {
                setBrute();
            }
            if (!hasRandomWare)
                this.hasRandomWare = MinecraftForge.EVENT_BUS.post(new AddRandomCyberwareEvent(this, isBrute()));
            if (isBrute()) {
                this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Brute Bonus", 6D, AttributeModifier.Operation.ADDITION));
                this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier("Brute Bonus", 1D, AttributeModifier.Operation.ADDITION));
            }
            setHealth(getMaxHealth());
            hasRandomWare = true;
        }
    }

    @Override
    public EntitySize getDimensions(Pose pose) {
        return isBrute() && getEyeHeight() != (1.95F * 1.2F) ? super.getDimensions(pose).scale(1.2f) : super.getDimensions(pose);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tagCompound) {
        super.addAdditionalSaveData(tagCompound);

        tagCompound.putBoolean("hasRandomWare", hasRandomWare);
        tagCompound.putBoolean("brute", isBrute());

        if (hasRandomWare) {

            CompoundNBT tagCompoundCybercraft = cybercraft.serializeNBT();
            tagCompound.put("ware", tagCompoundCybercraft);

        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tagCompound) {
        super.readAdditionalSaveData(tagCompound);

        boolean brute = tagCompound.getBoolean("brute");
        if (brute) {
            setBrute();
        }
        hasRandomWare = tagCompound.getBoolean("hasRandomWare");
        if (tagCompound.contains("ware")) {
            cybercraft.deserializeNBT(tagCompound.getCompound("ware"));
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CybercraftAPI.CYBERCRAFT_CAPABILITY) {
            return LazyOptional.of(() -> cybercraft).cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int lootingModifier, boolean wasRecentlyHit) {
        super.dropCustomDeathLoot(damageSource, lootingModifier, wasRecentlyHit);

        /*if (CybercraftConfig.C_OTHER.enableKatana.get() && CybercraftConfig.C_MOBS.addClothes.get()
                && !getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty() && getItemBySlot(EquipmentSlotType.MAINHAND).getItem() == ItemInit.KATANA.get()) {
            ItemStack itemstack = getItemBySlot(EquipmentSlotType.MAINHAND).copy();
            if (itemstack.isDamageableItem()) {
                int i = Math.max(itemstack.getMaxDamage() - 25, 1);
                int j = itemstack.getMaxDamage() - random.nextInt(random.nextInt(i) + 1);
                if (j > i) {
                    j = i;
                }
                if (j < 1) {
                    j = 1;
                }
                itemstack.setDamageValue(j);
            }
            spawnAtLocation(itemstack, 0.0F);
        }

        if (hasRandomWare) {
            float rarity = (float) Math.min(100.0F, CybercraftConfig.C_MOBS.cyberZombieDropRarity.get() + lootingModifier * 5.0F);
            if (level.random.nextFloat() < (rarity / 100.0F)) {
                List<ItemStack> allWares = new ArrayList<>();
                for (ICybercraft.EnumSlot slot : ICybercraft.EnumSlot.values()) {
                    NonNullList<ItemStack> nnlInstalled = cybercraft.getInstalledCybercraft(slot);
                    for (ItemStack stack : nnlInstalled) {
                        if (!stack.isEmpty()) {
                            allWares.add(stack);
                        }
                    }
                }
                allWares.removeAll(Collections.singleton(ItemStack.EMPTY));
                // Sanity check for corrupted NBT
                if (allWares.size() == 0) {
                    Cybercraft.LOGGER.error(String.format("Invalid cyberzombie with hasRandomWare %s with actually no implants: %s",
                            hasRandomWare, this));
                    return;
                }
                ItemStack drop = ItemStack.EMPTY;
                int count = 0;
                while (count < 50
                        && (drop.isEmpty()
                        || drop.getItem() == ItemInit.CREATIVE_BATTERY.get()
                        || Tags.Items.BODY_PARTS.contains(drop.getItem()))) {
                    int random = level.random.nextInt(allWares.size());
                    drop = allWares.get(random).copy();
                    drop = CybercraftAPI.sanitize(drop);
                    drop = CybercraftAPI.getCybercraft(drop).setQuality(drop, CybercraftAPI.QUALITY_SCAVENGED);
                    drop.setCount(1);
                    count++;
                }
                if (count < 50) {
                    spawnAtLocation(drop, 0.0F);
                }
            }
        }*/
    }

    @Override
    protected void populateDefaultEquipmentSlots(@Nonnull DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(difficulty);

        if (CybercraftConfig.C_OTHER.enableKatana.get() && CybercraftConfig.C_MOBS.addClothes.get()
                && !getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()
                && getItemBySlot(EquipmentSlotType.MAINHAND).getItem() == Items.IRON_SWORD) {
            setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.KATANA.get()));
            setDropChance(EquipmentSlotType.MAINHAND, 0F);
        }
    }

    public boolean isBrute() {
        return entityData.get(CYBER_VARIANT) == 1;
    }

    public boolean setBrute() {
        setBaby(false);
        entityData.set(CYBER_VARIANT, 1);

        return !hasRandomWare;
    }

}
