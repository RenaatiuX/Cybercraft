package com.rena.cyberware.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

public class CybercraftUpdateEvent extends EntityEvent {

    private final LivingEntity livingEntity;
    private final ICybercraftUserData cybercraftUserData;

    public CybercraftUpdateEvent(@Nonnull LivingEntity livingEntity, @Nonnull ICybercraftUserData cybercraftUserData)
    {
        super(livingEntity);
        this.livingEntity = livingEntity;
        this.cybercraftUserData = cybercraftUserData;
    }

    @Nonnull
    public LivingEntity getEntityLiving()
    {
        return livingEntity;
    }

    @Nonnull
    public ICybercraftUserData getCybercrafteUserData()
    {
        return cybercraftUserData;
    }
}
