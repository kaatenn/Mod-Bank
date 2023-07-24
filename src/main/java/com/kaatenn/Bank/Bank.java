package com.kaatenn.Bank;

import com.kaatenn.Bank.Item.Coin;
import com.kaatenn.Bank.Item.FertilizerUseQualification;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Bank.MODID)
public class Bank {
    public static final String MODID = "bank";
    private static final Logger LOGGER = LogUtils.getLogger();

    // 注册集合
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // 注册实例
    public static final RegistryObject<Block> bankBlock = BLOCKS.register("bank_block", () -> new Block(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.CROP)));
    public static final RegistryObject<Item> bankBlockItem = ITEMS.register("bank_block", () -> new BlockItem(bankBlock.get(), new Item.Properties()));
    public static final RegistryObject<Item> coinOneYuan = ITEMS.register("one_yuan", () -> new Coin(new Item.Properties(), 1));
    public static final RegistryObject<Item> fertilizerUseQualification = ITEMS.register("fertilizer_use_qualification", () -> new FertilizerUseQualification(new Item.Properties(), 1));
    public static final RegistryObject<CreativeModeTab> bankCreativeModeTab = CREATIVE_MODE_TAB.register("bank_creative_mode_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MODID + ".bank_creative_mode_tab"))
            .icon(() -> new ItemStack(bankBlock.get()))
            .displayItems((params, output) -> {
                output.accept(bankBlock.get());
                output.accept(coinOneYuan.get());
                output.accept(fertilizerUseQualification.get());
            })
            .build()); // 只能在这里写 accept 不能在监听器里面

    public Bank() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TAB.register(bus);
    }
}
