package com.kaatenn.Bank.register;

import com.kaatenn.Bank.gui.container.RapidRipeningDeviceGuiContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;

public class GuiContainerRegister {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<RapidRipeningDeviceGuiContainer>> RAPID_RIPENING_DEVICE_GUI_CONTAINER = MENU_TYPES.register("rapid_ripening_device_gui_container",
            () -> IForgeMenuType.create(((windowId, inv, data) -> new RapidRipeningDeviceGuiContainer(windowId, inv.player, data.readBlockPos()))));
}
