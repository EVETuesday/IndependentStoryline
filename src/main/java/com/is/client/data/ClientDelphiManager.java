package com.is.client.data;

import com.is.data.IDelphiManager;
import com.is.events.DelphiBalanceChangedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

public class ClientDelphiManager implements IDelphiManager {

    private static ClientDelphiManager manager;

    public static void invalidate() {
        manager = null;
    }

    public static void initialize() {
        manager = new ClientDelphiManager();
    }

    public static ClientDelphiManager getInstance() {
        return manager;
    }

    private ClientDelphiManager() {
    }

    private double balance;
    private double networth;

    @Override
    public double getBalance(Player player) {
        return balance;
    }

    @Override
    public void setBalance(Player player, double amount) {
        if (MinecraftForge.EVENT_BUS.post(new DelphiBalanceChangedEvent(Minecraft.getInstance().player, balance, amount, networth))) return;
        balance = amount;
    }

    @Override
    public double getNetworth(Player player) {
        return networth;
    }

    @Override
    public void setNetworth(Player player, double amount) {
        if (MinecraftForge.EVENT_BUS.post(new DelphiBalanceChangedEvent(Minecraft.getInstance().player, balance, balance, amount))) return;
        networth = amount;
    }
}
