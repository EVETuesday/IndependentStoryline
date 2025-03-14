package com.is.capabilities.delphi;

import net.minecraft.nbt.CompoundTag;

public final class DelphiCapabilityImpl implements IDelphiCapability {

    public static String NBT_KEY = "delphi_balance";

    private double balance = 0.0d;

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double value) {
        balance = value;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(NBT_KEY, balance);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        balance = compoundTag.getDouble(NBT_KEY);
    }
}
