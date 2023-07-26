package com.kaatenn.Bank.Block;

import com.kaatenn.Bank.BlockEntity.RapidRipeningDeviceEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RapidRipeningDeviceBlock extends BaseEntityBlock {
    public RapidRipeningDeviceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new RapidRipeningDeviceEntity(pos, blockState);
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null; // 客户端不做任何操作
        } else {
            return (lvl, pos, st, blockEntity) -> {
                if (blockEntity instanceof RapidRipeningDeviceEntity be) {
                    be.tickServer();
                }
            };
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (!level.isClientSide) {
            // 不要在客户端执行这个操作！
            ItemStack itemStack = player.getItemInHand(hand);

            if (!itemStack.isEmpty() && itemStack.isDamageableItem()) {
                RapidRipeningDeviceEntity entity = (RapidRipeningDeviceEntity) level.getBlockEntity(blockPos);
                if (entity != null) {
                    ItemStack remainItem = entity.insertItem(itemStack);
                    player.setItemInHand(hand, remainItem);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
