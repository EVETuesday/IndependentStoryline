package com.is.network;

import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacketHandler<T> {

    public abstract boolean handle(T data, Supplier<NetworkEvent.Context> ctx);

}
