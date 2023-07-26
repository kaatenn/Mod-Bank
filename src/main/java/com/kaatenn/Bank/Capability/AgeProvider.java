package com.kaatenn.Bank.Capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AgeProvider implements ICapabilityProvider, INBTSerializable {
    private final Age age = new Age();
    public static final Capability<Age> AGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<Age> lazyOptional = LazyOptional.of(() -> this.age);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == AGE_CAPABILITY) {
            return lazyOptional.cast();
        } else {
            return LazyOptional.empty();
        }
    }

    @Override
    public Tag serializeNBT() {
        var tag = new CompoundTag();
        age.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        age.loadNBTData((CompoundTag) nbt);
    }
}
