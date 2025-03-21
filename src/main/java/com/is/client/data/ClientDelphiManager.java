package com.is.client.data;

import com.is.data.IDelphiManager;
import net.minecraft.world.entity.player.Player;

public class ClientDelphiManager implements IDelphiManager {

    private static ClientDelphiManager manager;

    public static void initialize() {
        manager = new ClientDelphiManager();
    }

    public static ClientDelphiManager getInstance() {
        return manager;
    }

    private ClientDelphiManager() {}

    private double balance;
    private double networth;

    @Override
    public double getBalance(Player player) {
        return balance;
    }

    @Override
    public void setBalance(Player player, double amount) {
        balance = amount;
    }

    @Override
    public double getNetworth(Player player) {
        return networth;
    }

    @Override
    public void setNetworth(Player player, double amount) {
        networth = amount;
    }
}
