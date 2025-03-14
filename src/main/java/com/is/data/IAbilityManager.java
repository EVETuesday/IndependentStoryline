package com.is.data;

import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IAbilityManager {

    void addAbility(Player player, DelphiAbilityType abilityType);

    void removeAbility(Player player, DelphiAbilityType abilityType);

    List<DelphiAbilityType> getAbilities(Player player);

    void setAbilities(Player player, List<DelphiAbilityType> abilities);

}
