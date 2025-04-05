package com.is.capabilities.abilities;

import com.is.data.DelphiAbilityType;
import com.is.data.DelphiItemType;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class AbilityCapabilityImpl implements IAbilityCapability {

    public static String NBT_KEY_ABILITIES = "delphi_abilities";
    public static String NBT_KEY_ITEMS = "delphi_items";

    private List<DelphiAbilityType> abilities = new ArrayList<>();
    private int currentItemOrdinal = 0;

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
    public void setCurrentItem(DelphiItemType delphiItemType) {
        currentItemOrdinal = delphiItemType.ordinal();
    }

    @Override
    public DelphiItemType getCurrentItem() {
        return DelphiItemType.values()[currentItemOrdinal];
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putIntArray(NBT_KEY_ABILITIES, abilities.stream().mapToInt(DelphiAbilityType::ordinal).toArray());
        compoundTag.putInt(NBT_KEY_ITEMS, currentItemOrdinal);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        abilities = Arrays.stream(compoundTag.getIntArray(NBT_KEY_ABILITIES)).mapToObj((a) -> DelphiAbilityType.values()[a]).collect(Collectors.toList());
        currentItemOrdinal = compoundTag.getInt(NBT_KEY_ITEMS);
    }
}
