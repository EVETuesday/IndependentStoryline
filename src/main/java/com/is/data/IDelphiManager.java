package com.is.data;

import net.minecraft.world.entity.player.Player;

public interface IDelphiManager {

    double withdraw(Player player, double amount, boolean simulate);

    double transfer(Player player, double amount, boolean simulate);

    double getBalance(Player player);

    void setDelphi(Player player, double amount);

}
