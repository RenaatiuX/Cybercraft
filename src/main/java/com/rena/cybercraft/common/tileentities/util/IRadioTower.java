package com.rena.cybercraft.common.tileentities.util;

import net.minecraft.util.math.BlockPos;

/**
 * this is an Interface for a TileEntity that provides a Base for the Radio
 * u can add any kind of tiers here
 */
public interface IRadioTower {

    int getTier(BlockPos pos);

}
