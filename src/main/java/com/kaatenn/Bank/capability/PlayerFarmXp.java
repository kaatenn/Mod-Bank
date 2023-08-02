package com.kaatenn.Bank.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerFarmXp {
    private int farmXp;

    public PlayerFarmXp() {
        farmXp = 0;
    }

    public int getFarmXp() {
        return farmXp;
    }

    public void increase(int amount) {
        farmXp += amount;
    }

    public void increase() {
        farmXp++;
    }

    public void setFarmXp(int farmXp) {
        this.farmXp = farmXp;
    }

    public void decrease(int amount) {
        farmXp -= amount;
    }

    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("farm_xp", farmXp);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        farmXp = compoundTag.getInt("farm_xp");
    }
}
