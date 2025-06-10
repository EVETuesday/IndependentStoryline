package com.is.items;

import com.is.ISConst;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CircularAttackItem extends SwordItem {
    public CircularAttackItem() {
        super(Tiers.DIAMOND, 3, -2.4F, new Properties().tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            float radius = 3.0f;
            List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class,
                    new AABB(player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                            player.getX() + radius, player.getY() + radius, player.getZ() + radius),
                    e -> e != player && player.distanceToSqr(e) <= radius * radius);

            for (LivingEntity target : entities) {
                target.hurt(DamageSource.playerAttack(player), 5.0F);
                Vec3 knockback = new Vec3(target.getX() - player.getX(),0,target.getZ() - player.getZ()).normalize().scale(0.5);
                target.setDeltaMovement(knockback.x, 0.3, knockback.z);
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS,
                    1.0F, 1.0F);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}