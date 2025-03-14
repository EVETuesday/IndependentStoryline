package com.is.network;

import net.minecraftforge.network.NetworkDirection;

public class PacketData {

    public final String packetClass;
    public final String packetHandlerClass;
    public final NetworkDirection playTo;

    public PacketData(String packetClass, String packetHandlerClass, NetworkDirection playTo) {
        this.packetClass = packetClass;
        this.packetHandlerClass = packetHandlerClass;
        this.playTo = playTo;
    }
}
