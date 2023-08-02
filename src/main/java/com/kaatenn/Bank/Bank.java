package com.kaatenn.Bank;

import com.kaatenn.Bank.capability.PlayerDepositProvider;
import com.kaatenn.Bank.capability.PlayerFarmXpProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.kaatenn.Bank.register.BlockEntityTypeRegister.BLOCK_ENTITY_TYPES;
import static com.kaatenn.Bank.register.BlockRegister.BLOCKS;
import static com.kaatenn.Bank.register.GuiContainerRegister.MENU_TYPES;
import static com.kaatenn.Bank.register.ItemRegister.ITEMS;
import static com.kaatenn.Bank.register.CreativeTabRegister.CREATIVE_MODE_TAB;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Bank.MODID)
public class Bank {
    public static final String MODID = "bank";
    private static final Logger LOGGER = LogUtils.getLogger();

    // 总线管理
    public Bank() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_MODE_TAB.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        MENU_TYPES.register(bus);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapabilityToEntity);
    }

    public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(PlayerDepositProvider.PLAYER_DEPOSIT_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(MODID, "deposit"), new PlayerDepositProvider());
            }
            if (!player.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(MODID, "farm_xp"), new PlayerFarmXpProvider());
            }
        }
    }
}
