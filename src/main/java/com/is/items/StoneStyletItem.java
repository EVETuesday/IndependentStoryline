package com.is.items;

import com.is.ISConst;
import com.is.entitys.ModEntitys;
import com.is.entitys.StoneStyletProjectileEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StoneStyletItem extends Item implements IItemWithTooltip  {
    public StoneStyletItem() {
        super(new Properties().tab(ISConst.modCreativeTab));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            StoneStyletProjectileEntity projectile = new StoneStyletProjectileEntity(ModEntitys.STONE_STYLET_PROJECTILE.get(), level);
            projectile.setOwner(player);
            projectile.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

            Vec3 look = player.getLookAngle();
            projectile.shoot(look.x, look.y, look.z, 1.0F, 0.1F);

            level.addFreshEntity(projectile);
        }

        player.getCooldowns().addCooldown(this, 20);
        return InteractionResultHolder.success(itemstack);
    }
    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        return ISConst.generateMagicItemDescription(itemStack, player, "stone_stylet");

    }
}
