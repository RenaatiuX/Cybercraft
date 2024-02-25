package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.config.CybercraftConfig;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AttributeInit {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Cybercraft.MOD_ID);


    public static final RegistryObject<Attribute> TOLERANCE_ATTRIBUTE = ATTRIBUTES.register("tolerance", () -> new RangedAttribute("cybercraft.tolerance", CybercraftConfig.C_ESSENCE.essence.get(), 0.0F, Double.MAX_VALUE).setSyncable(true));
}
