package com.kaatenn.Bank.Event;

import com.kaatenn.Bank.Capability.PlayerDepositProvider;
import com.kaatenn.Bank.Capability.PlayerFarmXp;
import com.kaatenn.Bank.Capability.PlayerFarmXpProvider;
import com.kaatenn.Bank.Item.FertilizerUseQualification;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaatenn.Bank.Bank.MODID;
import static net.minecraft.world.level.block.StemBlock.AGE;
import static net.minecraft.world.level.block.StemBlock.MAX_AGE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class ForgeEventListener {

    @SubscribeEvent
    public static void preventRipening(BonemealEvent event) {
        Player player = event.getEntity();
        int maxLevel = 0;
        for (ItemStack itemStack : player.getInventory().items) {
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof FertilizerUseQualification) {
                int level = ((FertilizerUseQualification) itemStack.getItem()).getLevel();
                if (level > maxLevel)
                    maxLevel = level;
            }
        }
        BlockState blockState = event.getBlock();
        if (blockState.getBlock() instanceof CropBlock)
            if (maxLevel == 0) {
                event.getLevel().setBlock(event.getPos(), blockState.setValue(AGE, Math.max(blockState.getValue(AGE) - 1, 0)), 2);
            } else {
                int finalMaxLevel = maxLevel;
                player.getCapability(PlayerDepositProvider.PLAYER_DEPOSIT_CAPABILITY).ifPresent(deposit -> {
                    if (deposit.decrease()) {
                        // 没钱也不能用骨粉哦
                        event.getLevel().setBlock(event.getPos(), blockState.setValue(AGE, Math.min(blockState.getValue(AGE) + finalMaxLevel * 2, MAX_AGE)), 2);
                    }
                });
            }
        player.swing(InteractionHand.MAIN_HAND, true);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void IncreaseFarmXp(BlockEvent.BreakEvent event) {
        BlockState blockState = event.getState();
        Player player = event.getPlayer();

        if (blockState.getBlock() instanceof CropBlock cropBlock) {
            if (cropBlock.isMaxAge(blockState)) {
                player.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(PlayerFarmXp::increase);
            }
        }
    }
}
