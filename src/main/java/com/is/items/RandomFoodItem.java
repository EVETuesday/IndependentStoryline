package com.is.items;

import com.is.ISConst;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

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
                                        .alwaysEat()
                                        .meat().build()
                        )
        );

    }
    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        return ISConst.generateMagicItemDescription(itemStack, player, "random_food");
    }

    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide()) {
            boolean isBadEffect = ISConst.RANDOM.nextFloat() < .2f;

            List<MobEffect> effects = ForgeRegistries.MOB_EFFECTS.getEntries().stream()
                    .filter(mobEffect -> mobEffect.getKey().location().getNamespace().equals("minecraft")) // only vanilla effects
                    .map(Map.Entry::getValue)
                    .filter(mobEffect -> (isBadEffect && mobEffect.getCategory() == MobEffectCategory.HARMFUL
                            || !isBadEffect && mobEffect.getCategory() != MobEffectCategory.HARMFUL))
                    .toList();


            if (!effects.isEmpty()) {
                MobEffect effect = effects.get(ISConst.RANDOM.nextInt(effects.size()));

                MobEffectInstance effectInstance = new MobEffectInstance(effect, isBadEffect ? 200 : 600, isBadEffect ? 1 : 2);
                pLivingEntity.addEffect(effectInstance);
            }
        }

        return this.isEdible() ? pLivingEntity.eat(pLevel, pStack) : pStack;
    }

}
