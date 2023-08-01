package com.kaatenn.Bank.Register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;
import static com.kaatenn.Bank.Register.BlockRegister.*;
import static com.kaatenn.Bank.Register.ItemRegister.*;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> bankCreativeModeTab = CREATIVE_MODE_TAB.register("bank_creative_mode_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MODID + ".bank_creative_mode_tab"))
            .icon(() -> new ItemStack(BANK_BLOCK.get()))
            .displayItems((params, output) -> {
                output.accept(BANK_BLOCK.get());
                output.accept(COIN_ONE_YUAN.get());
                output.accept(FERTILIZER_USE_QUALIFICATION.get());
                output.accept(QUALIFICATION_PROVIDER_BLOCK.get());
                output.accept(JUNIOR_FERTILIZER_USE_QUALIFICATION.get());
                output.accept(RAPID_RIPENING_DEVICE_BLOCK.get());
            })
            .build()); // 只能在这里写 accept 不能在监听器里面
}
