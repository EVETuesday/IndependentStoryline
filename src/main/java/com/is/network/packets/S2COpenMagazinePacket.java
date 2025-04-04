package com.is.network.packets;

import com.is.client.gui.magazines.MagazineType;
import com.is.data.DelphiItemType;
import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

public class S2COpenMagazinePacket extends AbstractPacket {

    public MagazineType magazineType;

    public S2COpenMagazinePacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        magazineType = MagazineType.values()[packetBuffer.readInt()];
    }

    public S2COpenMagazinePacket(MagazineType magazineType) {
        super(null);
        this.magazineType = magazineType;

    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(magazineType.ordinal());
    }
}
