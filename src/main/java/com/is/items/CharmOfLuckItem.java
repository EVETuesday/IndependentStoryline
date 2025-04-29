package com.is.items;

import com.EveTuPart.Items.ModItems;
import com.is.ISConst;
import com.is.events.GainDelphiEvent;
import com.is.utils.CommonUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = ISConst.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CharmOfLuckItem extends Item implements IItemWithTooltip {

    public CharmOfLuckItem() {
        super(new Item.Properties().tab(ISConst.modCreativeTab).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @SubscribeEvent
    public void gainDelphiEvent(GainDelphiEvent event) {
        if (!(event.player instanceof ServerPlayer)) return;
        Random random = new Random();
        if (random.nextFloat() <= 0.15f && CommonUtils.isPlayerHasItem(event.player, ModItems.CHARM_OF_LUCK.get())) {
            event.amount *= 2;
        }
    }

    @Override
    public List<Component> getTooltip(ItemStack itemStack, Player player) {
        // 3 параметр - по какому названию искать описание в переводе
        return ISConst.generateMagicItemDescription(itemStack, player, "charm_of_luck");
    }
}
