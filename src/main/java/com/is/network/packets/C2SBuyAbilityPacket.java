package com.is.network.packets;

import com.is.data.DelphiAbilityType;
import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class C2SBuyAbilityPacket extends AbstractPacket {

    public DelphiAbilityType ability;

    public C2SBuyAbilityPacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        ability = DelphiAbilityType.values()[packetBuffer.readInt()];
    }

    public C2SBuyAbilityPacket(DelphiAbilityType ability) {
        super(null);
        this.ability = ability;
    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(ability.ordinal());
    }
}
