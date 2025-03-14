package com.is.data;

import net.minecraft.world.entity.player.Player;

public interface IDelphiManager {

    default double withdraw(Player player, double amount, boolean simulate) {
        return transfer(player, -amount, simulate);
    }

    default double transfer(Player player, double amount, boolean simulate) {
        double balance = getBalance(player);
        double newBalance = Math.max(balance + amount, 0);
        if (!simulate) setDelphi(player, newBalance);
        return Math.abs(balance - newBalance);
    }

    double getBalance(Player player);

    void setDelphi(Player player, double amount);

}
