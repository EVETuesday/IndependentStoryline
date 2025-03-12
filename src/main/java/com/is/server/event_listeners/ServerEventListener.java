package com.is.server.event_listeners;

import com.is.server.data.ServerDelphiManager;
import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public final class ServerEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static MinecraftServer server;

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        server = event.getServer();
        ServerDelphiManager.initialize();
    }

    public void blockDestroyedEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
            //Todo
        }
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static final class ModEvents {

    }
}
