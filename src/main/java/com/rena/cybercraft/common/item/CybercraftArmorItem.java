package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.IDeconstructable;
import com.rena.cybercraft.client.ClientUtils;
import com.rena.cybercraft.common.util.CybercraftArmorMaterial;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.rena.cybercraft.api.CybercraftAPI.withMetaData;


public class CybercraftArmorItem extends ArmorItem implements IDeconstructable, IDyeableArmorItem {

    public CybercraftArmorItem(IArmorMaterial material, EquipmentSlotType equipment, Properties properties) {
        super(material, equipment, properties);
    }


    @Override
    public boolean canDestroy(ItemStack stack) {
        return true;
    }

    public static NonNullList<ItemStack> getComponents(ItemStack blueprintItem) {
        return NonNullList.create();
    }

    /*@Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        if ( !itemStack.isEmpty()
                && itemStack.getItem() == ItemInit.TRENCHCOAT.get())
        {
            ClientUtils.TRENCH_COAT.setDefaultModel(_default);
            return ClientUtils.TRENCH_COAT;

        }

        return null;
    }*/

    @Override
    public boolean hasCustomColor(@Nonnull ItemStack stack)
    {
        if (getMaterial() != CybercraftArmorMaterial.TRENCHCOAT)
        {
            return false;
        }

        CompoundNBT tagCompound = stack.getTag();
        return tagCompound != null
                && tagCompound.contains("display", 10)
                && tagCompound.getCompound("display").contains("color", 3);
    }

    @Override
    public int getColor(@Nonnull ItemStack stack)
    {
        if (getMaterial() != CybercraftArmorMaterial.TRENCHCOAT)
        {
            return 16777215;
        }
        else
        {
            CompoundNBT tagCompound = stack.getTag();

            if (tagCompound != null)
            {
                CompoundNBT tagCompoundDisplay = tagCompound.getCompound("display");

                if (tagCompoundDisplay.contains("color", 3))
                {
                    return tagCompoundDisplay.getInt("color");
                }
            }

            return 0x333333; // 0x664028
        }
    }

    @Override
    public void clearColor(@Nonnull ItemStack stack)
    {
        if (getMaterial() != CybercraftArmorMaterial.TRENCHCOAT)
        {
            CompoundNBT tagCompound = stack.getTag();

            if (tagCompound != null)
            {
                CompoundNBT tagCompoundDisplay = tagCompound.getCompound("display");

                if (tagCompoundDisplay.contains("color"))
                {
                    tagCompoundDisplay.remove("color");
                }
            }
        }
    }

    @Override
    public void setColor(ItemStack stack, int color)
    {
        if (getMaterial() != CybercraftArmorMaterial.TRENCHCOAT)
        {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        else
        {
            CompoundNBT tagCompound = stack.getTag();

            if (tagCompound == null)
            {
                tagCompound = new CompoundNBT();
                stack.setTag(tagCompound);
            }

            CompoundNBT tagCompoundDisplay = tagCompound.getCompound("display");

            if (!tagCompound.contains("display", 10))
            {
                tagCompound.put("display", tagCompoundDisplay);
            }

            tagCompoundDisplay.putInt("color", color);
        }
    }

    @Override
    public void fillItemCategory(ItemGroup itemGroup, NonNullList<ItemStack> itemStack) {
        if(allowdedIn(itemGroup)){
            if(getMaterial() == CybercraftArmorMaterial.TRENCHCOAT)
            {
                super.fillItemCategory(itemGroup, itemStack);
                ItemStack brown = new ItemStack(this);
                setColor(brown, 0x664028);
                itemStack.add(brown);
                ItemStack white = new ItemStack(this);
                setColor(white, 0xEAEAEA);
                itemStack.add(white);
            }
            else {
                super.fillItemCategory(itemGroup, itemStack);
            }
        }
    }
}
