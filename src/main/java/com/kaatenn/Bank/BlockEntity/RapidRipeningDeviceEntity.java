package com.kaatenn.Bank.BlockEntity;

import com.kaatenn.Bank.Common.AdaptedItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.kaatenn.Bank.Block.RapidRipeningDeviceBlock.ON;
import static com.kaatenn.Bank.Register.BlockEntityTypeRegister.RAPID_RIPENING_DEVICE_ENTITY;

public final class RapidRipeningDeviceEntity extends BlockEntity {

    private static final String ITEMS_INPUT_TAG = "items_input";
    private static final String ITEMS_OUTPUT_TAG = "items_output";

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_INPUT_COUNT = 1;

    public static final int SLOT_OUTPUT = 0;
    public static final int SLOT_OUTPUT_COUNT = 1;

    private final ItemStackHandler inputItems = createItemHandler(SLOT_INPUT_COUNT);
    private final ItemStackHandler outputItems = createItemHandler(SLOT_OUTPUT_COUNT);
    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new CombinedInvWrapper(inputItems, outputItems));
    private final LazyOptional<IItemHandler> inputItemHandler = LazyOptional.of(() -> new AdaptedItemHandler(inputItems) {
        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }
    });

    private final LazyOptional<IItemHandler> outputItemHandler = LazyOptional.of(() -> new AdaptedItemHandler(outputItems) {
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }
    });

    public RapidRipeningDeviceEntity(BlockEntityType<RapidRipeningDeviceEntity> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public RapidRipeningDeviceEntity(BlockPos worldPosition, BlockState blockState) {
        this(RAPID_RIPENING_DEVICE_ENTITY.get(), worldPosition, blockState);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
        inputItemHandler.invalidate();
        outputItemHandler.invalidate();
    }

    @NotNull
    private ItemStackHandler createItemHandler(int count) {
        return new ItemStackHandler(count) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return itemHandler.cast();
            } else if (side == Direction.DOWN) {
                return outputItemHandler.cast();
            } else {
                return inputItemHandler.cast();
            }
        } else {
            return super.getCapability(cap, side);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS_INPUT_TAG, inputItems.serializeNBT());
        tag.put(ITEMS_OUTPUT_TAG, outputItems.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS_INPUT_TAG)) {
            inputItems.deserializeNBT(tag.getCompound(ITEMS_INPUT_TAG));
        }
        if (tag.contains(ITEMS_OUTPUT_TAG)) {
            outputItems.deserializeNBT(tag.getCompound(ITEMS_OUTPUT_TAG));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.put(ITEMS_INPUT_TAG, inputItems.serializeNBT());
        tag.put(ITEMS_OUTPUT_TAG, outputItems.serializeNBT());
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            if (tag.contains(ITEMS_INPUT_TAG)) {
                inputItems.deserializeNBT(tag.getCompound(ITEMS_INPUT_TAG));
            }
            if (tag.contains(ITEMS_OUTPUT_TAG)) {
                outputItems.deserializeNBT(tag.getCompound(ITEMS_OUTPUT_TAG));
            }
        }
    }

    @Override
    public @NotNull ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            handleUpdateTag(tag);
        }
    }

    public void tickServer() {
        if (level != null && level.getGameTime() % 5 == 0) {
            ItemStack itemsStackInSlot = inputItems.getStackInSlot(SLOT_INPUT);
            if (!itemsStackInSlot.isEmpty()) {
                if (itemsStackInSlot.isDamageableItem()) {
                    int value = itemsStackInSlot.getDamageValue();
                    if (value > 0) {
                        itemsStackInSlot.setDamageValue(value - 1);
                        BlockState updateBlock = getBlockState().setValue(ON, true);
                        level.setBlockAndUpdate(getBlockPos(), updateBlock);
                    } else {
                        BlockState updateBlock = getBlockState().setValue(ON, false);
                        level.setBlockAndUpdate(getBlockPos(), updateBlock);
                        if (outputItems.getStackInSlot(SLOT_OUTPUT).isEmpty() && !inputItems.getStackInSlot(SLOT_INPUT).isEmpty()){
                            outputItems.setStackInSlot(SLOT_OUTPUT, itemsStackInSlot);
                            inputItems.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
                        }
                    }
                } else {
                    ejectItemWhenErrorInput();
                }
            }
        }
    }

    private void ejectItemWhenErrorInput() {
        BlockPos pos = worldPosition.relative(Direction.UP);
        if (level != null) {
            Block.popResource(level, pos, inputItems.extractItem(SLOT_INPUT, 1, false));
        }
    }

    /**
     * @param itemStack 提供的 ItemStack
     * @return 返回剩余的物品数
     */
    public ItemStack insertItem(ItemStack itemStack) {
        inputItems.insertItem(SLOT_INPUT, itemStack, false);
        return new ItemStack(itemStack.getItem(), itemStack.getCount() - 1);
    }

    public boolean canInsertItem() {
        return inputItems.getStackInSlot(SLOT_INPUT).isEmpty() && outputItems.getStackInSlot(SLOT_OUTPUT).isEmpty();
    }

    public ItemStack extractItem() {
        return outputItems.extractItem(SLOT_OUTPUT, outputItems.getStackInSlot(SLOT_OUTPUT).getCount(), false);
    }

    public boolean canExtractItem() {
        return !outputItems.getStackInSlot(SLOT_OUTPUT).isEmpty();
    }

}
