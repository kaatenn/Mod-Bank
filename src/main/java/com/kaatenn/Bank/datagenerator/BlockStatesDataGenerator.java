package com.kaatenn.Bank.datagenerator;

import com.kaatenn.Bank.Register.BlockRegister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.kaatenn.Bank.Bank.MODID;

public class BlockStatesDataGenerator extends BlockStateProvider {
    public BlockStatesDataGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }
    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlockRegister.BANK_BLOCK.get());
        simpleBlock(BlockRegister.QUALIFICATION_PROVIDER_BLOCK.get());
    }
}
