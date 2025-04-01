package com.EveTuPart.Items;

import com.is.ISConst;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class LegendarySwordItemUse extends SwordItem {
    public LegendarySwordItemUse(){ super(Tiers.NETHERITE, 1,1,new Properties().tab(ISConst.modCreativeTab));

    }
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected){
        if (!pLevel.isClientSide)
        {
            Player player = pEntity.level.getNearestPlayer(pEntity,1);
            if (pIsSelected)
            {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP,5,2));
            }
            else
            {
                player.removeAllEffects();
            }
        }
    }
}
