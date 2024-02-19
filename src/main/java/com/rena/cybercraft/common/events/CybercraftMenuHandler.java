package com.rena.cybercraft.common.events;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class CybercraftMenuHandler {

    public static final CybercraftMenuHandler INSTANCE = new CybercraftMenuHandler();
    private Minecraft mc = Minecraft.getInstance();

    int wasInScreen = 0;
    public static boolean wasSprinting = false;
    private static List<Integer> lastPressed = new ArrayList<>();
    private static List<Integer> pressed = new ArrayList<>();


    /*@SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            if ( !KeyBinds.menu.consumeClick()
                    && mc.screen == null
                    && wasInScreen > 0 )
            {
                KeyConflictContext inGame = KeyConflictContext.IN_GAME;
                mc.options.keyUp.setKeyConflictContext(inGame);
                mc.options.keyLeft.setKeyConflictContext(inGame);
                mc.options.keyDown.setKeyConflictContext(inGame);
                mc.options.keyRight.setKeyConflictContext(inGame);
                mc.options.keyJump.setKeyConflictContext(inGame);
                mc.options.keyShift.setKeyConflictContext(inGame);
                mc.options.keySprint.setKeyConflictContext(inGame);

                if (wasSprinting)
                {
                    mc.player.setSprinting(wasSprinting);
                }
                wasInScreen--;
            }
        }
        if(event.phase == TickEvent.Phase.END)
        {
            ICybercraftUserData cyberwareUserData = CybercraftAPI.getCapabilityOrNull(mc.player);
            if ( mc.player != null
                    && mc.screen == null
                    && cyberwareUserData != null )
            {
                for (int keyCode : cyberwareUserData.getHotkeys())
                {
                    if (isPressed(cyberwareUserData, keyCode))
                    {
                        pressed.add(keyCode);
                        if (!lastPressed.contains(keyCode))
                        {
                            ClientUtils.useActiveItemClient(mc.player, cyberwareUserData.getHotkey(keyCode));
                        }
                    }
                }

                lastPressed = pressed;
                pressed = new ArrayList<>();
            }

            if ( mc.player != null
                    && cyberwareUserData.getNumActiveItems() > 0
                    && KeyBinds.menu.consumeClick()
                    && mc.screen == null )
            {
                KeyConflictContext gui = KeyConflictContext.GUI;
                mc.options.keyUp.setKeyConflictContext(gui);
                mc.options.keyLeft.setKeyConflictContext(gui);
                mc.options.keyDown.setKeyConflictContext(gui);
                mc.options.keyRight.setKeyConflictContext(gui);
                mc.options.keyJump.setKeyConflictContext(gui);
                mc.options.keyShift.setKeyConflictContext(gui);
                mc.options.keySprint.setKeyConflictContext(gui);

                mc.setScreen(new GuiCybercraftMenu());
                cyberwareUserData.setOpenedRadialMenu(true);
                CCNetwork.PACKET_HANDLER.sendToServer(new OpenRadialMenuPacket());

                wasInScreen = 5;
            }
            else if ( wasInScreen > 0
                    && mc.screen instanceof GuiCybercraftMenu )
            {
                wasSprinting = mc.player.isSprinting();
            }
        }
    }

    private boolean isPressed(ICybercraftUserData cyberwareUserData, int keyCode)
    {
        if (keyCode < 0)
        {
            keyCode = keyCode + 100;
            return Mouse.isButtonDown(keyCode);
        }
        else if (keyCode > 900)
        {
            boolean shiftPressed = Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) || Keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);

            keyCode = keyCode - 900;
            return Keyboard.isKeyDown(keyCode) && shiftPressed;
        }
        else
        {
            if (cyberwareUserData.getHotkey(keyCode + 900) != null)
            {
                boolean shiftPressed = Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT) || Keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT);

                return Keyboard.isKeyDown(keyCode) && !shiftPressed;
            }
            return Keyboard.isKeyDown(keyCode);
        }
    }*/

}
