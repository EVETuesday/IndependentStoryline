package com.is.capabilities.delphi;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IDelphiCapability extends INBTSerializable<CompoundTag> {

    double getBalance();

    void setBalance(double value);

    double getNetworth();

    void setNetworth(double value);

}
