package com.ObliviscorPart.Items;

import com.ObliviscorPart.Effects.ModEffects;
import com.is.ISConst;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class AlfiaAmuletOnUse extends Item {

    public AlfiaAmuletOnUse() {
      super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant().tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!player.level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            player.addEffect(new MobEffectInstance(ModEffects.FLIGHT.get(), 200, 0));
            player.displayClientMessage(Component.literal("§6Способность Полет активирована."), true);
            player.getCooldowns().addCooldown(this, 100);
        }
        return super.use(level, player, hand);
    }

}
