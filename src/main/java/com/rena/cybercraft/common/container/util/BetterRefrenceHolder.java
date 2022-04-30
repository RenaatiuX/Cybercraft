package com.rena.cybercraft.common.container.util;

import net.minecraft.util.IntReferenceHolder;

public abstract class BetterRefrenceHolder extends IntReferenceHolder {
        protected int lastKnownValue = -1;

        @Override
        public boolean checkAndClearUpdateFlag() {
            int i = this.get();
            boolean flag = i != this.lastKnownValue;
            this.lastKnownValue = i;
            return flag;
        }

    }
