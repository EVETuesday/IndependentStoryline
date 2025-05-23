package com.is.data.abilities;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.slf4j.Logger;

public class AgilityAbility extends AbstractAbility {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void add(ServerPlayer player) {
        LOGGER.debug("Attached agility ability");
    }

    @Override
    public void remove(ServerPlayer player) {

    }

    @Override
    public void tick(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 4, 3, true, false));
    }

}
