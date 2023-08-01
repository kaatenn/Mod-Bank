package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.GUI.Screen.RapidRipeningScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.kaatenn.Bank.Bank.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID, value = Dist.CLIENT)
public class ScreenRegister {

    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(GuiContainerRegister.RAPID_RIPENING_DEVICE_GUI_CONTAINER.get(), RapidRipeningScreen::new));
    }
}
