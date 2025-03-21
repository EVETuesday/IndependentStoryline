package com.is.client.network.handlers;

import com.is.client.data.ClientAbilityManager;
import com.is.network.AbstractPacketHandler;
import com.is.network.packets.S2CPlayerGotDelphiItemPacket;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CPlayerGotDelphiItemPacketHandler extends AbstractPacketHandler<S2CPlayerGotDelphiItemPacket> {

    @Override
    public boolean handle(S2CPlayerGotDelphiItemPacket data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientAbilityManager.getInstance().onPlayerGotItem(data.item);
        });
        return true;
    }
}
