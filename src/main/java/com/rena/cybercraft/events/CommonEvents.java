package com.rena.cybercraft.events;

import com.rena.cybercraft.common.tileentities.TileEntitySurgery;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;


public class CommonEvents {

    public static void wrong(TileEntitySurgery tileEntitySurgery)
    {
        // client side only
    }

    public static boolean workingOnPlayer(LivingEntity entityLivingBase)
    {
        return false;
    }

}
