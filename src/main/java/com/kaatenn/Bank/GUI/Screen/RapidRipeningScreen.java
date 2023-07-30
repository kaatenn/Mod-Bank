package com.kaatenn.Bank.GUI.Screen;

import com.kaatenn.Bank.GUI.Container.RapidRipeningDeviceGuiContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.kaatenn.Bank.Bank.MODID;

public class RapidRipeningScreen extends AbstractContainerScreen<RapidRipeningDeviceGuiContainer> {
    private final ResourceLocation GUI = new ResourceLocation(MODID, "textures/gui/rapid_ripening.png");
    public RapidRipeningScreen(RapidRipeningDeviceGuiContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.inventoryLabelY = this.imageHeight - 110; // 前者表示物品栏标签的 Y，后者是图片高度
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2; // 居中处理，前者是屏幕高度
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight); // 渲染
    }
}
