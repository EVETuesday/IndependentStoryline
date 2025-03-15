package com.is.server.data;

import com.is.data.IDelphiManager;
import com.is.server.ServerUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ServerDelphiManager implements IDelphiManager {

    private static ServerDelphiManager manager;

    public static void initialize() {
        manager = new ServerDelphiManager();
    }

    public static ServerDelphiManager getInstance() {
        return manager;
    }

    protected final Scoreboard scoreboard;
    protected final Objective scoreboardObjective;

    public ServerDelphiManager() {
        MinecraftServer minecraftServer = ServerUtils.getCurrentServer();
        if (minecraftServer == null) throw new RuntimeException("DelphiManager has tried to initialize before the server");

        scoreboard = minecraftServer.getScoreboard();

        Objective objective = scoreboard.getObjective("delphi");
        if (objective == null) {
            objective = scoreboard.addObjective("delphi", ObjectiveCriteria.DUMMY, Component.empty(), ObjectiveCriteria.RenderType.INTEGER);
        }
        scoreboardObjective = objective;
    }

    @Override
    public double withdraw(Player player, double amount, boolean simulate) {
        return transfer(player, -amount, simulate);
    }

    @Override
    public double transfer(Player player, double amount, boolean simulate) {
        double balance = getBalance(player);
        double newBalance = Math.max(balance + amount, 0);
        if (!simulate) setDelphi(player, newBalance);
        return Math.abs(balance - newBalance);
    }

    @Override
    public double getBalance(Player player) {
        return scoreboard.getOrCreatePlayerScore(player.getStringUUID(), scoreboardObjective).getScore();
    }

    @Override
    public void setDelphi(Player player, double amount) {
        // todo replace with capability
        scoreboard.getOrCreatePlayerScore(player.getStringUUID(), scoreboardObjective).setScore((int) amount);
    }

    protected void syncPlayer(ServerPlayer player) {

    }
}
