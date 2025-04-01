package com.is.items;

import com.is.ISConst;
import com.is.server.data.ServerAbilityManager;
import com.is.server.data.ServerDelphiManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class RandomFoodItem extends Item {

    public RandomFoodItem() {
        super(
                new Properties()
                        .tab(ISConst.modCreativeTab)
                        .stacksTo(64)
                        .fireResistant()
                        .rarity(Rarity.EPIC)
                        .food(
                                new FoodProperties.Builder()
                                        .nutrition(3)
                                        .saturationMod(0.3F)
                                        .meat()
                                        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60 * 2, 2), .5f)
                                        .effect(() -> new MobEffectInstance(MobEffects.JUMP, 20 * 60 * 2, 2), .5f)
                                        .effect(() -> new MobEffectInstance(MobEffects.POISON, 20 * 60, 2), .1f).build()
                        )
        );
    }
}
