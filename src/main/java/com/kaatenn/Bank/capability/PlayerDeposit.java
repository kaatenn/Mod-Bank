package com.kaatenn.Bank.capability;

import net.minecraft.nbt.CompoundTag;

public class PlayerDeposit {
    private int deposit;

    public PlayerDeposit() {
        deposit = 0;
    }

    public int getDeposit() {
        return deposit;
    }

    public void increase(int amount) {
        deposit += amount;
    }

    public void increase() {
        deposit++;
    }

    public boolean decrease(int amount) {
        if (deposit > amount) {
            deposit -= amount;
            return true;
        }
        return false;
    }

    public boolean decrease() {
        if (deposit == 0) {
            return false;
        }
        deposit--;
        return true;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("deposit", deposit);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        deposit = compoundTag.getInt("deposit");
    }
}
