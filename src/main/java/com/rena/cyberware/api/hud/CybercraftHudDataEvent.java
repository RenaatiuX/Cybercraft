package com.rena.cyberware.api.hud;

import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

public class CybercraftHudDataEvent extends Event {

    private List<IHudElement> elements = new ArrayList<>();

    public CybercraftHudDataEvent()
    {
        super();
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
