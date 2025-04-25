package com.is.data;

import com.EveTuPart.Items.ModItems;
import com.is.items.MagazineItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public enum DelphiItemType {

    CHARM_OF_LUCK(50, () -> new ItemStack(ModItems.CHARM_OF_LUCK.get())),
    MAGAZINE_1(100, () -> new ItemStack(ModItems.MAGAZINE.get(), 1, new CompoundTag())),
    RANDOM_FOOD(150, () -> new ItemStack(ModItems.RANDOM_FOOD.get(), 16)),
    MAGAZINE_2(200, () -> new ItemStack(ModItems.MAGAZINE.get(), 1, new CompoundTag())),
    NULL(-1, () -> new ItemStack(Items.AIR));

    public final static CompoundTag MAGAZINE_COMPOUND_1;
    public final static CompoundTag MAGAZINE_COMPOUND_2;

    public final double requiredDelphies;
    public final Supplier<ItemStack> item;

    DelphiItemType(double requiredDelphies, Supplier<ItemStack> item) {
        this.requiredDelphies = requiredDelphies;
        this.item = item;
    }

    static {
        MAGAZINE_COMPOUND_1 = new CompoundTag();
        MAGAZINE_COMPOUND_1.putInt(MagazineItem.MAGAZINE_TYPE_NBT, 0);
        MAGAZINE_COMPOUND_2 = new CompoundTag();
        MAGAZINE_COMPOUND_2.putInt(MagazineItem.MAGAZINE_TYPE_NBT, 1);
    }
}
