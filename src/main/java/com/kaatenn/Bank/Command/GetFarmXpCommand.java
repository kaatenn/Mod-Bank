package com.kaatenn.Bank.Command;

import com.kaatenn.Bank.Capability.PlayerFarmXpProvider;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class GetFarmXpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("farmxp").executes(context -> {
            Objects.requireNonNull(context.getSource().getPlayer()).getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(
                    (farmXp) -> context.getSource().sendSuccess(() -> Component.translatable("command.bank.farm_xp", farmXp.getFarmXp()), false)
            );
            return 0;
        }));
    }
}
