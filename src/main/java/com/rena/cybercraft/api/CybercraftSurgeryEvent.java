package com.rena.cybercraft.api;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.items.ItemStackHandler;

public class CybercraftSurgeryEvent extends EntityEvent {

    public CybercraftSurgeryEvent(LivingEntity entity) {
        super(entity);
    }

    /**
     * Fired when the Surgery Chamber starts the process of altering an entities installed Cybercraft
     * Changing inventories isn't supported.
     * Cancel to prevent any changes
     */
    @Cancelable
    public static class Pre extends CybercraftSurgeryEvent
    {
        public ItemStackHandler inventoryActual;
        public ItemStackHandler inventoryTarget;

        public Pre(LivingEntity livingEntity, ItemStackHandler inventoryActual, ItemStackHandler inventoryTarget)
        {
            super(livingEntity);

            this.inventoryActual = new ItemStackHandler(120);
            this.inventoryActual.deserializeNBT(inventoryActual.serializeNBT());
            this.inventoryTarget = new ItemStackHandler(120);
            this.inventoryTarget.deserializeNBT(inventoryTarget.serializeNBT());

        }

        public ItemStackHandler getActualCybercraft()
        {
            return inventoryActual;
        }

        public ItemStackHandler getTargetCybercraft()
        {
            return inventoryTarget;
        }
    }

    /**
     * Fired when the Surgery Chamber finishes the process of altering an entities installed Cybercraft
     */
    public static class Post extends CybercraftSurgeryEvent
    {
        public Post(LivingEntity livingEntity)
        {
            super(livingEntity);
        }
    }
}
