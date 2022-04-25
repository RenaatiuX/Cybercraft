package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.effect.NeuropozyneEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectInit {

    public static final DeferredRegister<Effect> EFFECT = DeferredRegister.create(ForgeRegistries.POTIONS, Cybercraft.MOD_ID);

    public static final RegistryObject<Effect> NEUROPOZYNE = EFFECT.register("neuropozyne",
            ()-> new NeuropozyneEffect(EffectType.BENEFICIAL, 0x47453d, 0));
    public static final RegistryObject<Effect> REJECTION = EFFECT.register("rejection",
            ()-> new NeuropozyneEffect(EffectType.HARMFUL, 0xFF0000, 1));

}
