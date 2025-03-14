package com.EveTuPart.Items;

import com.is.ISConst;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class LegendarySwordItemUse extends Item {
    public LegendarySwordItemUse(){ super(new Properties().tab(ISConst.modCreativeTab).stacksTo(1));
    }
    /*@Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide)
        {
            player.getInventory().items.set(player.getInventory().selected,new ItemStack(Items.GLOWSTONE_DUST,new Random().nextInt(1,5)));
        }
        return super.use(level, player, hand);
    }*/
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected){
        if (!pLevel.isClientSide)
        {
            Player player = pEntity.level.getNearestPlayer(pEntity,1);
            if (pIsSelected)
            {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP,9999,5));
            }
            else
            {
                player.removeAllEffects();
            }
        }
    }
}
