package com.rena.cybercraft.client.events;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.api.item.ICybercraft;
import com.rena.cybercraft.client.renderer.entity.CyberZombieRenderer;
import com.rena.cybercraft.common.config.CybercraftConfig;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import com.rena.cybercraft.common.item.BlueprintItem;
import com.rena.cybercraft.common.item.CybercraftArmorItem;
import com.rena.cybercraft.common.item.CybercraftBaseItem;
import com.rena.cybercraft.common.item.CybercraftItem;
import com.rena.cybercraft.core.init.EntityTypeInit;
import com.rena.cybercraft.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Cybercraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.CYBER_ZOMBIE.get(), CyberZombieRenderer::new);

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

}
