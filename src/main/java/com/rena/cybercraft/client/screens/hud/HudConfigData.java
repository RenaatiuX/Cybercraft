package com.rena.cybercraft.client.screens.hud;

import com.rena.cybercraft.api.hud.IHudSaveData;
import com.rena.cybercraft.common.config.CybercraftConfig;
import net.minecraftforge.common.ForgeConfig;

import java.io.File;

public class HudConfigData implements IHudSaveData {

    private ForgeConfig config;

    public HudConfigData(String name)
    {
        //config = new ForgeConfig(new File(CybercraftConfig.configDirectory, "cybercraft_hud/" + name + ".cfg"));
    }


    @Override
    public void setString(String key, String s)
    {
        //config.getString(key, category, defaultValue, comment);
    }

    @Override
    public void setInteger(String key, int i)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBoolean(String key, boolean b)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFloat(String key, float f)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getString(String key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getInteger(String key)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getBoolean(String key)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public float getFloat(String key)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
