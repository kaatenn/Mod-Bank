package com.kaatenn.Bank.Register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;
import static com.kaatenn.Bank.Register.BlockRegister.bankBlock;
import static com.kaatenn.Bank.Register.BlockRegister.qualificationProviderBlock;
import static com.kaatenn.Bank.Register.ItemRegister.*;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> bankCreativeModeTab = CREATIVE_MODE_TAB.register("bank_creative_mode_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MODID + ".bank_creative_mode_tab"))
            .icon(() -> new ItemStack(bankBlock.get()))
            .displayItems((params, output) -> {
                output.accept(bankBlock.get());
                output.accept(coinOneYuan.get());
                output.accept(fertilizerUseQualification.get());
                output.accept(qualificationProviderBlock.get());
                output.accept(juniorFertilizerUseQualification.get());
            })
            .build()); // 只能在这里写 accept 不能在监听器里面
}
