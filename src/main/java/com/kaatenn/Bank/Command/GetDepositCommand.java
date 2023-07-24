package com.kaatenn.Bank.Command;

import com.kaatenn.Bank.Capability.PlayerDepositProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.Objects;

public class GetDepositCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("money").executes(context -> {
            Objects.requireNonNull(context.getSource().getPlayer()).getCapability(PlayerDepositProvider.PLAYER_DEPOSIT_CAPABILITY).ifPresent(
                    (deposit) -> {
                        context.getSource().sendSuccess(() -> Component.translatable("command.bank.deposit", deposit.getDeposit()),false);
                    }
            );
            return 0;
        }));
    }
}
