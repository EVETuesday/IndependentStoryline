package com.ObliviscorPart.Items;

import com.EveTuPart.Items.LegendarySwordItemUse;
import com.EveTuPart.Items.ModItems;
import com.is.ISConst;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.UUID;

public class GerdesAmuletOnUse extends Item {

    public GerdesAmuletOnUse() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON).tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack MainHandItem = player.getItemBySlot(EquipmentSlot.MAINHAND);

            if(MainHandItem.getItem() == ModItems.LEGENDARY_SWORD.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));
                player.displayClientMessage(Component.literal("§5Получен буст к урону!"), true);
                player.getCooldowns().addCooldown(this, 600);
            } else {
                player.displayClientMessage(Component.literal("§mДля активации возьми в руку Легендарный меч!"), true);
            }

            //Гельмо активирует амулет, который дает одноразовый супер-удар и уходит на перезарядку в 30 секунд
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

}
