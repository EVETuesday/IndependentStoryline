package com.is.network.packets;

import com.is.data.DelphiAbilityType;
import com.is.data.DelphiItemType;
import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class S2CSyncAbilitiesPacket extends AbstractPacket {

    public List<DelphiAbilityType> abilities;
    public DelphiItemType currentItem;

    public S2CSyncAbilitiesPacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        currentItem = DelphiItemType.values()[packetBuffer.readInt()];
        abilities = new ArrayList<>();
        int size = packetBuffer.readInt();
        for (int i = 0; i < size; i++) {
            abilities.add(DelphiAbilityType.values()[packetBuffer.readInt()]);
        }
    }

    public S2CSyncAbilitiesPacket(List<DelphiAbilityType> abilities, DelphiItemType currentItem) {
        super(null);
        this.currentItem = currentItem;
        this.abilities = abilities;
    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(currentItem.ordinal());
        packetBuffer.writeInt(abilities.size());
        abilities.forEach(abilityType -> packetBuffer.writeInt(abilityType.ordinal()));
    }
}
