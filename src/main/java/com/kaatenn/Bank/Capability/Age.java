package com.kaatenn.Bank.Capability;

import net.minecraft.nbt.CompoundTag;

public class Age {
    private int age;

    public Age() {
        age = 0;
    }

    public int getAge() {
        return age;
    }

    public void increase(int amount) {
        age += amount;
    }

    public void increase() {
        age++;
    }

    public boolean decrease(int amount) {
        if (age > amount) {
            age -= amount;
            return true;
        }
        return false;
    }

    public boolean decrease() {
        if (age == 0) {
            return false;
        }
        age--;
        return true;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("deposit", age);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        age = compoundTag.getInt("deposit");
    }
}
