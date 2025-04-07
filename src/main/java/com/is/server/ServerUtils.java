package com.is.server;

import com.is.server.event_listeners.ServerEventListener;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

public final class ServerUtils {

    private ServerUtils() {
    }

    @Nullable
    public static MinecraftServer getCurrentServer() {
        return ServerEventListener.getServer();
    }

}
