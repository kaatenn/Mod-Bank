package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.BlockEntity.RapidRipeningDeviceEntity;
import com.mojang.datafixers.DSL;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.kaatenn.Bank.Bank.MODID;
import static com.kaatenn.Bank.Register.BlockRegister.RAPID_RIPENING_DEVICE_BLOCK;

public class BlockEntityTypeRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<RapidRipeningDeviceEntity>> RAPID_RIPENING_DEVICE_ENTITY = BLOCK_ENTITY_TYPES.register("rapid_ripening_device",
            () -> BlockEntityType.Builder.of(RapidRipeningDeviceEntity::new, RAPID_RIPENING_DEVICE_BLOCK.get()).build(DSL.remainderType())
            );
}
