package com.is.data;

import com.EveTuPart.Items.ModItems;
import com.is.items.MagazineItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Function;
import java.util.function.Supplier;

public enum DelphiItemType {

    MAGAZINE_1(100, () -> {
        ItemStack itemStack = new ItemStack(ModItems.MAGAZINE.get(), 1);
        itemStack.getOrCreateTag().merge(MagazineItem.MAGAZINE_COMPOUND_1);
        return itemStack;
    }, (player) -> Component.literal("Вы получили газету «Правда Силы».").append(" Данную газету Вы можете прочитать неограниченное количество раз.")),
    CHARM_OF_LUCK(50, () -> new ItemStack(ModItems.CHARM_OF_LUCK.get()),
            (player) -> Component.literal("Вы получили Амулет Удачи. Данный амулет увеличит на 30 секунд количество получаемых дельфов в два раза, но активируется с шансом 15%.").append(" Перезарядка амулета: 15 секунд.")),
    MAGAZINE_2(200, () -> {
        ItemStack itemStack = new ItemStack(ModItems.MAGAZINE.get(), 1);
        itemStack.getOrCreateTag().merge(MagazineItem.MAGAZINE_COMPOUND_2);
        return itemStack;
    }, (player) -> Component.literal("Вы получили газету «Горькая правда».").append(" Создателем данной газеты является неизвестная группа повстанцев этого мира.")),
    RANDOM_FOOD(150, () -> new ItemStack(ModItems.RANDOM_FOOD.get(), 3),
            (player) -> Component.literal("Вы получили Еду величия.").append(" Когда Вы ее съедите, то на вас наложится случайный эффект, он может быть как положительным, так и отрицательным.")),
    NULL(-1, () -> new ItemStack(Items.AIR), (player) -> Component.empty());

    public final double requiredDelphies;
    public final Supplier<ItemStack> item;
    public final Function<Player, Component> message;

    DelphiItemType(double requiredDelphies, Supplier<ItemStack> item, Function<Player, Component> message) {
        this.requiredDelphies = requiredDelphies;
        this.item = item;
        this.message = message;
    }

}
