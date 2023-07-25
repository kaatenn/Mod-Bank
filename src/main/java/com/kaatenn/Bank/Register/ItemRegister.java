package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.Item.Coin;
import com.kaatenn.Bank.Item.FertilizerUseQualification;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;
import static com.kaatenn.Bank.Register.BlockRegister.bankBlock;
import static com.kaatenn.Bank.Register.BlockRegister.qualificationProviderBlock;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> coinOneYuan = ITEMS.register("one_yuan", () -> new Coin(new Item.Properties(), 1));
    public static final RegistryObject<Item> fertilizerUseQualification = ITEMS.register("fertilizer_use_qualification", () -> new FertilizerUseQualification(new Item.Properties(), 1));
    public static final RegistryObject<Item> juniorFertilizerUseQualification = ITEMS.register("junior_fertilizer_use_qualification", () -> new FertilizerUseQualification(new Item.Properties(), 2));
    public static final RegistryObject<Item> bankBlockItem = ITEMS.register("bank_block", () -> new BlockItem(bankBlock.get(), new Item.Properties()));
    public static final RegistryObject<Item> qualificationProviderBlockItem = ITEMS.register("qualification_provider_block", () -> new BlockItem(qualificationProviderBlock.get(), new Item.Properties()));
}
