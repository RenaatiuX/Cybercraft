package com.rena.cyberware.api.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class CybercraftHudEvent extends Event {

    private List<IHudElement> elements = new ArrayList<>();
    private boolean hudjackAvailable;
    private MatrixStack scaledResolution;

    public CybercraftHudEvent(MatrixStack scaledResolution, boolean hudjackAvailable)
    {
        super();
        this.scaledResolution = scaledResolution;
        this.hudjackAvailable = hudjackAvailable;
    }

    public MatrixStack getResolution()
    {
        return scaledResolution;
    }

    public boolean isHudjackAvailable()
    {
        return hudjackAvailable;
    }

    public void setHudjackAvailable(boolean hudjackAvailable)
    {
        this.hudjackAvailable = hudjackAvailable;
    }

    public List<IHudElement> getElements()
    {
        return elements;
    }

    public void addElement(IHudElement element)
    {
        elements.add(element);
    }

}
