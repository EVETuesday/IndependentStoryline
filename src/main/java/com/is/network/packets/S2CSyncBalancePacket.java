package com.is.network.packets;

import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

public class S2CSyncBalancePacket extends AbstractPacket {

    public double balance;

    public S2CSyncBalancePacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        balance = packetBuffer.readDouble();
    }

    public S2CSyncBalancePacket(double balance) {
        super(null);
        this.balance = balance;
    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(balance);
    }
}
