package com.ObliviscorPart.Items;

import com.ObliviscorPart.Effects.ModEffects;
import com.is.ISConst;
import com.is.items.IItemWithTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlfiaAmuletOnUse extends Item implements IItemWithTooltip {

    public AlfiaAmuletOnUse() {
      super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!player.level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ServerLevel serverLevel = (ServerLevel) level;
            player.addEffect(new MobEffectInstance(ModEffects.FLIGHT.get(), 200, 0));
            player.displayClientMessage(Component.literal("§6Способность Полет активирована."), true);
            player.getCooldowns().addCooldown(this, 1200);
            Vec3 pos = player.position();
            serverLevel.sendParticles(ParticleTypes.CLOUD, pos.x, pos.y+1, pos.z, 20, 0.2, 0.2,0.2, 0.2);
        }
        return super.use(level, player, hand);
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        return ISConst.generateMagicItemDescription(itemStack, player, "alfia_amulet");
    }
}
