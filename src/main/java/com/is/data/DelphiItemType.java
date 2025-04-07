package com.is.data;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum DelphiItemType {

    COPPER(50, () -> new ItemStack(Items.COPPER_BLOCK)),
    IRON(100, () -> new ItemStack(Items.IRON_AXE)),
    GOLD(150, () -> new ItemStack(Items.GOLD_BLOCK)),
    DIAMOND(200, () -> new ItemStack(Items.DIAMOND)),
    NULL(-1, () -> new ItemStack(Items.AIR));

    public final double requiredDelphies;
    public final Supplier<ItemStack> item;

    DelphiItemType(double requiredDelphies, Supplier<ItemStack> item) {
        this.requiredDelphies = requiredDelphies;
        this.item = item;
    }
}
