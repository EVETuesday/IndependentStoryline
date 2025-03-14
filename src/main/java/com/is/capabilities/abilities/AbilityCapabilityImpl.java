package com.is.capabilities.abilities;

import com.is.data.DelphiAbilityType;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class AbilityCapabilityImpl implements IAbilityCapability {

    public static String NBT_KEY = "delphi_abilities";

    private List<DelphiAbilityType> abilities = new ArrayList<>();

    @Override
    public List<DelphiAbilityType> getAbilities() {
        return new ArrayList<>(abilities);
    }

    @Override
    public void addAbility(DelphiAbilityType abilityType) {
        abilities.add(abilityType);
    }

    @Override
    public void removeAbility(DelphiAbilityType abilityType) {
        abilities.remove(abilityType);
    }

    @Override
    public void setAbilities(List<DelphiAbilityType> abilities) {
        this.abilities = abilities;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putIntArray(NBT_KEY, abilities.stream().mapToInt(DelphiAbilityType::ordinal).toArray());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        abilities = Arrays.stream(compoundTag.getIntArray(NBT_KEY)).mapToObj((a) -> DelphiAbilityType.values()[a]).collect(Collectors.toList());
    }
}
