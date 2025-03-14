package com.is.data.abilities;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.slf4j.Logger;

public class SpeedAbility extends AbstractAbility {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void add(ServerPlayer player) {
        LOGGER.debug("Attached speed ability");
    }

    @Override
    public void remove(ServerPlayer player) {

    }

    @Override
    public void tick(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 3, true, false));
    }

}
