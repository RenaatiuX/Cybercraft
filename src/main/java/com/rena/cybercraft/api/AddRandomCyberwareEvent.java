package com.rena.cybercraft.api;

import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraftforge.eventbus.api.Event;

/**
 * is executed when a cyberZombie begins to tick, there u can add cyberware to the spawned zombie
 *
 */
public class AddRandomCyberwareEvent extends Event {


    private boolean isBrute, hasRandomWare = true;
    private CyberZombieEntity zombie;

    public AddRandomCyberwareEvent(CyberZombieEntity zombie, boolean isBrute){
        this.isBrute = isBrute;
        this.zombie = zombie;
    }

    public CyberZombieEntity getZombie() {
        return zombie;
    }

    public boolean isBrute() {
        return isBrute;
    }

    public void setHasRandomWare(boolean hasRandomWare) {
        this.hasRandomWare = hasRandomWare;
    }
}
