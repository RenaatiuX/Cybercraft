package com.rena.cybercraft.common.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rena.cybercraft.api.CybercraftAPI;
import com.rena.cybercraft.api.ICybercraftUserData;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;

public class CommandClearCybercraft{

    public CommandClearCybercraft(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("clearcyberware").requires((command) ->{
            return command.hasPermission(2);
        }).then(Commands.argument("targets", EntityArgument.entities()).executes((p_198356_0_) -> {
            return clearCyberware(p_198356_0_.getSource(), EntityArgument.getEntities(p_198356_0_, "targets"));
        })));

    }

    private int clearCyberware(CommandSource source, Collection<? extends Entity> entity) throws CommandSyntaxException {

        ServerPlayerEntity serverPlayer = source.getPlayerOrException();
        ICybercraftUserData cybercraftUserData = CybercraftAPI.getCapabilityOrNull(serverPlayer);
        if (cybercraftUserData == null);
        cybercraftUserData.resetWare(serverPlayer);
        CybercraftAPI.updateData(serverPlayer);

        source.sendSuccess(new StringTextComponent("cybercraft.commands.clearCyberware.success"), true);
        return 1;
    }

}
