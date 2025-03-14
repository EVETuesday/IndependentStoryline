package com.is.client.network.handlers;

import com.is.client.data.ClientDelphiManager;
import com.is.network.AbstractPacketHandler;
import com.is.network.packets.S2CSyncBalancePacket;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncBalancePacketHandler extends AbstractPacketHandler<S2CSyncBalancePacket> {

    @Override
    public boolean handle(S2CSyncBalancePacket data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientDelphiManager.getInstance().setDelphi(null, data.balance);
        });
        return true;
    }

}
