package com.rena.cybercraft.common.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class NeuropozyneEffect extends Effect {

    private int iconIndex;

    public NeuropozyneEffect(EffectType type, int color, int iconIndex) {
        super(type, color);
        this.iconIndex = iconIndex;
    }
}
