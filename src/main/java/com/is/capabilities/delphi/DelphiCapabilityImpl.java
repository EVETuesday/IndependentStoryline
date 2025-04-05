package com.is.capabilities.delphi;

import net.minecraft.nbt.CompoundTag;

public final class DelphiCapabilityImpl implements IDelphiCapability {

    public static String NBT_KEY_BALANCE = "delphi_balance";
    public static String NBT_KEY_NETWORTH = "delphi_networth";

    private double balance = 0.0d;
    private double networth = 0.0d;

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double value) {
        balance = value;
    }

    @Override
    public double getNetworth() {
        return networth;
    }

    @Override
    public void setNetworth(double value) {
        networth = value;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(NBT_KEY_BALANCE, balance);
        tag.putDouble(NBT_KEY_NETWORTH, networth);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        balance = compoundTag.getDouble(NBT_KEY_BALANCE);
        networth = compoundTag.getDouble(NBT_KEY_NETWORTH);
    }
}
