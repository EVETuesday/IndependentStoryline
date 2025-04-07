package com.is.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static boolean isPlayerHasItem(Player player, Item item) {
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.is(item)) {
                return true;
            }
        }
        return false;
    }

}
