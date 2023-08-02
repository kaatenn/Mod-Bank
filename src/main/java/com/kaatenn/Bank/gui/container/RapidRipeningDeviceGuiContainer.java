package com.kaatenn.Bank.gui.container;

import com.kaatenn.Bank.blockentity.RapidRipeningDeviceEntity;
import com.kaatenn.Bank.register.BlockRegister;
import com.kaatenn.Bank.register.GuiContainerRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import static com.kaatenn.Bank.blockentity.RapidRipeningDeviceEntity.*;

public class RapidRipeningDeviceGuiContainer extends AbstractContainerMenu {
    private final BlockPos pos;

    public RapidRipeningDeviceGuiContainer(int worldId, Player player, BlockPos pos) {
        super(GuiContainerRegister.RAPID_RIPENING_DEVICE_GUI_CONTAINER.get(), worldId);
        this.pos = pos;
        if (player.level().getBlockEntity(pos) instanceof RapidRipeningDeviceEntity entity) {
            // 添加物品槽
            addSlot(new SlotItemHandler(entity.getInputItems(), SLOT_INPUT, 64, 24));
            addSlot(new SlotItemHandler(entity.getOutputItems(), SLOT_OUTPUT, 108, 24));
        }
        layoutPlayerInventorySlots(player.getInventory());
    }

    private void layoutPlayerInventorySlots(Container inventory) {
        int yPos = 70, xPos = 10;
        addSlotBox(inventory, xPos, yPos); // 确定背包栏的位置

        yPos += 58; // 因为快捷栏和背包栏有一点点间隔
        addSlotRange(inventory, 0, xPos, yPos); // 确定快捷栏的位置
    }

    private void addSlotBox(Container inventory, int xPos, int yPos) {
        int index = 9;
        // 逐行确定位置
        for (int j = 0; j < 3; j++) {
            index = addSlotRange(inventory, index, xPos, yPos);
            yPos += 18;
        }
    }

    private int addSlotRange(Container inventory, int index, int xPos, int yPos) {
        // 逐个确定单行插槽位置
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, index, xPos, yPos));
            xPos += 18;
            index++;
        }
        return index;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy(); // 复制一份用作原状态备份
            if (index < SLOT_COUNT) { // 因为是先添加的插槽故插槽的编号靠前，这里的条件是如果点的是插槽内的物品
                if (!this.moveItemStackTo(stack, SLOT_COUNT, Inventory.INVENTORY_SIZE + SLOT_COUNT, true)) {
                    // 五个参数分别表示原 stack，检测能否移动的第一个插槽的编号，检测终点和是否为反向检索，移动失败返回 false
                    // 如果移动失败了返回 EMPTY
                    return ItemStack.EMPTY;
                }
            }
            if (!this.moveItemStackTo(stack, SLOT_INPUT, SLOT_INPUT + 1, false)) {
                // 如果不是从插槽到物品栏的，如果不能移动到插槽里的话
                if (index < 27 + SLOT_COUNT) {
                    // 槽位是先添加的背包再添加的快捷，其中背包是 27 格快捷是 9 格，此处是将背包栏里的物品移动到快捷栏
                    if (!this.moveItemStackTo(stack, 27 + SLOT_COUNT, 36 + SLOT_COUNT, false)) {
                        // 如果移动不来就返回空
                        return ItemStack.EMPTY;
                    }
                } else if (index < Inventory.INVENTORY_SIZE + SLOT_COUNT && !this.moveItemStackTo(stack, SLOT_COUNT, 27 + SLOT_COUNT, false)) {
                    // 如果是在快捷栏里的物体就移动到背包栏
                    // 如果移动不来就返回空
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                // 如果移动完以后的插槽干净了那就设置为空
                slot.set(ItemStack.EMPTY); // 这里里面进行过 else 了
            } else {
                slot.setChanged();// 别忘了哦
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY; // 移动后的数量等于移动前的数量那不是等于无事发生嘛，不管是不是空的（主要是为了后半句）
            }

            slot.onTake(player, stack); // 通知槽进行响应操作
            return itemStack; // 返回被调整的原 stack
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        // 检查是否还可以和该方块交互
        // 这里交给了一个父类方法，即判断玩家和方块是否足够进
        return stillValid(ContainerLevelAccess.create(player.level(), pos), player, BlockRegister.RAPID_RIPENING_DEVICE_BLOCK.get());
    }
}
