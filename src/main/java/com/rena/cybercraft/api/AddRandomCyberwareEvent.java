package com.rena.cybercraft.api;

import com.rena.cybercraft.common.entity.CyberZombieEntity;
import net.minecraftforge.eventbus.api.Event;

public class AddRandomCyberwareEvent extends Event {

    private boolean isbrute, hasRandomWare = true;
    private CyberZombieEntity zombie;

    public AddRandomCyberwareEvent(CyberZombieEntity zombie, boolean isBrute){
        this.isbrute = isBrute;
        this.zombie = zombie;
    }

    public CyberZombieEntity getZombie() {
        return zombie;
    }

    public boolean isBrute() {
        return isbrute;
    }

    public void setHasRandomWare(boolean hasRandomWare) {
        this.hasRandomWare = hasRandomWare;
    }
}
