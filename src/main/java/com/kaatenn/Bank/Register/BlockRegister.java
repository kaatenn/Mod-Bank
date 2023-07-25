package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.Block.QualificationProviderBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> bankBlock = BLOCKS.register("bank_block", () -> new Block(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.CROP)));
    public static final RegistryObject<Block> qualificationProviderBlock = BLOCKS.register("qualification_provider_block", () -> new QualificationProviderBlock(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.CROP)));

}
