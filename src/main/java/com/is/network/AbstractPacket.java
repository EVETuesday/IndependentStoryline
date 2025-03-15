package com.is.network;

import net.minecraft.network.FriendlyByteBuf;

public abstract class AbstractPacket {

    protected AbstractPacket(FriendlyByteBuf packetBuffer) {}

    public abstract void toBuf(FriendlyByteBuf packetBuffer);
}
