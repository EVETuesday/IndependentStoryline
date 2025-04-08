package com.EveTuPart.Items;

import com.is.ISConst;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LegendarySwordItemUse extends SwordItem {
    public LegendarySwordItemUse(){ super(Tiers.NETHERITE, 3,-2.4f,new Properties().tab(ISConst.modCreativeTab).rarity(Rarity.EPIC));
    }
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected){
        if (!pLevel.isClientSide)
        {
            Player player = pEntity.level.getNearestPlayer(pEntity,1);
            if (pIsSelected)
            {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP,10,1));
            }
            else
            {
                player.removeAllEffects();
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity Attacker) {
        if (Attacker.hasEffect(MobEffects.DAMAGE_BOOST)) {
            target.setSecondsOnFire(10);
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 8 * 20, 0));
        }
        return super.hurtEnemy(itemStack, target, Attacker);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> components, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Ваш легендарный меч").withStyle(ChatFormatting.WHITE));
        } else {
            components.add(Component.literal("Зажмите клавишу SHIFT для подробной информации.").withStyle(ChatFormatting.ITALIC, ChatFormatting.AQUA));
        }
        super.appendHoverText(pStack, pLevel, components, pIsAdvanced);
    }
}
