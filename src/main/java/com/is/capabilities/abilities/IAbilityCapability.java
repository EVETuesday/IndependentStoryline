package com.is.capabilities.abilities;

import com.is.data.DelphiAbilityType;
import com.is.data.DelphiItemType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IAbilityCapability extends INBTSerializable<CompoundTag> {

    List<DelphiAbilityType> getAbilities();

    void addAbility(DelphiAbilityType abilityType);

    void removeAbility(DelphiAbilityType abilityType);

    void setAbilities(List<DelphiAbilityType> abilities);

    default boolean isAbilityUnlocked(DelphiAbilityType abilityType) {
        return getAbilities().contains(abilityType);
    }

    void setCurrentItem(DelphiItemType delphiItemType);

    DelphiItemType getCurrentItem();

}
