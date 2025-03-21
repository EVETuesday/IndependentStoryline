package com.is.network.packets;

import com.is.data.DelphiItemType;
import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

public class S2CPlayerGotDelphiItemPacket extends AbstractPacket {

    public DelphiItemType item;

    public S2CPlayerGotDelphiItemPacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        item = DelphiItemType.values()[packetBuffer.readInt()];
    }

    public S2CPlayerGotDelphiItemPacket(DelphiItemType item) {
        super(null);
        this.item = item;
    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(item.ordinal());
    }
}
