package com.rena.cybercraft.api;

public interface IEnergy {

    long givePower(long power, boolean simulated);

    long getStoredPower();

    long getCapacity();

    long takePower(long power, boolean simulated);

}
