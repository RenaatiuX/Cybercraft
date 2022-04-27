package com.rena.cybercraft.client.events;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.item.CybercraftArmorItem;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Cybercraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

        if(CybercraftConfig.C_OTHER.enableClothes.get()){
            Minecraft.getInstance().getItemColors().register(new IItemColor()
            {
                @Override
                public int getColor(ItemStack stack, int tintIndex) {
                    return tintIndex > 0 ? -1 : ((CybercraftArmorItem)stack.getItem()).getColor(stack);
                }

            }, ItemInit.TRENCHCOAT.get());
        }

    }

    /*private void registerRenders(Item item)
    {
        if (item instanceof CybercraftItem)
        {
            CybercraftItem ware = (CybercraftItem) item;
            List<ModelResourceLocation> models = new ArrayList<>();
            if (ware.subnames.length > 0)
            {
                for (int indexSubname = 0; indexSubname < ware.subnames.length; indexSubname++)
                {
                    String name = ware.getRegistryName() + "_" + ware.subnames[indexSubname];
                    for (ICybercraft.Quality quality : ICybercraft.Quality.qualities)
                    {
                        if ( quality.getSpriteSuffix() != null
                                && ware.canHoldQuality(new ItemStack(ware, 1, indexSubname), quality) )
                        {
                            models.add(new ModelResourceLocation(name + "_" + quality.getSpriteSuffix(), "inventory"));
                        }
                    }
                    models.add(new ModelResourceLocation(name, "inventory"));
                }
            }
            else
            {
                String name = ware.getRegistryName() + "";

                for (ICybercraft.Quality quality : ICybercraft.Quality.qualities)
                {
                    if ( quality.getSpriteSuffix() != null
                            && ware.canHoldQuality(new ItemStack(ware), quality) )
                    {
                        models.add(new ModelResourceLocation(name + "_" + quality.getSpriteSuffix(), "inventory"));
                    }
                }
                models.add(new ModelResourceLocation(name, "inventory"));

            }
            ModelLoader.registerItemVariants(item, models.toArray(new ModelResourceLocation[0]));
            ModelLoader.setCustomMeshDefinition(item, new CybercraftMeshDefinition());
        }
        else if (item instanceof BlueprintItem)
        {
            for (int i = 0; i < 2; i++)
            {
                ModelLoader.setCustomModelResourceLocation(item,
                        i, new ModelResourceLocation(item.getRegistryName() + (i == 1 ? "_blank" : ""), "inventory"));
            }
        }
        else if (item instanceof CybercraftBaseItem)
        {
            CybercraftBaseItem base = ((CybercraftBaseItem) item);
            if (base.subnames.length > 0)
            {
                for (int indexSubname = 0; indexSubname < base.subnames.length; indexSubname++)
                {
                    ModelLoader.setCustomModelResourceLocation(item,
                            indexSubname, new ModelResourceLocation(item.getRegistryName() + "_" + base.subnames[indexSubname], "inventory"));
                }
            }
            else
            {
                ModelLoader.setCustomModelResourceLocation(item,
                        0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
        else
        {
            ModelLoader.setCustomModelResourceLocation(item,
                    0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }*/

}
