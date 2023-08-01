package com.kaatenn.Bank.command;

import com.kaatenn.Bank.capability.PlayerDepositProvider;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public class GetDepositCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("money").executes(context -> {
            Objects.requireNonNull(context.getSource().getPlayer()).getCapability(PlayerDepositProvider.PLAYER_DEPOSIT_CAPABILITY).ifPresent(
                    (deposit) -> context.getSource().sendSuccess(() -> Component.translatable("command.bank.deposit", deposit.getDeposit()),false)
            );
            return 0;
        }));
    }
}
