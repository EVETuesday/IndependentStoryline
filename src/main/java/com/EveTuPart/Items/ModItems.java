package com.EveTuPart.Items;

import com.is.ISConst;
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
}
