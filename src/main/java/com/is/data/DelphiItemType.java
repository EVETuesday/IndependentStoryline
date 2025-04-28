package com.is.data;

import com.EveTuPart.Items.ModItems;
import com.is.items.MagazineItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum DelphiItemType {

    CHARM_OF_LUCK(50, () -> new ItemStack(ModItems.CHARM_OF_LUCK.get())),
    MAGAZINE_1(100, () -> {
        ItemStack itemStack = new ItemStack(ModItems.MAGAZINE.get(), 1);
        itemStack.getOrCreateTag().merge(MagazineItem.MAGAZINE_COMPOUND_1);
        return itemStack;
    }),
    RANDOM_FOOD(150, () -> new ItemStack(ModItems.RANDOM_FOOD.get(), 16)),
    MAGAZINE_2(200, () -> {
        ItemStack itemStack = new ItemStack(ModItems.MAGAZINE.get(), 1);
        itemStack.getOrCreateTag().merge(MagazineItem.MAGAZINE_COMPOUND_2);
        return itemStack;
    }),
    NULL(-1, () -> new ItemStack(Items.AIR));

    public final double requiredDelphies;
    public final Supplier<ItemStack> item;

    DelphiItemType(double requiredDelphies, Supplier<ItemStack> item) {
        this.requiredDelphies = requiredDelphies;
        this.item = item;
    }

}
