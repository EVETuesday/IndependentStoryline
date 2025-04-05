package com.is.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class DelphiBalanceChangedEvent extends Event {

    public final Player player;
    public final double oldBalance;
    public final double newBalance;
    public final double newNetworth;

    public DelphiBalanceChangedEvent(Player player, double oldBalance, double newBalance, double newNetworth) {
        this.player = player;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.newNetworth = newNetworth;
    }

}
