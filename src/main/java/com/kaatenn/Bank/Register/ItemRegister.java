package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.Item.Coin;
import com.kaatenn.Bank.Item.FertilizerUseQualification;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;
import static com.kaatenn.Bank.Register.BlockRegister.*;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> FERTILIZER_USE_QUALIFICATION = ITEMS.register("fertilizer_use_qualification", () -> new FertilizerUseQualification(new Item.Properties(), 1));
    public static final RegistryObject<Item> JUNIOR_FERTILIZER_USE_QUALIFICATION = ITEMS.register("junior_fertilizer_use_qualification", () -> new FertilizerUseQualification(new Item.Properties(), 2));
    public static final RegistryObject<Item> COIN_ONE_YUAN = ITEMS.register("one_yuan", () -> new Coin(new Item.Properties(), 1));
    public static final RegistryObject<Item> BANK_BLOCK_ITEM = ITEMS.register("bank_block", () -> new BlockItem(BANK_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> QUALIFICATION_PROVIDER_BLOCK_ITEM = ITEMS.register("qualification_provider_block", () -> new BlockItem(QUALIFICATION_PROVIDER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAPID_RIPENING_DEVICE_BLOCK_ITEM = ITEMS.register("rapid_ripening_device", () -> new BlockItem(RAPID_RIPENING_DEVICE_BLOCK.get(), new Item.Properties()));
}
