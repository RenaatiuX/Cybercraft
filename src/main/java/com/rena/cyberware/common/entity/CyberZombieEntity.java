package com.rena.cyberware.common.entity;

import com.rena.cyberware.api.CybercraftAPI;
import com.rena.cyberware.api.CybercraftUserDataImpl;
import com.rena.cyberware.common.config.CybercraftConfig;
import com.rena.cyberware.core.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        entityData.set(CYBER_VARIANT, 0);
    }

   /* @Override
    public void tick() {
        if ( !hasRandomWare
                && !level.isClientSide )
        {
            if ( !isBrute()
                    && level.random.nextFloat() < (LibConstants.NATURAL_BRUTE_CHANCE / 100F) )
            {
                setBrute();
            }
            CyberwareDataHandler.addRandomCyberware(this, isBrute());
            if (isBrute())
            {
                this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Brute Bonus", 6D, 0));
                this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier("Brute Bonus", 1D, 0));
            }
            setHealth(getMaxHealth());
            hasRandomWare = true;
        }
        if ( isBrute()
                && height != (1.95F * 1.2F) )
        {
            setSizeNormal(0.6F * 1.2F, 1.95F * 1.2F);
        }
        super.tick();
    }*/

   /* protected void setSizeNormal(float width, float height)
    {
        if ( width != this.width
                || height != this.height )
        {
            float widthPrevious = this.width;
            this.width = width;
            this.height = height;
            AxisAlignedBB axisalignedbb = getEntityBoundingBox();
            setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ,
                    axisalignedbb.minX + width, axisalignedbb.minY + height, axisalignedbb.minZ + width ));

            if ( this.width > widthPrevious
                    && !firstUpdate
                    && !level.isClientSide )
            {
                move(MoverType.SELF, this.getDeltaMovement());
            }
        }
    }*/

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
