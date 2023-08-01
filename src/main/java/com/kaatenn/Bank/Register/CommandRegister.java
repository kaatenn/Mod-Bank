package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.Command.GetDepositCommand;
import com.kaatenn.Bank.Command.GetFarmXpCommand;
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
