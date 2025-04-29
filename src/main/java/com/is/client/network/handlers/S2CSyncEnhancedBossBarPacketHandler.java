package com.is.client.network.handlers;

import com.is.ISConst;
import com.is.client.data.ClientEnhancedBossEvent;
import com.is.client.data.ClientEnhancedBossEventManager;
import com.is.network.AbstractPacketHandler;
import com.is.network.packets.S2CSyncEnhancedBossBarPacket;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class S2CSyncEnhancedBossBarPacketHandler extends AbstractPacketHandler<S2CSyncEnhancedBossBarPacket> {

    @Override
    public boolean handle(S2CSyncEnhancedBossBarPacket data, Supplier<NetworkEvent.Context> ctx) {
        Optional<ClientEnhancedBossEvent> bossEvent = ClientEnhancedBossEventManager.getInstance().getBossBar(data.uuid);
        if (bossEvent.isEmpty()) {
            //todo implement entity name display
            ClientEnhancedBossEvent event = new ClientEnhancedBossEvent(data.uuid, ISConst.rl("textures/gui/boss_bar.png"), Component.literal("Абоба"), data.maxValue, false, false, false);
            ClientEnhancedBossEventManager.getInstance().add(event);
            event.sync(data);
        } else {
            bossEvent.get().sync(data);
        }
        return true;
    }

}
