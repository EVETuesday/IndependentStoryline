package com.is.data.abilities;

import net.minecraft.server.level.ServerPlayer;

public abstract class AbstractAbility {

    public abstract void add(ServerPlayer player);

    public abstract void remove(ServerPlayer player);

    public abstract void tick(ServerPlayer player);

}
