package com.rena.cybercraft.core.init;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Cybercraft.MOD_ID);

    public static final RegistryObject<EntityType<CyberZombieEntity>> CYBER_ZOMBIE = ENTITY_TYPES.register("cyberzombie",
            () -> EntityType.Builder.of(CyberZombieEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.95F)
                    .clientTrackingRange(8).build("cyberzombie"));

}
