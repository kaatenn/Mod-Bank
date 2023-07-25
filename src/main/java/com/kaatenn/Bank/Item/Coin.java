package com.kaatenn.Bank.Item;

import com.kaatenn.Bank.Capability.PlayerDepositProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.kaatenn.Bank.Register.BlockRegister.BANK_BLOCK;

public class Coin extends Item {
    int amount;
    public Coin(Properties properties, int amount) {
        super(properties);
        this.amount = amount;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        ItemStack itemStack = context.getItemInHand();
        if (block == BANK_BLOCK.get()) {
            Player player = context.getPlayer();
            if (player != null) {
                player.getCapability(PlayerDepositProvider.PLAYER_DEPOSIT_CAPABILITY).ifPresent(
                        (playerDeposit -> playerDeposit.increase(this.amount))
                );
                itemStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}
