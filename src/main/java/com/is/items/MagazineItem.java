package com.is.items;

import com.EveTuPart.Items.ModItems;
import com.is.ISConst;
import com.is.client.gui.magazines.MagazineType;
import com.is.events.GainDelphiEvent;
import com.is.network.NetworkHandler;
import com.is.network.packets.S2COpenMagazinePacket;
import com.is.utils.CommonUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MagazineItem extends Item {

    public static final String MAGAZINE_TYPE_NBT = "magazineType";

    public MagazineItem() {
        super(new Properties().tab(ISConst.modCreativeTab).stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer instanceof ServerPlayer player) {
            int id = itemStack.getOrCreateTag().getInt(MAGAZINE_TYPE_NBT);
            if (id >= 0 && id < MagazineType.values().length) {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new S2COpenMagazinePacket(MagazineType.values()[id]));
                return InteractionResultHolder.success(itemStack);
            }

            return InteractionResultHolder.pass(itemStack);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, true);
    }
}
