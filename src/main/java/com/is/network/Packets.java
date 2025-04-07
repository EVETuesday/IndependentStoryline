package com.is.network;

import com.is.client.network.handlers.*;
import com.is.network.packets.*;
import com.is.server.network.handlers.C2SBuyAbilityPacketHandler;
import net.minecraftforge.network.NetworkDirection;

public enum Packets {

    C2S_BUY_ABILITY(C2SBuyAbilityPacket.class, C2SBuyAbilityPacketHandler.class, NetworkDirection.PLAY_TO_SERVER),

    S2C_SYNC_BOSS_BAR(S2CSyncEnhancedBossBarPacket.class, S2CSyncEnhancedBossBarPacketHandler.class, NetworkDirection.PLAY_TO_CLIENT),
    S2C_SYNC_BALANCE(S2CSyncBalancePacket.class, S2CSyncBalancePacketHandler.class, NetworkDirection.PLAY_TO_CLIENT),
    S2C_OPEN_MAGAZINE(S2COpenMagazinePacket.class, S2COpenMagazinePacketHandler.class, NetworkDirection.PLAY_TO_CLIENT),
    S2C_PLAYER_GOT_DELPHI_ITEM(S2CPlayerGotDelphiItemPacket.class, S2CPlayerGotDelphiItemPacketHandler.class, NetworkDirection.PLAY_TO_CLIENT),
    S2C_SYNC_ABILITIES(S2CSyncAbilitiesPacket.class, S2CSyncAbilitiesPacketHandler.class, NetworkDirection.PLAY_TO_CLIENT);

    public final Class<? extends AbstractPacket> packetClazz;
    public final Class<? extends AbstractPacketHandler<?>> handlerClazz;
    public final NetworkDirection playTo;

    Packets(Class<? extends AbstractPacket> packetClazz, Class<? extends AbstractPacketHandler<?>> handlerClazz, NetworkDirection playTo) {
        this.packetClazz = packetClazz;
        this.handlerClazz = handlerClazz;
        this.playTo = playTo;
    }
}
