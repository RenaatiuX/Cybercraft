package com.rena.cybercraft.client;

import com.rena.cybercraft.Cybercraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.awt.event.KeyEvent;

public class KeyBinds {

    public static final KeyBinding menu = register("menu", KeyEvent.VK_L);

    public static KeyBinding register(String name, int key) {
        KeyBinding binding = create(name, key);
        ClientRegistry.registerKeyBinding(binding);
        return binding;
    }

    protected static KeyBinding create(String name, int key) {
        return new KeyBinding( Cybercraft.MOD_ID + ".keybinds" + name, key, Cybercraft.MOD_ID + ".keybinds.category");
    }

}
