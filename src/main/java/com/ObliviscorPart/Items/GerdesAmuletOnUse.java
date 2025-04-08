package com.ObliviscorPart.Items;

import com.EveTuPart.Items.LegendarySwordItemUse;
import com.EveTuPart.Items.ModItems;
import com.is.ISConst;
import com.is.items.IItemWithTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GerdesAmuletOnUse extends Item implements IItemWithTooltip {

    public GerdesAmuletOnUse() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack MainHandItem = player.getItemBySlot(EquipmentSlot.MAINHAND);
            ServerLevel serverLevel = (ServerLevel) level;

            if(MainHandItem.getItem() == ModItems.LEGENDARY_SWORD.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));
                player.displayClientMessage(Component.literal("§5Получен буст к урону!"), true);
                player.getCooldowns().addCooldown(this, 600);
                serverPlayer.playSound(SoundEvents.BEACON_ACTIVATE, 100, 10);
                Vec3 pos = player.position();
                serverLevel.sendParticles(ParticleTypes.FLAME, pos.x, pos.y+1, pos.z, 20, 0.1, 0.1, 0.1, 0.2);
            } else {
                player.displayClientMessage(Component.literal("§mДля активации возьми в руку Легендарный меч!"), true);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        return ISConst.generateMagicItemDescription(itemStack, player, "gerdes_amulet");
    }

}
