package com.is.network.packets;

import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

public class S2CSyncBalancePacket extends AbstractPacket {

    public double balance;
    public double networth;

    public S2CSyncBalancePacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        balance = packetBuffer.readDouble();
        networth = packetBuffer.readDouble();
    }

    public S2CSyncBalancePacket(double balance, double networth) {
        super(null);
        this.balance = balance;
        this.networth = networth;
    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(balance);
        packetBuffer.writeDouble(networth);
    }
}
