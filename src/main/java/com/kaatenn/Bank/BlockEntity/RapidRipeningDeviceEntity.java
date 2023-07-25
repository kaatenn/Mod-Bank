package com.kaatenn.Bank.BlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.kaatenn.Bank.Register.BlockEntityTypeRegister.RAPID_RIPENING_DEVICE_ENTITY;

public final class RapidRipeningDeviceEntity extends BlockEntity {

    public RapidRipeningDeviceEntity(BlockEntityType<RapidRipeningDeviceEntity> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public RapidRipeningDeviceEntity(BlockPos worldPosition, BlockState blockState) {
        this(RAPID_RIPENING_DEVICE_ENTITY.get(), worldPosition, blockState);
    }
}
