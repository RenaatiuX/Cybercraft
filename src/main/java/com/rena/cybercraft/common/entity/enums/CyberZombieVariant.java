package com.rena.cybercraft.common.entity.enums;

import java.util.Arrays;
import java.util.Comparator;

public enum CyberZombieVariant {

    ZOMBIE(0),
    ZOMBIE_BRUTE(1);

    private static final CyberZombieVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(CyberZombieVariant::getId)).toArray(CyberZombieVariant[]::new);

    private final int id;

    CyberZombieVariant(int id){

        this.id = id;

    }

    public int getId() {
        return this.id;
    }


    public static CyberZombieVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
