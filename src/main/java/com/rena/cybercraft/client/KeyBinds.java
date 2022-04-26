package com.rena.cybercraft.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBinds {

    public static KeyBinding menu;

    public static void init()
    {
        menu = new KeyBinding("cybercraft.keybinds.menu", Keyboard.KEY_R, "cybercraft.keybinds.category");
        ClientRegistry.registerKeyBinding(menu);
    }

}
