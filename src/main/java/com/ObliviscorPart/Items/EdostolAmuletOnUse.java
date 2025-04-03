package com.ObliviscorPart.Items;

import com.is.ISConst;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EdostolAmuletOnUse extends Item {
    public EdostolAmuletOnUse() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant().tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer){
            BlockPos targetPos = getTeleportPosition(player);

            if(targetPos != null) {
                serverPlayer.teleportTo(targetPos.getX()+0.5, targetPos.getY(), targetPos.getZ()+0.5);
                serverPlayer.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                player.getCooldowns().addCooldown(this, 200);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private BlockPos getTeleportPosition (Player player) {
        Vec3 start = player.getEyePosition(1.0f);
        Vec3 direction = player.getViewVector(1.0f).scale(10);
        Vec3 end = start.add(direction);
        HitResult hitResult = player.level.clip(new ClipContext(start, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

        if(hitResult.getType() == HitResult.Type.BLOCK) {
            return new BlockPos(hitResult.getLocation());
        }
        return null;
    }
}
