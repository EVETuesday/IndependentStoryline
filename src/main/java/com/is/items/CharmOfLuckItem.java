package com.is.items;

import com.EveTuPart.Items.ModItems;
import com.is.ISConst;
import com.is.events.GainDelphiEvent;
import com.is.utils.CommonUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CharmOfLuckItem extends Item {

    public CharmOfLuckItem() {
        super(new Item.Properties().tab(ISConst.modCreativeTab).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @SubscribeEvent
    public void gainDelphiEvent(GainDelphiEvent event) {
        Random random = new Random();
        if (random.nextFloat() <= 0.15f && CommonUtils.isPlayerHasItem(event.player, ModItems.CHARM_OF_LUCK.get())) {
            event.amount *= 2;
        }
    }

}
