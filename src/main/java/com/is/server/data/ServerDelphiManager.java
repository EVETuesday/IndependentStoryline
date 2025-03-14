package com.is.server.data;

import com.is.capabilities.ModCapabilities;
import com.is.capabilities.delphi.IDelphiCapability;
import com.is.data.IDelphiManager;
import com.is.network.NetworkHandler;
import com.is.network.packets.S2CSyncBalancePacket;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.util.Optional;

public class ServerDelphiManager implements IDelphiManager {

    private static ServerDelphiManager manager;

    public static void initialize() {
        manager = new ServerDelphiManager();
    }

    public static ServerDelphiManager getInstance() {
        return manager;
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private ServerDelphiManager() {}

    @Override
    public double getBalance(Player player) {
        Optional<IDelphiCapability> capability = player.getCapability(ModCapabilities.DELPHI).resolve();
        if (capability.isPresent()) {
            LOGGER.debug("Successfully got players balance info");
            return capability.get().getBalance();
        } else {
            LOGGER.warn("No capability was found for this player (at getBalance)");
            return 0.0;
        }
    }

    @Override
    public void setDelphi(Player player, double amount) {
        Optional<IDelphiCapability> capability = player.getCapability(ModCapabilities.DELPHI).resolve();
        capability.ifPresentOrElse((cap) -> {
            cap.setBalance(amount);
            syncPlayer((ServerPlayer) player, amount);
            LOGGER.debug("Updated players balance to {}", amount);
        }, () -> LOGGER.warn("No capability attached!"));
    }

    public void syncPlayer(ServerPlayer player) {
        syncPlayer(player, getBalance(player));
    }

    protected void syncPlayer(ServerPlayer player, double amount) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new S2CSyncBalancePacket(amount));
    }
}
