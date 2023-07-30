package com.kaatenn.Bank.Block;

import com.kaatenn.Bank.BlockEntity.RapidRipeningDeviceEntity;
import com.kaatenn.Bank.GUI.Container.RapidRipeningDeviceGuiContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RapidRipeningDeviceBlock extends BaseEntityBlock {

    public static final BooleanProperty ON = BooleanProperty.create("working_state");
    public RapidRipeningDeviceBlock() {
        super(Properties.of()
                .strength(3.5F)
                .noOcclusion()
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL));
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

    @NotNull
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction facingDirection = context.getNearestLookingDirection().getOpposite();
        if (facingDirection.getAxis() == Direction.Axis.Y) {
            facingDirection = Direction.NORTH;
        }
        return this.defaultBlockState()
                .setValue(BlockStateProperties.FACING, facingDirection)
                .setValue(ON, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, ON);
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
        final String SCREEN_RAPID_TUTORIAL = "tutorial.screen.rapid_ripening_device"; // 该参数用于显示屏幕的名称
        if (!level.isClientSide) {
            // 不要在客户端执行这个操作！
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof RapidRipeningDeviceEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return Component.translatable(SCREEN_RAPID_TUTORIAL);
                    }

                    @Override
                    public @NotNull AbstractContainerMenu createMenu(int windowId, @NotNull Inventory inventory, @NotNull Player playerEntity) {
                        return new RapidRipeningDeviceGuiContainer(windowId, playerEntity, blockPos);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) player, containerProvider, be.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}
