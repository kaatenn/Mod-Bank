package com.kaatenn.Bank.Block;

import com.kaatenn.Bank.Capability.PlayerFarmXpProvider;
import com.kaatenn.Bank.Item.FertilizerUseQualification;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static com.kaatenn.Bank.Register.ItemRegister.fertilizerUseQualification;
import static com.kaatenn.Bank.Register.ItemRegister.juniorFertilizerUseQualification;

public class QualificationProviderBlock extends Block {
    public QualificationProviderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        int maxLevel = 0;
        ItemStack maxItemStack = null;
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() instanceof FertilizerUseQualification) {
                maxLevel = Math.max(((FertilizerUseQualification) (itemStack.getItem())).getLevel(), maxLevel);
                maxItemStack = itemStack;
            }
        }
        // 升级资格证书
        if (maxLevel == 0) {
            player.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(
                    farmXp -> {
                        if (farmXp.getFarmXp() >= 1) {
                            farmXp.decrease(1);
                            player.addItem(new ItemStack(fertilizerUseQualification.get()));
                        }
                    }
            );
        } else {
            if (maxLevel < FertilizerUseQualification.MAX_QUALIFICATION_LEVEL) {
                int finalMaxLevel = maxLevel;
                ItemStack finalMaxItemStack = maxItemStack;
                player.getCapability(PlayerFarmXpProvider.PLAYER_FARM_XP_CAPABILITY).ifPresent(playerFarmXp ->
                {
                    if (playerFarmXp.getFarmXp() >= finalMaxLevel * Math.pow(5, finalMaxLevel)) {
                        finalMaxItemStack.shrink(1);
                        upgradeQualification(finalMaxLevel, player);
                        playerFarmXp.decrease((int) (finalMaxLevel * Math.pow(5, finalMaxLevel)));
                    }
                });
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void upgradeQualification(int maxLevel, Player player) {
        switch (maxLevel) {
            case 1 -> player.addItem(new ItemStack(juniorFertilizerUseQualification.get()));
        }
    }
}
