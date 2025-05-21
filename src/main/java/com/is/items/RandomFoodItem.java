package com.is.items;

import com.is.ISConst;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class RandomFoodItem extends Item implements IItemWithTooltip {

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
                                        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60 * 2, 2, false, false), .5f)
                                        .effect(() -> new MobEffectInstance(MobEffects.JUMP, 20 * 60 * 2, 2, false, false), .5f)
                                        .effect(() -> new MobEffectInstance(MobEffects.POISON, 20 * 60, 2, false, false), .1f).build()
                        )
        );

    }
    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        return ISConst.generateMagicItemDescription(itemStack, player, "random_food");
    }
}
