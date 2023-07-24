package com.kaatenn.Bank.Event;

import com.kaatenn.Bank.Capability.PlayerDeposit;
import com.kaatenn.Bank.Capability.PlayerDepositProvider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaatenn.Bank.Bank.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class ModEventListener {
    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(bankBlock);
        }
    }

    @SubscribeEvent
    public static void registerCapabiliy(RegisterCapabilitiesEvent event) {
        event.register(PlayerDepositProvider.class);
    }
}
