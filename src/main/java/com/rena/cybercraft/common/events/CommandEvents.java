package com.rena.cybercraft.common.events;

import com.rena.cybercraft.Cybercraft;
import com.rena.cybercraft.common.command.CommandClearCybercraft;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = Cybercraft.MOD_ID)
public class CommandEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event){
        new CommandClearCybercraft(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

}
