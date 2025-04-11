package com.ObliviscorPart.Items;

import com.is.ISConst;
import com.is.items.IItemWithTooltip;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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

import java.util.List;

public class FanziaAmuletOnUse extends Item implements IItemWithTooltip {
    public FanziaAmuletOnUse() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant().tab(ISConst.modCreativeTab));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && player instanceof ServerPlayer serverPlayer){
            BlockPos targetPos = getTeleportPosition(player);
            ServerLevel serverLevel = (ServerLevel) level;

            if(targetPos != null) {
                serverPlayer.teleportTo(targetPos.getX()+0.5, targetPos.getY(), targetPos.getZ()+0.5);
                serverPlayer.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                player.getCooldowns().addCooldown(this, 200);
                Vec3 pos = player.position();
                serverLevel.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y+1, pos.z, 20, 0.2, 0.2,0.2, 0.2);
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

    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        return ISConst.generateMagicItemDescription(itemStack, player, "edostol_amulet");
    }

}
