package com.is.data;

import com.is.network.NetworkHandler;
import com.is.network.packets.S2CSyncEnhancedBossBarPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

public class CommonEnhancedBossEvent extends BossEvent {

    private float maxValue;
    private float value;

    public CommonEnhancedBossEvent(UUID pId, Component pName, float maxValue, boolean shouldDarkenSky, boolean shouldAddFog, boolean shouldPlayBossMusic) {
        super(pId, pName, BossBarColor.WHITE, BossBarOverlay.PROGRESS);
        this.maxValue = maxValue;
        setDarkenScreen(shouldDarkenSky);
        setCreateWorldFog(shouldAddFog);
        setPlayBossMusic(shouldPlayBossMusic);
    }

    public void remove() {
        syncClients(true);
    }

    public void add() {
        syncClients(false);
    }

    public void setValue(float value) {
        this.value = Mth.clamp(value, 0, getMaxValue());
    }

    public float getValue() {
        return this.value;
    }

    public void setMaxValue(float value) {
        this.maxValue = value;
        this.value = Mth.clamp(getValue(), 0, getMaxValue());
    }

    public float getMaxValue() {
        return this.maxValue;
    }

    protected void syncClients(boolean removed) {
        NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new S2CSyncEnhancedBossBarPacket(this, removed));
    }

    @Override
    public void setProgress(float pProgress) {
        this.value = (int) (maxValue * pProgress);
    }

    @Override
    public float getProgress() {
        return getValue() / getMaxValue();
    }

}
