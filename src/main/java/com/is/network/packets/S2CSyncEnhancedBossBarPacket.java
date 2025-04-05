package com.is.network.packets;

import com.is.data.CommonEnhancedBossEvent;
import com.is.network.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class S2CSyncEnhancedBossBarPacket extends AbstractPacket {

    public final UUID uuid;
    public final float value;
    public final float maxValue;
    public final Component component;
    public final boolean shouldDarkenSky;
    public final boolean shouldAddFog;
    public final boolean shouldPlayBossMusic;
    public final boolean removed;

    public S2CSyncEnhancedBossBarPacket(FriendlyByteBuf packetBuffer) {
        super(packetBuffer);
        this.uuid = packetBuffer.readUUID();
        this.value = packetBuffer.readFloat();
        this.maxValue = packetBuffer.readFloat();
        this.component = packetBuffer.readComponent();
        this.shouldDarkenSky = packetBuffer.readBoolean();
        this.shouldAddFog = packetBuffer.readBoolean();
        this.shouldPlayBossMusic = packetBuffer.readBoolean();
        this.removed = packetBuffer.readBoolean();
    }

    public S2CSyncEnhancedBossBarPacket(UUID uuid, float value, float maxValue, Component component, boolean shouldDarkenSky, boolean shouldAddFog, boolean shouldPlayBossMusic, boolean removed) {
        super(null);
        this.uuid = uuid;
        this.value = value;
        this.maxValue = maxValue;
        this.component = component;
        this.shouldDarkenSky = shouldDarkenSky;
        this.shouldAddFog = shouldAddFog;
        this.shouldPlayBossMusic = shouldPlayBossMusic;
        this.removed = removed;
    }

    public S2CSyncEnhancedBossBarPacket(CommonEnhancedBossEvent event, boolean removed) {
        this(event.getId(), event.getValue(), event.getMaxValue(), event.getName(), event.shouldDarkenScreen(), event.shouldCreateWorldFog(), event.shouldPlayBossMusic(), removed);
    }

    @Override
    public void toBuf(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeUUID(uuid);
        packetBuffer.writeFloat(value);
        packetBuffer.writeFloat(maxValue);
        packetBuffer.writeComponent(component);
        packetBuffer.writeBoolean(shouldDarkenSky);
        packetBuffer.writeBoolean(shouldAddFog);
        packetBuffer.writeBoolean(shouldPlayBossMusic);
        packetBuffer.writeBoolean(removed);
    }
}