package com.kaatenn.Bank.Register;

import com.kaatenn.Bank.Renderer.RapidRipeningDeviceRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaatenn.Bank.Bank.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererRegister {
    @SubscribeEvent
    public static void rendererRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityTypeRegister.RAPID_RIPENING_DEVICE_ENTITY.get(), RapidRipeningDeviceRenderer::new);
    }
}
