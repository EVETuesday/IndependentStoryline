package com.EveTuPart.Items;

import com.ObliviscorPart.Items.AlfiaAmuletOnUse;
import com.ObliviscorPart.Items.FanziaAmuletOnUse;
import com.ObliviscorPart.Items.GerdesAmuletOnUse;
import com.ObliviscorPart.Items.WrenchItem;
import com.is.ISConst;
import com.is.items.CharmOfLuckItem;
import com.is.items.CircularAttackItem;
import com.is.items.StoneStyletItem;
import com.is.items.MagazineItem;
import com.is.items.RandomFoodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ISConst.MODID);

    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }

    public static final RegistryObject<Item> LEGENDARY_SWORD = ITEMS.register("legendary_sword", LegendarySwordItemUse::new);

    public static final RegistryObject<Item> CHARM_OF_LUCK = ITEMS.register("charm_of_luck", CharmOfLuckItem::new);
    public static final RegistryObject<Item> RANDOM_FOOD = ITEMS.register("random_food", RandomFoodItem::new);
    public static final RegistryObject<Item> MAGAZINE = ITEMS.register("magazine_item", MagazineItem::new);
    public static final RegistryObject<Item> ALFIA_AMULET = ITEMS.register("alfia_amulet", AlfiaAmuletOnUse::new);
    public static final RegistryObject<Item> GERDES_AMULET = ITEMS.register("gerdes_amulet", GerdesAmuletOnUse::new);
    public static final RegistryObject<Item> FANZIA_AMULET = ITEMS.register("fanzia_amulet", FanziaAmuletOnUse::new);
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", WrenchItem::new);
    public static final RegistryObject<Item> STONE_STYLET_ITEM = ITEMS.register("stone_stylet", StoneStyletItem::new);
    public static final RegistryObject<Item> CIRCULAR_ATTACK_SWORD = ITEMS.register("circular_attack_sword", CircularAttackItem::new);

}
