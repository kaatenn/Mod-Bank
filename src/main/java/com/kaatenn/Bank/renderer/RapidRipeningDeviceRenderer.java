package com.kaatenn.Bank.renderer;

import com.kaatenn.Bank.blockentity.RapidRipeningDeviceEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class RapidRipeningDeviceRenderer implements BlockEntityRenderer<RapidRipeningDeviceEntity> {
    public RapidRipeningDeviceRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull RapidRipeningDeviceEntity be, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        be.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(items -> {
            ItemStack stack = items.getStackInSlot(RapidRipeningDeviceEntity.SLOT_INPUT);
            if (!stack.isEmpty()) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                long millis = System.currentTimeMillis();

                poseStack.pushPose();
                poseStack.scale(.5f, .5f, .5f);
                poseStack.translate(1f, 2.8f, 1f);
                float angle = (int)((millis / 10) % 360);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, combinedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, 0);
                poseStack.popPose();

                poseStack.translate(0, 0.5f, 0);
            }
        });
    }
}
