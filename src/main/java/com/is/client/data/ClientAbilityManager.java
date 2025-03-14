package com.is.client.data;

import com.is.data.DelphiAbilityType;
import com.is.data.IAbilityManager;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ClientAbilityManager implements IAbilityManager {

    private static ClientAbilityManager manager;

    public static void initialize() {
        manager = new ClientAbilityManager();
    }

    public static ClientAbilityManager getInstance() {
        return manager;
    }

    private ClientAbilityManager() {}

    protected List<DelphiAbilityType> abilities = new ArrayList<>();

    @Override
    public void addAbility(Player player, DelphiAbilityType abilityType) {
        abilities.add(abilityType);
    }

    @Override
    public void removeAbility(Player player, DelphiAbilityType abilityType) {
        abilities.remove(abilityType);
    }

    @Override
    public List<DelphiAbilityType> getAbilities(Player player) {
        return abilities;
    }

    @Override
    public void setAbilities(Player player, List<DelphiAbilityType> abilities) {
        this.abilities = abilities;
    }

}
