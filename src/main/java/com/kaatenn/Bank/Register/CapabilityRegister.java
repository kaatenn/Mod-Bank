package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.Capability.PlayerDepositProvider;
import com.kaatenn.Bank.Capability.PlayerFarmXpProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaatenn.Bank.Bank.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class CapabilityRegister {
    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(PlayerDepositProvider.class);
        event.register(PlayerFarmXpProvider.class);
    }
}
