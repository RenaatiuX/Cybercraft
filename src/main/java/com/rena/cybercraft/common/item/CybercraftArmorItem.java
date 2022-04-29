package com.rena.cybercraft.common.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.IDeconstructable;
import com.rena.cybercraft.common.util.CybercraftArmorMaterial;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

import static com.rena.cybercraft.api.CybercraftAPI.withMetaData;


public class CybercraftArmorItem extends ArmorItem implements IDeconstructable, IDyeableArmorItem {

    public CybercraftArmorItem(IArmorMaterial material, EquipmentSlotType equipment, Properties properties) {
        super(material, equipment, properties);
    }


    @Override
    public boolean canDestroy(ItemStack stack) {
        return true;
    }

    @Override
    public NonNullList<ItemStack> getComponents(ItemStack stack) {
        Item item = stack.getItem();

        NonNullList<ItemStack> nnl = NonNullList.create();
        if (item == ItemInit.TRENCHCOAT.get())
        {
            nnl.add(withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 2), 2));
            nnl.add(withMetaData(new ItemStack(Items.LEATHER, 12), 0));
            //nnl.add(new ItemStack(Items.DYE, 1, 0));
        }
        else if (item == ItemInit.JACKET.get())
        {
            nnl.add(withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 2));
            nnl.add(withMetaData(new ItemStack(Items.LEATHER, 8), 0));
            //nnl.add(new ItemStack(Items.DYE, 1, 0));
        }
        else
        {
            //nnl.add(new ItemStack(Blocks.STAINED_GLASS, 4, 15));
            nnl.add(withMetaData(new ItemStack(ItemInit.COMPONENT.get(), 1), 4));
        }
        return nnl;
    }

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
