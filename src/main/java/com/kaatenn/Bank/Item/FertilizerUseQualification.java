package com.kaatenn.Bank.Item;

import net.minecraft.world.item.Item;

public class FertilizerUseQualification extends Item {
    private final int level;
    public FertilizerUseQualification(Properties properties, int level) {
        super(properties);
        // 肥料使用资格证类，若未持有该物品则不能使用骨粉，若持有而存款不够会导致返老还童
        int MAX_LEVEL = 4;
        this.level = Math.min(level, MAX_LEVEL);
    }

    public int getLevel() {
        return level;
    }
}
