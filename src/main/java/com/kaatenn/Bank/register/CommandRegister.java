package com.kaatenn.Bank.register;

import com.kaatenn.Bank.command.GetDepositCommand;
import com.kaatenn.Bank.command.GetFarmXpCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaatenn.Bank.Bank.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class CommandRegister {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        GetDepositCommand.register(event.getDispatcher());
        GetFarmXpCommand.register(event.getDispatcher());
    }
}
