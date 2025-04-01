package com.EveTuPart.Items;

import com.is.ISConst;
import com.is.items.CharmOfLuckItem;
import com.is.items.RandomFoodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public  static  final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ISConst.MODID);

    public  static  void register(IEventBus iEventBus)
    {
        ITEMS.register(iEventBus);
    }
    public  static  final RegistryObject<Item> LEGENDARY_SWORD = ITEMS.register("legendary_sword",LegendarySwordItemUse :: new);

    public static final RegistryObject<Item> CHARM_OF_LUCK = ITEMS.register("charm_of_luck", CharmOfLuckItem::new);
    public static final RegistryObject<Item> RANDOM_FOOD = ITEMS.register("random_food", RandomFoodItem::new);
}
