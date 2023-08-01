package com.kaatenn.Bank.datagenerator;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

import static com.kaatenn.Bank.Bank.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class DataGenerate {
    @SubscribeEvent
    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator(); // 数据生成器
        PackOutput output = generator.getPackOutput(); // 路径存储器
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider(); // 异步查找器

        generator.addProvider(event.includeClient(), new BlockStatesDataGenerator(output, event.getExistingFileHelper()));
    }

}