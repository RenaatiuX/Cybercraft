package com.rena.cybercraft.common.entity;

import com.rena.cybercraft.api.AddRandomCyberwareEvent;
import com.rena.cybercraft.api.CybercraftUserDataImpl;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.util.LibConstants;
import com.rena.cybercraft.core.init.ItemInit;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

public class CyberZombieEntity extends ZombieEntity {

    private static final DataParameter<Integer> CYBER_VARIANT = EntityDataManager.defineId(CyberZombieEntity.class, DataSerializers.INT);

    public boolean hasRandomWare;
    private CybercraftUserDataImpl cybercraft;

    public CyberZombieEntity(EntityType<? extends ZombieEntity> cyberZombie, World world) {
        super(cyberZombie, world);
        cybercraft = new CybercraftUserDataImpl();
        hasRandomWare = false;
    }

    @Override
    protected void defineSynchedData()
    {
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
    public void tick() {
        if ( !hasRandomWare && !level.isClientSide ) {
            if ( !isBrute() && level.random.nextFloat() < (LibConstants.NATURAL_BRUTE_CHANCE / 100F) ) {
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

        super.tick();
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

        if(hasRandomWare){

            CompoundNBT tagCompoundCybercraft = cybercraft.serializeNBT();
            tagCompound.put("ware", tagCompoundCybercraft);

        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tagCompound) {
        super.readAdditionalSaveData(tagCompound);

        boolean brute = tagCompound.getBoolean("brute");
        if(brute)
        {
            setBrute();
        }
        hasRandomWare = tagCompound.getBoolean("hasRandomWare");
        if(tagCompound.contains("ware"))
        {
            cybercraft.deserializeNBT(tagCompound.getCompound("ware"));
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(@Nonnull DifficultyInstance difficulty)
    {
        super.populateDefaultEquipmentSlots(difficulty);

        if ( CybercraftConfig.C_OTHER.enableKatana.get() && CybercraftConfig.C_MOBS.addClothes.get()
                && !getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()
                && getItemBySlot(EquipmentSlotType.MAINHAND).getItem() == Items.IRON_SWORD )
        {
            setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ItemInit.KATANA.get()));
            setDropChance(EquipmentSlotType.MAINHAND, 0F);
        }
    }

    public boolean isBrute()
    {
        return entityData.get(CYBER_VARIANT) == 1;
    }

    public boolean setBrute()
    {
        setBaby(false);
        entityData.set(CYBER_VARIANT, 1);

        return !hasRandomWare;
    }

}
