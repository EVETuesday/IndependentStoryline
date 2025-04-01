package com.is.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class GainDelphiEvent extends Event {

    public final Player player;
    public double amount;

    public GainDelphiEvent(Player player, double amount) {
        this.player = player;
        this.amount = amount;
    }

}
