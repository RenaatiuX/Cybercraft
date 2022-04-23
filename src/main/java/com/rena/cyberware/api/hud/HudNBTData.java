package com.rena.cyberware.api.hud;

import net.minecraft.nbt.CompoundNBT;

public class HudNBTData implements IHudSaveData {

    private final CompoundNBT nbt;

    public HudNBTData(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    @Override
    public void setString(String key, String s) {
        nbt.putString(key, s);
    }

    @Override
    public void setInteger(String key, int i) {
        nbt.putInt(key, i);
    }

    @Override
    public void setBoolean(String key, boolean b) {
        nbt.putBoolean(key, b);
    }

    @Override
    public void setFloat(String key, float f) {
        nbt.putFloat(key, f);
    }

    @Override
    public String getString(String key) {
        return nbt.getString(key);
    }

    @Override
    public int getInteger(String key) {
        return nbt.getInt(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return nbt.getBoolean(key);
    }

    @Override
    public float getFloat(String key) {
        return nbt.getFloat(key);
    }
}
