package com.kaatenn.Bank.Event;

import com.kaatenn.Bank.Capability.PlayerDeposit;
import com.kaatenn.Bank.Capability.PlayerDepositProvider;
import com.kaatenn.Bank.Command.GetDepositCommand;
import com.kaatenn.Bank.Item.FertilizerUseQualification;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kaatenn.Bank.Bank.*;
import static net.minecraft.world.level.block.StemBlock.AGE;
import static net.minecraft.world.level.block.StemBlock.MAX_AGE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class ForgeEventListener {
    // 在没有资格证时使农作物倒着长
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
        if (blockState.getBlock() instanceof BushBlock)
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
    public static void registerCommand(RegisterCommandsEvent event) {
        GetDepositCommand.register(event.getDispatcher());
    }
}
