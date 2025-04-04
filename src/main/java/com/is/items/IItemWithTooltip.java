package com.is.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IItemWithTooltip {

    List<Component> getTooltip(ItemStack itemStack, Player player);

}
