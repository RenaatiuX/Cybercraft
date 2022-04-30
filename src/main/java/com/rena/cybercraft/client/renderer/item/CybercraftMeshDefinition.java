package com.rena.cybercraft.client.renderer.item;

import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.common.item.CybercraftItem;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ItemModelMesherForge;

import javax.annotation.Nonnull;

public class CybercraftMeshDefinition extends ItemModelMesherForge {
    public CybercraftMeshDefinition(ModelManager manager) {
        super(manager);
    }

    /*
    @Override
    public ModelResourceLocation getLocation(@Nonnull ItemStack stack) {
        ItemStack test = stack.copy();
        if (!test.isEmpty() && test.hasTag())
        {
            test.getTag().remove(CybercraftAPI.QUALITY_TAG);
        }

        CybercraftItem ware = (CybercraftItem) stack.getItem();
        String added = "";
        if (ware.subnames.length > 0)
        {
            int i = Math.min(ware.subnames.length - 1, stack.getDamageValue());
            added = "_" + ware.subnames[i];
        }

        ICybercraft.Quality q = CybercraftAPI.getCybercraft(stack).getQuality(stack);

        if (q != null && CybercraftAPI.getCybercraft(test).getQuality(test) != q && q.getSpriteSuffix() != null)
        {
            return new ModelResourceLocation(ware.getRegistryName() + added + "_" + q.getSpriteSuffix(), "inventory");
        }
        else
        {
            return new ModelResourceLocation(ware.getRegistryName() + added, "inventory");
        }
    }
    */
}
