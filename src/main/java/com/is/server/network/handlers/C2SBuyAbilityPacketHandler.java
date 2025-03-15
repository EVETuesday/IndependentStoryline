package com.is.server.network.handlers;

import com.is.network.AbstractPacketHandler;
import com.is.network.packets.C2SBuyAbilityPacket;
import com.is.server.data.ServerAbilityManager;
import com.is.server.data.ServerDelphiManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class C2SBuyAbilityPacketHandler extends AbstractPacketHandler<C2SBuyAbilityPacket> {

    @Override
    public boolean handle(C2SBuyAbilityPacket data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            if (ServerDelphiManager.getInstance().getBalance(player) >= data.ability.price) {
                if (ServerDelphiManager.getInstance().withdraw(player, data.ability.price, true) == data.ability.price) {
                    ServerDelphiManager.getInstance().withdraw(player, data.ability.price, false);
                    ServerAbilityManager.getInstance().addAbility(player, data.ability);
                }
            }
        });
        return true;
    }

}
